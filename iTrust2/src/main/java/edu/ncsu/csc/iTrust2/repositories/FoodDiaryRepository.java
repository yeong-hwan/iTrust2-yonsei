package edu.ncsu.csc.iTrust2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.iTrust2.models.FoodDiary;

public class FoodDiaryRepository extends JpaRepository<FoodDiary, Long> { 
    public List<FoodDiary> findByHcp ( User hcp );

    public List<FoodDiary> findByPatient ( User patient );

    public List<FoodDiaryEntry> findAllByOrderByDateDesc();

}
