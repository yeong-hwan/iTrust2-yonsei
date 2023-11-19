package edu.ncsu.csc.iTrust2.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class APIFoodDiaryController extends APIController {

    @Autowired
    private FoodDiaryService foodDiaryService;

    @GetMapping
    public ResponseEntity<List<FoodDiaryEntry>> getAllEntries() {
        return ResponseEntity.ok(foodDiaryService.getAllEntries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDiaryEntry> getEntryById(@PathVariable Long id) {
        FoodDiaryEntry entry = foodDiaryService.getEntryById(id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entry);
    }

    @PostMapping
    public ResponseEntity<FoodDiaryEntry> addEntry(@RequestBody FoodDiaryEntry entry) {
        return ResponseEntity.ok(foodDiaryService.addEntry(entry));
    }
}
