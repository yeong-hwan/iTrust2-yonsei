package edu.ncsu.csc.iTrust2.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.sql.Date;
import java.util.List;

import edu.ncsu.csc.iTrust2.forms.FoodDiaryForm;
import edu.ncsu.csc.iTrust2.models.FoodDiary;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.FoodDiaryService;
import edu.ncsu.csc.iTrust2.services.PatientService;
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

    // @GetMapping(BASE_PATH + "food_diary/view")
    // public List<Patient> getPatients() {
    // final List<Patient> patients = (List<Patient>) PatientService.findAll();
    // return patients;
    // }

    // @GetMapping(BASE_PATH + "food_diary/view/{patientMID}")
    // public ResponseEntity List<FoodDiary> findByIdContains(
    // @PathVariable("patientMID") Long id ) {
    // final List<FoodDiary> f = (List<FoodDiary>) foodDiaryService.findById( id );
    // return ResponseEntity.ok(f);
    // }

    @GetMapping(BASE_PATH + "food_diary/view/{patientMID}/{date}")
    public FoodDiary calculateDailyTotal() {
        final FoodDiary dailyTotal =
        foodDiaryService.calculateDailyTotal(@PathVariable("patientMID") Long id, @PathVariable("date") Date date);
    return dailyTotal;
    }

    // @GetMapping(BASE_PATH + "food_diary/view/{patientMID}/{date}/{mealType}")
    // public ResponseEntity List<FoodDiary> viewEntries(
    // @PathVariable("patientMID") Long id, @PathVariable("date") Date date,
    // @PathVariable("mealType") String mealType) {
    // final List<FoodDiary> f = (List<FoodDiary>)
    // foodDiaryService.findByIdDateContains( id, date );
    // return ResponseEntity.ok(f);
    // }

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
}
