package edu.ncsu.csc.iTrust2.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import edu.ncsu.csc.iTrust2.forms.FoodDiaryForm;
import edu.ncsu.csc.iTrust2.models.FoodDiary;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.FoodDiaryService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

@RestController
public class APIFoodDiaryController extends APIController {

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private FoodDiaryService foodDiaryService;

    @Autowired
    private UserService userService;

    @PostMapping(BASE_PATH + "/food_diary/add")
    public ResponseEntity<FoodDiary> addEntry(@RequestBody FoodDiary entry) {
        return ResponseEntity.ok(foodDiaryService.addEntry(entry));
    }

    /**
     * Retrieves and returns a list of all Patients stored in the system
     *
     * @return list of patients
     */
    @GetMapping(BASE_PATH + "/patients")
    public List<Patient> getPatients() {
        final List<Patient> patients = (List<Patient>) patientService.findAll();
        return patients;
    }

    @GetMapping(BASE_PATH + "food_diary/view/{patientMID}")
    public ResponseEntity List<FoodDiary> findByIdContains( @PathVariable("patientMID") Long id ) {
        final List<FoodDiary> f = (List<FoodDiary>) foodDiaryService.findById( id );
        return ResponseEntity.ok(f);
    }


    // @GetMapping ( BASE_PATH + "/fooddiary/{id}" )
    // @PreAuthorize ( "hasAnyRole('ROLE_PATIENT', 'ROLE_HCP)" )
    // public ResponseEntity getFoodDiary ( @PathVariable final Long id ) {
    // final FoodDiary f = (FoodDiary) foodDiaryService.findById( id );
    //
    // if (f == null ) {
    // loggerUtil.log( TransactionType.PRESCRIPTION_VIEW, LoggerUtil.currentUser(),
    // "Failed to find food diary with id" + id );
    // return new ResponseEntity (errorResponse("No food diary found for " + id),
    // "Viewed food diary " + id );
    // }
    // }
    //

    // @GetMapping
    // public ResponseEntity<List<FoodDiary>> getAllEntries() {
    // return ResponseEntity.ok(foodDiaryService.getAllEntries());
    // }
    //
    // @GetMapping("/{id}")
    // public ResponseEntity<FoodDiary> getEntryById(@PathVariable Long id) {
    // FoodDiaryEntry entry = foodDiaryService.getEntryById(id);
    // if (entry == null) {
    // return ResponseEntity.notFound().build();
    // }
    // return ResponseEntity.ok(entry);
    // }
    //

}
