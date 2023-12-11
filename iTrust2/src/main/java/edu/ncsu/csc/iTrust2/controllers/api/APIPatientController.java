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

import edu.ncsu.csc.iTrust2.forms.PatientForm;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

import edu.ncsu.csc.iTrust2.models.Diagnosis;
import edu.ncsu.csc.iTrust2.models.Prescription;
import edu.ncsu.csc.iTrust2.services.DiagnosisService;
import edu.ncsu.csc.iTrust2.services.PrescriptionService;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.Period;

/**
 * Controller responsible for providing various REST API endpoints for the
 * Patient model.
 *
 * @author Kai Presler-Marshall
 *
 */
@RestController
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APIPatientController extends APIController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService    userService;

    @Autowired
    private LoggerUtil     loggerUtil;
    
    @Autowired
    private PrescriptionService    prescriptionService;
    
    @Autowired
    private DiagnosisService    diagnosisService;

    /**
     * Retrieves and returns a list of all Patients stored in the system
     *
     * @return list of patients
     */
    @GetMapping ( BASE_PATH + "/patients" )
    public List<Patient> getPatients () {
        final List<Patient> patients = (List<Patient>) patientService.findAll();
        return patients;
    }

    /**
     * If you are logged in as a patient, then you can use this convenience
     * lookup to find your own information without remembering your id. This
     * allows you the shorthand of not having to look up the id in between.
     *
     * @return The patient object for the currently authenticated user.
     */
    @GetMapping ( BASE_PATH + "/patient" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public ResponseEntity getPatient () {
        final User self = userService.findByName( LoggerUtil.currentUser() );
        final Patient patient = (Patient) patientService.findByName( self.getUsername() );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "Could not find a patient entry for you, " + self.getUsername() ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            loggerUtil.log( TransactionType.VIEW_DEMOGRAPHICS, self );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
    }


    /* @yewon 2023.11.19
     * *****************************************************
     * GET patient list given a query, named 'searchQuery'
     *  -   A 'searchQuery' can be either a part of name(including firstname and lastname) or a part of username
     *  -   If the query is empty, return all patients
     *  -   If the query is not empty, return all patients whose name or username contains the query
     *  -   It also takes a parameter 'searchType', which can be 'name' or 'username' 
     *  -   If the searchType is 'name', the query is a part of name
     *  -   If the searchType is 'username', the query is a part of username
     * *****************************************************
     */
    @GetMapping ( BASE_PATH + "/emergency_health_records/search")
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_ER')" )
    public ResponseEntity getPatientsByQuery ( @RequestParam final String searchQuery, @RequestParam final String searchType ) {
        List<Patient> patients = null;
        boolean isAuthorized = false;
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority hcp = new SimpleGrantedAuthority( "ROLE_HCP" );
        final SimpleGrantedAuthority er = new SimpleGrantedAuthority( "ROLE_ER" );
        
        isAuthorized = auth.getAuthorities().contains( hcp ) || auth.getAuthorities().contains( er );
        if ( !isAuthorized ) {
            return new ResponseEntity( errorResponse( "User not authenticated" ),
                    HttpStatus.UNAUTHORIZED );
        }
        
        if ( searchQuery.isEmpty() ) {
            patients = (List<Patient>) patientService.findAll();
        }
        else {
            if ( searchType.equals( "name" ) ) {
                patients = (List<Patient>) patientService.findByNameContains( searchQuery );
            }
            else if ( searchType.equals( "username" ) ) {
                patients = (List<Patient>) patientService.findByUsernameContains( searchQuery );
            }
        }
        if ( patients == null ) {
            return new ResponseEntity( errorResponse( "No matching patients" ), HttpStatus.NOT_FOUND );
        }
        else {
            // logging 
            // if current user is hcp, log as hcp_view_er
            // if current user is er, log as er_view_er
            if ( auth.getAuthorities().contains( hcp ) ) {
                loggerUtil.log( TransactionType.HCP_VIEW_ER, LoggerUtil.currentUser(), "HCP views a patients Emergency Health Records " + searchQuery );
            }
            else if ( auth.getAuthorities().contains( er ) ) {
                loggerUtil.log( TransactionType.ER_VIEW_ER, LoggerUtil.currentUser(), "ER views a patients Emergency Health Records " + searchQuery );
            }
            return new ResponseEntity( patients, HttpStatus.OK );
        }   
        
    }


    /**
     * Retrieves and returns the Patient with the username provided
     *
     * @param username
     *            The username of the Patient to be retrieved, as stored in the
     *            Users table
     * @return response
     */
    @GetMapping ( BASE_PATH + "/patients/{username}" )
    @PreAuthorize ( "hasRole('ROLE_HCP')" )
    public ResponseEntity getPatient ( @PathVariable ( "username" ) final String username ) {
        final Patient patient = (Patient) patientService.findByName( username );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "No Patient found for username " + username ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            loggerUtil.log( TransactionType.PATIENT_DEMOGRAPHICS_VIEW, LoggerUtil.currentUser(), username,
                    "HCP retrieved demographics for patient with username " + username );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
    }

    /**
     * Updates the Patient with the id provided by overwriting it with the new
     * Patient record that is provided. If the ID provided does not match the ID
     * set in the Patient provided, the update will not take place
     *
     * @param id
     *            The username of the Patient to be updated
     * @param patientF
     *            The updated Patient to save
     * @return response
     */
    @PutMapping ( BASE_PATH + "/patients/{id}" )
    public ResponseEntity updatePatient ( @PathVariable final String id, @RequestBody final PatientForm patientF ) {
        // check that the user is an HCP or a patient with username equal to id
        boolean userEdit = false; // true if user edits his or her own
                                  // demographics, false if hcp edits them
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority hcp = new SimpleGrantedAuthority( "ROLE_HCP" );
        try {
            userEdit = !auth.getAuthorities().contains( hcp );

            if ( !auth.getName().equals( id ) && userEdit ) {
                return new ResponseEntity( errorResponse( "You do not have permission to edit this record" ),
                        HttpStatus.UNAUTHORIZED );
            }

        }
        catch ( final Exception e ) {
            return new ResponseEntity( HttpStatus.UNAUTHORIZED );
        }

        try {
            final Patient patient = (Patient) patientService.findByName( id );

            // Shouldn't be possible but let's check anyways
            if ( null == patient ) {
                return new ResponseEntity( errorResponse( "Patient not found" ), HttpStatus.NOT_FOUND );
            }

            patient.update( patientF );

            final User dbPatient = patientService.findByName( id );
            if ( null == dbPatient ) {
                return new ResponseEntity( errorResponse( "No Patient found for id " + id ), HttpStatus.NOT_FOUND );
            }
            patientService.save( patient );

            // Log based on whether user or hcp edited demographics
            if ( userEdit ) {
                loggerUtil.log( TransactionType.EDIT_DEMOGRAPHICS, LoggerUtil.currentUser(),
                        "User with username " + patient.getUsername() + "updated their demographics" );
            }
            else {
                loggerUtil.log( TransactionType.PATIENT_DEMOGRAPHICS_EDIT, LoggerUtil.currentUser(),
                        patient.getUsername(),
                        "HCP edited demographics for patient with username " + patient.getUsername() );
            }
            return new ResponseEntity( patient, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not update " + patientF.toString() + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Get the patient's zip code to autofill Find Expert Form
     *
     * @return Response entity with status and response data (zip or error
     *         message)
     *
     */
    @GetMapping ( BASE_PATH + "/patient/findexperts/getzip" )
    @PreAuthorize ( "hasRole( 'ROLE_PATIENT')" )
    public ResponseEntity getPatientZip () {
        final String user = LoggerUtil.currentUser();
        if ( user == null ) {
            return new ResponseEntity( errorResponse( "Patient not found" ), HttpStatus.NOT_FOUND );
        }
        final String zip = ( (Patient) patientService.findByName( user ) ).getZip();
        if ( zip == null ) {
            return new ResponseEntity( errorResponse( "Patient does not have zip stored" ), HttpStatus.NO_CONTENT );
        }
        else {
            final String[] zipParts = zip.split( "-" );
            return new ResponseEntity( zipParts, HttpStatus.OK );

        }

    }
    /* Gyumin Noh
     * *****************************************************
     * GET patient list given a patient's username (patientMID)
     *  -   Only accessible by ER and HCP
     *  -	Returns patient not found 404 error if username is not in database
     *  -	If username is found, returns relevant emergency health record information including diagnoses and prescriptions
     * *****************************************************
     */
    @GetMapping ( BASE_PATH + "/emergency_health_records/view")
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_ER')" )
    public ResponseEntity getRecordsByPatientId ( @RequestParam final String patientUsername ) {
        final Authentication authorized = SecurityContextHolder.getContext().getAuthentication();
        final SimpleGrantedAuthority hcp = new SimpleGrantedAuthority( "ROLE_HCP" );
        final SimpleGrantedAuthority er = new SimpleGrantedAuthority( "ROLE_ER" );
        
        if(!(authorized.getAuthorities().contains( hcp ) || authorized.getAuthorities().contains( er ))) {
            return new ResponseEntity( errorResponse( "Unauthorized User" ),
                    HttpStatus.UNAUTHORIZED );
        }
    
        Patient patient = (Patient) patientService.findByName( patientUsername );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "Patient not found"),
                    HttpStatus.NOT_FOUND );
        }
        List<Diagnosis> diagnosis = diagnosisService.findByPatient(patient);
        List<Prescription> prescription = prescriptionService.findByPatient(patient);
        
        /*LocalDate today = LocalDate.now();
        Period ageCalc = Period.between(patient.getDateOfBirth(), today);
        int age = ageCalc.getYears();*/
        
        Map<String, Object> record = new HashMap<>();
        record.put("firstName", patient.getFirstName());
        record.put("lastName", patient.getLastName());
        record.put("age", patient.getAge());
        record.put("username", patientUsername);
        record.put("dob", patient.getDateOfBirth());
        record.put("gender", patient.getGender());
        record.put("bloodType", patient.getBloodType());
        //Map<String, Object> record = new HashMap<>();
        //record.put("patient", patient);
        record.put("diagnoses", diagnosis);
        record.put("prescriptions", prescription);
        
        return new ResponseEntity(record, HttpStatus.OK);
        
    }

}
