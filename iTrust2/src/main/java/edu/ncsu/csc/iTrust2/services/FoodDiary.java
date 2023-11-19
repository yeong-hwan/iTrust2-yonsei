package edu.ncsu.csc.iTrust2.services;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.repositories.FoodDiaryRepository;

@Component
@Transactional
public class FoodDiary extends Service {
	private FoodDiaryRepository repository;
	
	@Override
    protected JpaRepository getRepository () {
        return repository;
    }
	
	// find by patient or HCP?
}
