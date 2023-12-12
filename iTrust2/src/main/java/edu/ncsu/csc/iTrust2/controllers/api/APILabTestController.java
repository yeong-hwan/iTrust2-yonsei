package edu.ncsu.csc.iTrust2.controllers.api;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import edu.ncsu.csc.iTrust2.models.LabTest;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.models.Patient;

import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

import edu.ncsu.csc.iTrust2.models.Diagnosis;
import edu.ncsu.csc.iTrust2.models.Prescription;
import edu.ncsu.csc.iTrust2.services.DiagnosisService;
import edu.ncsu.csc.iTrust2.services.PrescriptionService;
import edu.ncsu.csc.iTrust2.services.LabTestService;
import edu.ncsu.csc.iTrust2.forms.LabTestForm;


import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.Period;

/**
 * Controller responsible for providing various REST API endpoints for the
 * LabTest model.
 *
 * @author Yewon Lim
 *
 */
@RestController
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APILabTestController extends APIController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService    userService;

    @Autowired
    private LoggerUtil     loggerUtil;

    @Autowired
    private LabTestService    labTestService;
    
   
    /* 
     * Retrieves LabTest results for a given patient
     */
    @GetMapping ( BASE_PATH + "/lab_tests/view_results/{patiendMID}")
    public ResponseEntity getLabTestResults ( @PathVariable ( "patiendMID" ) final String patiendMID ) {
        final Patient obj_patient = patientService.findByName( patiendMID );
        if (obj_patient == null){
            return new ResponseEntity( errorResponse( "No results found" ),
                    HttpStatus.NOT_FOUND );
        }
        boolean isAuthorized = false;
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority hcp = new SimpleGrantedAuthority( "ROLE_HCP" );
        final SimpleGrantedAuthority patient = new SimpleGrantedAuthority( "ROLE_PATIENT" );
        final SimpleGrantedAuthority labtech = new SimpleGrantedAuthority( "ROLE_LABTECH" );
        
        isAuthorized = auth.getAuthorities().contains( hcp ) || auth.getAuthorities().contains( patient ) ||
                        auth.getAuthorities().contains( labtech );
        if ( !isAuthorized ) {
            return new ResponseEntity( errorResponse( "User not authenticated" ),
                    HttpStatus.UNAUTHORIZED );
        }

        final List<LabTest> labTests = labTestService.findByPatient( patiendMID );
        if ( labTests.isEmpty() ) {
            return new ResponseEntity( errorResponse( "No results found" ),
                    HttpStatus.NOT_FOUND );
        }
        loggerUtil.log( TransactionType.VIEW_LAB_TEST_RESULTS, LoggerUtil.currentUser(), "Patient, Lab Tech, or HCP views lab test results");

        return new ResponseEntity( labTests, HttpStatus.OK );
    }


    @GetMapping (BASE_PATH + "/lab_tests/view_my_results")
    public ResponseEntity getMyLabTestResult (){
        final User self = userService.findByName( LoggerUtil.currentUser() );
        boolean isAuthorized = false;
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority patient = new SimpleGrantedAuthority( "ROLE_PATIENT" ); 
        isAuthorized =  auth.getAuthorities().contains( patient );
        if ( !isAuthorized ) {
            return new ResponseEntity( errorResponse( "User not authenticated" ),
                    HttpStatus.UNAUTHORIZED );
        }

        final List<LabTest> labTests = labTestService.findByPatient( self.getUsername() );
        if ( labTests.isEmpty() ) {
            return new ResponseEntity( errorResponse( "No results found" ),
                    HttpStatus.NOT_FOUND );
        }
        loggerUtil.log( TransactionType.VIEW_LAB_TEST_RESULTS, LoggerUtil.currentUser(), "Patient, Lab Tech, or HCP views lab test results");

        return new ResponseEntity( labTests, HttpStatus.OK );
    }

    /*
     * Order a lab test for a given patient name, testName, labName, and instructions
     */
    @PostMapping(BASE_PATH + "/lab_tests/order")
    public ResponseEntity orderLabTest(@RequestBody final LabTestForm body) {
        final String patientName = body.getPatientName();
        final String testName = body.getTestName();
        final String labName = body.getLabName();
        final String instructions = body.getInstructions();

        final User self = userService.findByName( LoggerUtil.currentUser() );
        boolean isAuthorized = false;
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority hcp = new SimpleGrantedAuthority( "ROLE_HCP" );
        try {
            isAuthorized = auth.getAuthorities().contains( hcp );
            if ( !isAuthorized ) {
                return new ResponseEntity( errorResponse( "User not authenticated" ),
                        HttpStatus.UNAUTHORIZED );
            }
        }
        catch ( final Exception e ) {
            return new ResponseEntity(errorResponse("User not authenticated"), HttpStatus.UNAUTHORIZED );
        }

        try{
            final Patient patient = patientService.findByName( patientName );
            if ( patient == null ) {
                return new ResponseEntity( errorResponse( "Missing or incorrect information" ),
                        HttpStatus.BAD_REQUEST );
            }
            
            LabTest dummyLabTest = new LabTest();
            List<User> labtechList = userService.findByRole(Role.ROLE_LABTECH);
            // gender-neutral indication ! ㅋㅋ 

            java.util.Random random = new java.util.Random();
            int zim = random.nextInt(labtechList.size());
            User labtech = labtechList.get(zim);
            


            dummyLabTest.setTestName( testName );
            dummyLabTest.setLabName( labName );
            dummyLabTest.setInstructions( instructions );
            dummyLabTest.setResults( "not-executed" );


            dummyLabTest.setPatient( patient );
            dummyLabTest.setHcp( self );
            dummyLabTest.setLabtech(labtech);
            
            dummyLabTest.setNotes( "" );

            labTestService.save( dummyLabTest );

            loggerUtil.log( TransactionType.ORDER_LAB_TESTS, LoggerUtil.currentUser(), "HCP orders laboratory tests");

            HashMap<String, String> response = new HashMap<String, String>();
            response.put("message", "Laboratory tests ordered successfully");
            return new ResponseEntity( response, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "Missing or incorrect information. Please check your request." + e  ),
                    HttpStatus.BAD_REQUEST );
        }
    }
    

    /*
     * View all lab tests assigned to a given lab tech
     */

    @GetMapping ( BASE_PATH + "/lab_tests/view_labtests")
    public ResponseEntity getLabTests() {
        final User self = userService.findByName( LoggerUtil.currentUser() );
        boolean isAuthorized = false;
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority labtech = new SimpleGrantedAuthority( "ROLE_LABTECH" );
        
        // only labtech can call this function
        isAuthorized = auth.getAuthorities().contains( labtech );
        if ( !isAuthorized ) {
            return new ResponseEntity( errorResponse( "User not authenticated" ),
                    HttpStatus.UNAUTHORIZED );
        }
        List<LabTest> labTests = labTestService.findByLabTech( self.getUsername() );
        if ( labTests.isEmpty() ) {
            return new ResponseEntity( errorResponse( "No results found" ),
                    HttpStatus.NOT_FOUND );
        }
        return new ResponseEntity( labTests, HttpStatus.OK );
    }
    
    

    /*
     * Process and Record a lab test for a given patient name, testName, labName, notes and results
     */
    @PostMapping(BASE_PATH + "/lab_tests/record_results")
    public ResponseEntity recordLabTest(@RequestBody final LabTestForm body) {
        final String patientName = body.getPatientName();
        final String testName = body.getTestName();
        final String notes = body.getNotes();
        final String results = body.getResults();
        final String labName = body.getLabName();
        

        final User self = userService.findByName( LoggerUtil.currentUser() );
        boolean isAuthorized = false;
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority labtech = new SimpleGrantedAuthority( "ROLE_LABTECH" );
        
        isAuthorized = auth.getAuthorities().contains( labtech );
        if ( !isAuthorized ) {
            return new ResponseEntity( errorResponse( "User not authenticated" ),
                    HttpStatus.UNAUTHORIZED );
        }
        
        

        try{
            final Patient patient = patientService.findByName( patientName );
            if ( patient == null ) {
                return new ResponseEntity( errorResponse( "No results found" ),
                        HttpStatus.NOT_FOUND );
            }
            final LabTest labTest = labTestService.findByPatientAndTestNameAndLabNameAndLabtech(patientName, testName, labName, self.getUsername());
            if ( labTest == null ) {
                return new ResponseEntity( errorResponse( "No results found" ),
                        HttpStatus.NOT_FOUND );
            }
            
            labTest.setNotes( notes );
            labTest.setResults( results );
            labTestService.save( labTest );

            loggerUtil.log( TransactionType.RECORD_LAB_TEST_RESULTS, LoggerUtil.currentUser(), "Lab Tech records lab test results");

            HashMap<String, String> response = new HashMap<String, String>();
            response.put("message", "Laboratory test results recorded successfully");
            return new ResponseEntity( response, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "No results found" ),
                        HttpStatus.NOT_FOUND );
        }
    }
}
