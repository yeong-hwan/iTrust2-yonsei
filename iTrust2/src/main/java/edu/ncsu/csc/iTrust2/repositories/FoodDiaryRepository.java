package edu.ncsu.csc.iTrust2.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import edu.ncsu.csc.iTrust2.models.FoodDiary;
import edu.ncsu.csc.iTrust2.models.User;

/**
 * This is the repository for interacting with the Food Diary table
 * - findAllByUsername
 * - findAllByDate
 * - findAllByMealType
 * 
 * Expecting these functions to be automatsically implemented by Spring JPA.
 */
public interface FoodDiaryRepository extends JpaRepository<FoodDiary, Long> {

  List<FoodDiary> findAllByUsernameContaining(String username);

  List<FoodDiary> findAllByDateContaining(String date);

  List<FoodDiary> findAllByMealTypeContaining(String mealType);

  List<FoodDiary> findAllByUsernameAndDateContaining(String id, String date);
}
