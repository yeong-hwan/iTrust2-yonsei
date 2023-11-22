package edu.ncsu.csc.iTrust2.forms;

import java.util.Date;

import edu.ncsu.csc.iTrust2.models.FoodDiary;
import edu.ncsu.csc.iTrust2.models.User;

/**
 * * A form for REST API communication. Contains fields for constructing
 * FoodDiary objects.
 * 
 * @author jang-yeonghwan
 */
public class FoodDiaryForm {

	private String username;
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
	 * 
	 * @param foodDiary
	 */
	public FoodDiaryForm(final FoodDiary foodDiary) {
		setUsername(foodDiary.getUsername());
		setId(foodDiary.getId());
		setDate(foodDiary.getDate());
		setMealType(foodDiary.getMealType());
		setFoodName(foodDiary.getFoodName());
		setServingNumber(foodDiary.getServingNumber());
		setCaloriesPerServing(foodDiary.getCaloriesPerServing());
		setFatPerServing(foodDiary.getFatPerServing());
		setSodiumPerServing(foodDiary.getSodiumPerServing());
		setCarbsPerServing(foodDiary.getCarbsPerServing());
		setSugarsPerServing(foodDiary.getSugarsPerServing());
		setFiberPerServing(foodDiary.getFiberPerServing());
		setProteinPerServing(foodDiary.getProteinPerServing());
	}

	/**
	 * getter methods
	 */

	public String getUsername() {
		return username;
	}

	public Long getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getMealType() {
		return mealType;
	}

	public String getFoodName() {
		return foodName;
	}

	public Long getServingNumber() {
		return servingNumber;
	}

	public Long getCaloriesPerServing() {
		return caloriesPerServing;
	}

	public Long getFatPerServing() {
		return fatPerServing;
	}

	public Long getSodiumPerServing() {
		return sodiumPerServing;
	}

	public Long getCarbsPerServing() {
		return carbsPerServing;
	}

	public Long getSugarsPerServing() {
		return sugarsPerServing;
	}

	public Long getFiberPerServing() {
		return fiberPerServing;
	}

	public Long getProteinPerServing() {
		return proteinPerServing;
	}

	/**
	 * setter methods
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public void setMealType(final String mealType) {
		this.mealType = mealType;
	}

	public void setFoodName(final String foodName) {
		this.foodName = foodName;
	}

	public void setServingNumber(final Long servingNumber) {
		this.servingNumber = servingNumber;
	}

	public void setCaloriesPerServing(final Long caloriesPerServing) {
		this.caloriesPerServing = caloriesPerServing;
	}

	public void setFatPerServing(final Long fatPerServing) {
		this.fatPerServing = fatPerServing;
	}

	public void setSodiumPerServing(final Long sodiumPerServing) {
		this.sodiumPerServing = sodiumPerServing;
	}

	public void setCarbsPerServing(final Long carbsPerServing) {
		this.carbsPerServing = carbsPerServing;
	}

	public void setSugarsPerServing(final Long sugarsPerServing) {
		this.sugarsPerServing = sugarsPerServing;
	}

	public void setFiberPerServing(final Long fiberPerServing) {
		this.fiberPerServing = fiberPerServing;
	}

	public void setProteinPerServing(final Long proteinPerServing) {
		this.proteinPerServing = proteinPerServing;
	}
}