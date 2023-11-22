package edu.ncsu.csc.iTrust2.models;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

import edu.ncsu.csc.iTrust2.forms.FoodDiaryForm;

@Entity
public class FoodDiary extends DomainObject {

	/**
	 * Construct an empty Food Diary. Used for Hibernate.
	 */
	public FoodDiary() {
	}

	/**
	 * Construct a Food Diary object from the foodDiaryForm object provided
	 *
	 * @param form
	 *             A FoodDiaryForm to convert to a FoodDiary
	 */
	public FoodDiary(final FoodDiaryForm form) {
		setUsername(form.getUsername());
		setId(form.getId());
		setDate(form.getDate());
		setMealType(form.getMealType());
		setFoodName(form.getFoodName());
		setServingNumber(form.getServingNumber());
		setCaloriesPerServing(form.getCaloriesPerServing());
		setFatPerServing(form.getFatPerServing());
		setSodiumPerServing(form.getSodiumPerServing());
		setCarbsPerServing(form.getCarbsPerServing());
		setSugarsPerServing(form.getSugarsPerServing());
		setFiberPerServing(form.getFiberPerServing());
		setProteinPerServing(form.getProteinPerServing());
	}

	// @NotNull
	// @ManyToOne(cascade = CascadeType.ALL)
	// @JoinColumn(name = "patient_id", columnDefinition = "varchar(100)")
	// private User patient;

	// private String username;
	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Date of Diaries
	 * PastOrPresent가 있어서 null값 못 받아요
	 */
	// @Temporal(TemporalType.DATE)
	// @DateTimeFormat(pattern = "mm/dd/yyyy")
	// @PastOrPresent(message = "Date must be in the past or present")
	private String date;

	/**
	 * Meal type
	 * annotation 잘 모르겠어서 테스트 해봐야 할 것 같네요,,ㅎㅎ
	 */
	@NotNull(message = "Type of meal is required")
	@Pattern(regexp = "Breakfast|Lunch|Dinner|Snack", message = "Meal type must be one of 'Breakfast', 'Lunch', 'Dinner', or 'Snack'")
	private String mealType;

	@NotBlank(message = "name of food should not be empty")
	private String foodName;

	@Positive(message = "Number of Servings should be a positive number")
	private long servingNumber;

	@PositiveOrZero(message = "Calories per serving must be Zero or a positive number")
	private long caloriesPerServing;

	@PositiveOrZero(message = "Grams of fat per serving must be Zero or a positive number")
	private long fatPerServing;

	@PositiveOrZero(message = "Milligrams of sodium per serving must be Zero or a positive number")
	private long sodiumPerServing;

	@PositiveOrZero(message = "Grams of carbs per serving must be Zero or a positive number")
	private long carbsPerServing;

	@PositiveOrZero(message = "Grams of sugars per serving must be Zero or a positive number")
	private long sugarsPerServing;

	@PositiveOrZero(message = "Grams of fiber per serving must be Zero or a positive number")
	private long fiberPerServing;

	@PositiveOrZero(message = "Grams of protein per serving must be Zero or a positive number")
	private long proteinPerServing;

	private String username;

	/**
	 * getter methods
	 */
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
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
	public void setId(final Long id) {
		this.id = id;
	}

	public void setUsername(final String username) {
		this.username = username;
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
