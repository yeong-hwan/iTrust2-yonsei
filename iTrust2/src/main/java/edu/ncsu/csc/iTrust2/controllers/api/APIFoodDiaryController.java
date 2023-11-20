package edu.ncsu.csc.iTrust2.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import edu.ncsu.csc.iTrust2.forms.FoodDiaryForm;
import edu.ncsu.csc.iTrust2.models.FoodDiary;
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
    
    /**
     * Retrieves a list of all FoodDiaries in the DB
     * 
     * @return list of food diaries
     */
    @GetMapping ( BASE_PATH + "/fooddiary/{id}" )
    public List<FoodDiary> getFoodDiaryEntry () {
    	loggerUtil.log( TransactionType.PATIENT_VIEW_FOOD_DIARY_ENTRY, LoggerUtil.currentUser() );
//    	return (List<FoodDiary>) // = foodDiaryService.
		return null;
    }
    
    /**
     * Retrieves a list of certain patient's FoodDiaries in the DB.
     * 
     * @return list of certain patient's food diaries
     */
//    @GetMapping ( BASE_PATH + "/fooddiary/HCP/{id}" )
    @GetMapping ( BASE_PATH + "/fooddiary/HCP/{id}" )
    public List<FoodDiary> getFoodDiaryEntryForHCP () {
    	final User self = userService.findByName( LoggerUtil.currentUser() );
    	loggerUtil.log( TransactionType.HCP_VIEW_FOOD_DIARY_ENTRY, self );
//    	final List<FoodDiary> diaries // = foodDiaryService.
//    	return diaries
		return null;
    }
    
    
    
    
    /**
     * Returns a single food diary using the given id.
     * 
     * @param id
     * 
     * @return the requested food diary
     */
    
//    @GetMapping ( BASE_PATH + "/fooddiary/{id}" )
//    @PreAuthorize ( "hasAnyRole('ROLE_PATIENT', 'ROLE_HCP)" )
//    public ResponseEntity getFoodDiary ( @PathVariable final Long id ) {
//    	final FoodDiary f = (FoodDiary) foodDiaryService.findById( id );
//    	
//    	if (f == null ) {
//    		loggerUtil.log( TransactionType.PRESCRIPTION_VIEW, LoggerUtil.currentUser(),
//    				"Failed to find food diary with id" + id );
//    		return new ResponseEntity (errorResponse("No food diary found for " + id), "Viewed food diary " + id );
//    	}
//    }
//    
    

//    @GetMapping
//    public ResponseEntity<List<FoodDiary>> getAllEntries() {
//        return ResponseEntity.ok(foodDiaryService.getAllEntries());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<FoodDiary> getEntryById(@PathVariable Long id) {
//        FoodDiaryEntry entry = foodDiaryService.getEntryById(id);
//        if (entry == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(entry);
//    }
//
    @PostMapping
    public ResponseEntity<FoodDiary> addEntry(@RequestBody FoodDiary entry) {
       return ResponseEntity.ok(foodDiaryService.addEntry(entry));
    }
}
