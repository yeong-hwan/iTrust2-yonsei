package edu.ncsu.csc.iTrust2.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.iTrust2.forms.OfficeVisitForm;
import edu.ncsu.csc.iTrust2.models.OfficeVisit;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.OfficeVisitService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

@RestController
@SuppressWarnings ( { "unchecked", "rawtypes" } )
public class APIOfficeVisitController extends APIController {

    @Autowired
    private OfficeVisitService officeVisitService;

    @Autowired
    private UserService        userService;

    @Autowired
    private LoggerUtil         loggerUtil;

    /**
     * Retrieves a list of all OfficeVisits in the database
     *
     * @return list of office visits
     */
    @GetMapping ( BASE_PATH + "/officevisits" )
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_OD', 'ROLE_OPH')" )
    public List<OfficeVisit> getOfficeVisits () {
        loggerUtil.log( TransactionType.VIEW_ALL_OFFICE_VISITS, LoggerUtil.currentUser() );
        return (List<OfficeVisit>) officeVisitService.findAll();
    }

    /**
     * Retrieves all of the office visits for the current HCP.
     *
     * @return all of the office visits for the current HCP.
     */
    @GetMapping ( BASE_PATH + "/officevisits/HCP" )
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_OD', 'ROLE_OPH')" )
    public List<OfficeVisit> getOfficeVisitsForHCP () {
        final User self = userService.findByName( LoggerUtil.currentUser() );
        loggerUtil.log( TransactionType.VIEW_ALL_OFFICE_VISITS, self );
        final List<OfficeVisit> visits = officeVisitService.findByHcp( self );
        return visits;
    }

    /**
     * Retrieves a list of all OfficeVisits in the database for the current
     * patient
     *
     * @return list of office visits
     */
    @GetMapping ( BASE_PATH + "/officevisits/myofficevisits" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public List<OfficeVisit> getMyOfficeVisits () {
        final User self = userService.findByName( LoggerUtil.currentUser() );
        loggerUtil.log( TransactionType.VIEW_ALL_OFFICE_VISITS, self );
        return officeVisitService.findByPatient( self );
    }

    /**
     * Retrieves a list of all OfficeVisits in the database
     *
     * @param id
     *            ID of the office visit to retrieve
     * @return list of office visits
     */
    @GetMapping ( BASE_PATH + "/officevisits/{id}" )
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_OD', 'ROLE_OPH')" )
    public ResponseEntity getOfficeVisit ( @PathVariable final Long id ) {
        final User self = userService.findByName( LoggerUtil.currentUser() );
//        loggerUtil.log( TransactionType.GENERAL_CHECKUP_HCP_VIEW, self );
        if ( !officeVisitService.existsById( id ) ) {
            return new ResponseEntity( HttpStatus.NOT_FOUND );
        }
        //12.02 logger 수정
        OfficeVisit officeVisit = (OfficeVisit) officeVisitService.findById(id);
        // 여기서 visitType을 기반으로 로그 타입 결정
        TransactionType logType;
        switch (officeVisit.getType()) { // getVisitType 메소드가 존재한다고 가정
            case GENERAL_CHECKUP:
                logType = TransactionType.GENERAL_CHECKUP_HCP_VIEW;
                break;
            case GENERAL_OPHTHALMOLOGY:
                logType = TransactionType.GENERAL_OPHTHALMOLOGY_HCP_VIEW;
                break;
            case OPHTHALMOLOGY_SURGERY:
                logType = TransactionType.OPHTHALMOLOGY_SURGERY_HCP_VIEW;
                break;
            default:
                logType = TransactionType.GENERAL_CHECKUP_HCP_VIEW; // 기본값 설정
        }        
        loggerUtil.log(logType, self);
        return new ResponseEntity( officeVisitService.findById( id ), HttpStatus.OK );
    }
    
    /**12.02 
     * get information of surgery visit of given id
     *
     * @param id
     *            ID of the surgery visit to retrieve
     * @return list of surgery visits
     */
    @GetMapping ( BASE_PATH + "/surgeryvisits/{id}" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public ResponseEntity getSurgeryVisit ( @PathVariable final Long id ) {
        final User self = userService.findByName( LoggerUtil.currentUser() );
        loggerUtil.log( TransactionType.OPHTHALMOLOGY_SURGERY_PATIENT_VIEW, self );
        if ( !officeVisitService.existsById( id ) ) {
            return new ResponseEntity( HttpStatus.NOT_FOUND );
        }
        OfficeVisit officeVisit = (OfficeVisit) officeVisitService.findById(id);
        return new ResponseEntity( officeVisit.getSurgeryVisit(), HttpStatus.OK );
    }

    /**
     * Creates and saves a new OfficeVisit from the RequestBody provided.
     *
     * @param visitForm
     *            The office visit to be validated and saved
     * @return response
     */
    @PostMapping ( BASE_PATH + "/officevisits" )
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_OD', 'ROLE_OPH')" )
    public ResponseEntity createOfficeVisit ( @RequestBody final OfficeVisitForm visitForm ) {
        try {
            final OfficeVisit visit = officeVisitService.build( visitForm );
            

            // 12.02 로그 타입 결정을 위한 로직
            TransactionType logType;
            switch (visit.getType()) {
            	case GENERAL_CHECKUP:
            		logType = TransactionType.GENERAL_CHECKUP_CREATE;
            		break;
            	case GENERAL_OPHTHALMOLOGY:
            		logType = TransactionType.GENERAL_OPHTHALMOLOGY_CREATE;
            		break;
            	case OPHTHALMOLOGY_SURGERY:
            		logType = TransactionType.OPHTHALMOLOGY_SURGERY_CREATE;
            		break;
            	default:
            		logType = TransactionType.GENERAL_CHECKUP_CREATE; // 기본값 설정
            }

            if ( null != visit.getId() && officeVisitService.existsById( visit.getId() ) ) {
                return new ResponseEntity(
                        errorResponse( "Office visit with the id " + visit.getId() + " already exists" ),
                        HttpStatus.CONFLICT );
            }
            officeVisitService.save( visit );
            loggerUtil.log( logType, LoggerUtil.currentUser(),
                    visit.getPatient().getUsername() );
            return new ResponseEntity( visit, HttpStatus.OK );

        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return new ResponseEntity(
                    errorResponse( "Could not validate or save the OfficeVisit provided due to " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Creates and saves a new Office Visit from the RequestBody provided.
     *
     * @param id
     *            ID of the office visit to update
     * @param visitForm
     *            The office visit to be validated and saved
     * @return response
     */
    @PutMapping ( BASE_PATH + "/officevisits/{id}" )
    @PreAuthorize ( "hasAnyRole('ROLE_HCP', 'ROLE_OD', 'ROLE_OPH')" )
    public ResponseEntity updateOfficeVisit ( @PathVariable final Long id,
            @RequestBody final OfficeVisitForm visitForm ) {
        try {
            final OfficeVisit visit = officeVisitService.build( visitForm );
            
            // 12.02 로그 타입 결정을 위한 로직
            TransactionType logType;
            switch (visit.getType()) {
            	case GENERAL_CHECKUP:
            		logType = TransactionType.GENERAL_CHECKUP_EDIT;
            		break;
            	case GENERAL_OPHTHALMOLOGY:
            		logType = TransactionType.GENERAL_OPHTHALMOLOGY_EDIT;
            		break;
            	case OPHTHALMOLOGY_SURGERY:
            		logType = TransactionType.OPHTHALMOLOGY_SURGERY_EDIT;
            		break;
            	default:
            		logType = TransactionType.GENERAL_CHECKUP_EDIT; // 기본값 설정
            }

            if ( null == visit.getId() || !officeVisitService.existsById( visit.getId() ) ) {
                return new ResponseEntity(
                        errorResponse( "Office visit with the id " + visit.getId() + " doesn't exist" ),
                        HttpStatus.NOT_FOUND );
            }
            officeVisitService.save( visit );
            loggerUtil.log( logType, LoggerUtil.currentUser(),
                    visit.getPatient().getUsername() );
            return new ResponseEntity( visit, HttpStatus.OK );

        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return new ResponseEntity(
                    errorResponse( "Could not validate or save the OfficeVisit provided due to " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

}
