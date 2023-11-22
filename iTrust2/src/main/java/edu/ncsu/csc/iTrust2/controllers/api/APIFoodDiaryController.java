package edu.ncsu.csc.iTrust2.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import java.text.SimpleDateFormat;

@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
public class APIFoodDiaryController extends APIController {

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private FoodDiaryService foodDiaryService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    /*
     * @yewon added 11.21
     * Create a new food diary entry for the patient with the given FoodDiary form
     */
    @PostMapping(BASE_PATH + "/food_diary/add")
    public ResponseEntity addEntry(@RequestBody FoodDiaryForm entry) {
        // return ResponseEntity.ok(foodDiaryService.addEntry(entry));
        // final User self = userService.findByName( LoggerUtil.currentUser() );
        try {
            // System.out.println(entry);

            final FoodDiary foodDiary = new FoodDiary(entry);

            final User self = userService.findByName(LoggerUtil.currentUser());
            foodDiary.setUsername(self.getUsername());
            foodDiary.setId((long) 1);

            foodDiaryService.save(foodDiary);
            // =====================================
            // !! You should add FOOD_DIARY_ADD to TransactionType.java !!
            // =====================================
            return new ResponseEntity(foodDiary, HttpStatus.OK);
            // loggerUtil.log( TransactionType.FOOD_DIARY_ADD, LoggerUtil.currentUser(),
            // "Added food diary entry for " + entry.getUsername() );
        } catch (final Exception e) {
            return new ResponseEntity(
                    errorResponse("Could not create " + entry.getUsername() + " because of " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(BASE_PATH + "food_diary/view")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientService.findByNameContains("");
        return ResponseEntity.ok(patients);
    }

    @GetMapping(BASE_PATH + "food_diary/view/{patientMID}")
    public ResponseEntity findByUsernameContains(@PathVariable("patientMID") String username) {
        final List<FoodDiary> foodDiaryList = (List<FoodDiary>) foodDiaryService.findByUsernameContains(username);
        return ResponseEntity.ok(foodDiaryList);
    }

    // @GetMapping(BASE_PATH + "food_diary/view/{date}")
    // public FoodDiary calculateDailyTotal(@PathVariable("date") String date) {
    // final User self = userService.findByName(LoggerUtil.currentUser());
    // String username = self.getUsername();

    // final FoodDiary dailyTotal = foodDiaryService.calculateDailyTotal(username,
    // date);
    // return dailyTotal;
    // }

    @GetMapping(BASE_PATH + "food_diary/view/{patientMID}/{date}")
    public FoodDiary calculateDailyTotal(@PathVariable("patientMID") String username,
            @PathVariable("date") String date) {
        final FoodDiary dailyTotal = foodDiaryService.calculateDailyTotal(username,
                date);
        return dailyTotal;
    }

    @GetMapping(BASE_PATH + "food_diary/view/{patientMID}/{date}/{mealType}")
    public ResponseEntity<List<FoodDiary>> viewEntries(@PathVariable("patientMID") String username,
            @PathVariable("date") String date, @PathVariable("mealType") String mealType) {
        List<FoodDiary> entries = foodDiaryService.findByUsernameAndDateContains(username, date);
        return ResponseEntity.ok(entries);
    }

}
