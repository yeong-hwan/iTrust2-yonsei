package edu.ncsu.csc.iTrust2.forms;

import edu.ncsu.csc.iTrust2.models.FoodDiary;

/**
 * * A form for REST API communication. Contains fields for constructing
 * FoodDiary objects.
 * 
 * @author jang-yeonghwan
 */
public class FoodDiaryForm {
	
	private Long id;
	private String date;
	private String mealType;
	private String foodName;
	private Long servingNumber;
	private Long caloriesPerServing;
	private Long fatPerServing;
	private Long sodiumPerServing;
	private Long carbsPerServing;
	private Long sugarsPerServing;
	private Long fiberPerServing;
	private Long proteinPerServing;
	
	/**
     * Empty constructor for filling in fields without a Drug object.
     */
	public FoodDiaryForm() {
	}
	
	/**
	 * Constructs a new form with information from the given FoodDiary.
	 * @param foodDiary
	 */
	public FoodDiaryForm(final FoodDiary foodDiary) {
		setId( foodDiary.getId() );
		setDate( foodDiary.getDate() );
		setMealType( foodDiary.getMealType() );
		setFoodName( foodDiary.getFoodName() );
		setServingNumber( foodDiary.getServingNumber() );
		setCaloriesPerServing( foodDiary.getCaloriesPerServing() );
		setFatPerServing( foodDiary.getFatPerServing() );
		setSodiumPerServing( foodDiary.getSodiumPerServing() );
		setCarbsPerServing( foodDiary.getCarbsPerServing() );
		setSugarPerServing( foodDiary.getSugarPerServing() );
		setFiberPerServing( foodDiary.getFiberPerServing() );
		setProteinPerServing( foodDiary.getProteinPerServing() );
	}
	

		
		
}
