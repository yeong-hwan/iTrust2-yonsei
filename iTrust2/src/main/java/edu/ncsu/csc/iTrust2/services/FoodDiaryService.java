package edu.ncsu.csc.iTrust2.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.repositories.FoodDiaryRepository;

@Component
@Transactional
public class FoodDiary extends Service {

    @Autowired
	private FoodDiaryRepository repository;
	
	@Override
    protected JpaRepository getRepository () {
        return repository;
    }
	
	// find by patient or HCP?

    public List<FoodDiaryEntry> getAllEntries() {return repository.findAllByOrderByDateDesc();}

    public FoodDiaryEntry getEntryById(Long id) {return repository.findById(id).orElse(null);}

    public FoodDiaryEntry addEntry(FoodDiaryEntry entry) {return repository.save(entry);}
