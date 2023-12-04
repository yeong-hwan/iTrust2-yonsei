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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import edu.ncsu.csc.iTrust2.models.LabTest;
import edu.ncsu.csc.iTrust2.models.LabOrder;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.models.Patient;

import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

import edu.ncsu.csc.iTrust2.models.Diagnosis;
import edu.ncsu.csc.iTrust2.models.Prescription;
import edu.ncsu.csc.iTrust2.services.DiagnosisService;
import edu.ncsu.csc.iTrust2.services.PrescriptionService;
import edu.ncsu.csc.iTrust2.services.LabOrderService;
import edu.ncsu.csc.iTrust2.services.LabTestService;

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
    private LabOrderService    labOrderService;

    @Autowired
    private LabTestService    labTestService;
    
   
    /* 
     * Retrieves LabTest results for a given patient
     */
    @GetMapping ( BASE_PATH + "/lab_tests/view_results/{patiendMID}")
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_PATIENT', 'ROLE_LABTECH')")
    public ResponseEntity getLabTestResults ( @PathVariable ( "patiendMID" ) final String patiendMID ) {
        final User self = userService.findByName( LoggerUtil.currentUser() );
        
        boolean isAuthorized = false;
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority hcp = new SimpleGrantedAuthority( "ROLE_HCP" );
        final SimpleGrantedAuthority patient = new SimpleGrantedAuthority( "ROLE_PATIENT" );
        final SimpleGrantedAuthority labtech = new SimpleGrantedAuthority( "ROLE_LABTECH" );
        try {
            isAuthorized = auth.getAuthorities().contains( hcp ) || auth.getAuthorities().contains( patient ) ||
                            auth.getAuthorities().contains( labtech );
            if ( !isAuthorized ) {
                return new ResponseEntity( errorResponse( "User not authenticated" ),
                        HttpStatus.UNAUTHORIZED );
            }
        }
        catch ( final Exception e ) {
            return new ResponseEntity(errorResponse("User not authenticated"), HttpStatus.UNAUTHORIZED );
        }

        try{
            final List<LabTest> labTests = labTestService.findByPatient( self.getUsername() );
            if ( labTests.isEmpty() ) {
                return new ResponseEntity( errorResponse( "No results found" ),
                        HttpStatus.NOT_FOUND );
            }
            loggerUtil.log( TransactionType.VIEW_LAB_TEST_RESULTS, LoggerUtil.currentUser(), "Patient, Lab Tech, or HCP views lab test results");

            return new ResponseEntity( labTests, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "No results found" ),
                    HttpStatus.NOT_FOUND );
        }
    }

}
