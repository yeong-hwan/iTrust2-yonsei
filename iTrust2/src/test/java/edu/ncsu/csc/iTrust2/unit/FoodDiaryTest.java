package edu.ncsu.csc.iTrust2.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.iTrust2.TestConfig;
import edu.ncsu.csc.iTrust2.forms.FoodDiaryForm;
import edu.ncsu.csc.iTrust2.models.FoodDiary;
import edu.ncsu.csc.iTrust2.services.FoodDiaryService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = TestConfig.class)
public class FoodDiaryTest {

  @Autowired
  private FoodDiaryService service;

  private static final String TEST_USER = "demoTestUser";

  @Test
  @Transactional
  public void testFoodDiary() {

    final FoodDiary base = new FoodDiary();
    base.setId((long) 1);
    base.setUsername(TEST_USER);
    base.setDate("2023-11-23");
    base.setMealType("Lunch");
    base.setFoodName("food");
    base.setServingNumber((long) 2);
    base.setCaloriesPerServing((long) 100);
    base.setFatPerServing((long) 10);
    base.setSodiumPerServing((long) 10);
    base.setCarbsPerServing((long) 20);
    base.setSugarsPerServing((long) 10);
    base.setFiberPerServing((long) 5);
    base.setProteinPerServing((long) 30);

  }
  @Test
  @Transactional
  public void testFoodDiaryForm() {

    final FoodDiary base = new FoodDiary();
    base.setId((long) 1);
    base.setUsername(TEST_USER);
    base.setDate("2023-11-23");
    base.setMealType("Lunch");
    base.setFoodName("food");
    base.setServingNumber((long) 2);
    base.setCaloriesPerServing((long) 100);
    base.setFatPerServing((long) 10);
    base.setSodiumPerServing((long) 10);
    base.setCarbsPerServing((long) 20);
    base.setSugarsPerServing((long) 10);
    base.setFiberPerServing((long) 5);
    base.setProteinPerServing((long) 30);

    FoodDiaryForm form = new FoodDiaryForm(base);
  }



  // @Test
  // @Transactional
  // public void testTotalNutrition() {
  // final FoodDiary foodDiary_1 = new FoodDiary();
  // foodDiary_1.setId((long) 1);
  // foodDiary_1.setUsername(TEST_USER);
  // foodDiary_1.setDate("2023-11-23");
  // foodDiary_1.setMealType("Lunch");
  // foodDiary_1.setServingNumber((long) 2);
  // foodDiary_1.setCaloriesPerServing((long) 100);
  // foodDiary_1.setFatPerServing((long) 10);
  // foodDiary_1.setSodiumPerServing((long) 10);
  // foodDiary_1.setCarbsPerServing((long) 20);
  // foodDiary_1.setSugarsPerServing((long) 10);
  // foodDiary_1.setFiberPerServing((long) 5);
  // foodDiary_1.setProteinPerServing((long) 30);

  // final FoodDiary foodDiary_2 = new FoodDiary();
  // foodDiary_2.setId((long) 2);
  // foodDiary_2.setUsername(TEST_USER);
  // foodDiary_2.setDate("2023-11-23");
  // foodDiary_2.setMealType("Lunch");
  // foodDiary_2.setServingNumber((long) 2);
  // foodDiary_2.setCaloriesPerServing((long) 100);
  // foodDiary_2.setFatPerServing((long) 10);
  // foodDiary_2.setSodiumPerServing((long) 10);
  // foodDiary_2.setCarbsPerServing((long) 20);
  // foodDiary_2.setSugarsPerServing((long) 10);
  // foodDiary_2.setFiberPerServing((long) 5);
  // foodDiary_2.setProteinPerServing((long) 30);

  // service.save(foodDiary_1);
  // service.save(foodDiary_2);

  // final FoodDiary totalNutrion = service.calculateDailyTotal(TEST_USER,
  // "2023-11-23");

  // assertEquals((long) totalNutrion.getCarbsPerServing(), 40);
  // }

}
