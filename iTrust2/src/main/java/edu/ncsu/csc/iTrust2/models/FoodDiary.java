package edu.ncsu.csc.iTrust2.models;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import edu.ncsu.csc.iTrust2.forms.foodDiaryForm;


@Entity
public class FoodDiary extends DomainObject {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;

	/**
	 * Date of Diaries
	 * PastOrPresent가 있어서 null값 못 받아요
	 */
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "mm/dd/yyyy")
	@PastOrPresent(message = "Date must be in the past or present")
	private Date date;

	/**
	 * Meal type
	 * annotation 잘 모르겠어서 테스트 해봐야 할 것 같네요,,ㅎㅎ
	 */
	@NotNull(message = "Type of meal is required")
	@Pattern(regexp = "Breakfast|Lunch|Dinner|Snack", message = "Meal type must be one of 'Breakfast', 'Lunch', 'Dinner', or 'Snack'")
	private String mealType;
	/**
	 *
	 */
	@NotBlank(message = "name of food should not be empty")
	private String foodName;

	@Positive(message = "Number of Servings should be a positive number")
	private long servings;

	@PositiveOrZero(message = "Calories per serving must be Zero or a positive number")
	private long calories;

	@PositiveOrZero(message = "Grams of fat per serving must be Zero or a positive number")
	private long fat;

	@PositiveOrZero(message = "Milligrams of sodium per serving must be Zero or a positive number")
	private long sodium;

	@PositiveOrZero(message = "Grams of carbs per serving must be Zero or a positive number")
	private long carbs;

	@PositiveOrZero(message = "Grams of sugars per serving must be Zero or a positive number")
	private long sugars;

	@PositiveOrZero(message = "Grams of fiber per serving must be Zero or a positive number")
	private long fiber;

	@PositiveOrZero(message = "Grams of protein per serving must be Zero or a positive number")
	private long protein;
	/**
	 * Construct a Hospital object from all of its individual fields.
	 *
	 * @param name
	 *            Name of the Hospital
	 * @param address
	 *            Address of the Hospital
	 * @param zip
	 *            ZIP of the Hospital
	 * @param state
	 *            State of the Hospital
	 */
	public foodDiary ( final Long id, final Date date, final String mealType, final String foodName, final long servings, final long calories, final long fat, final long sodium, final long carbs, final long sugars, final long fiber, final long protein) {
		setId( id )
		setDate( date )
		setMealType( mealType )
		setFoodName( foodName )
		setServingNumber( servingNumber )
		setCaloriesPerServing( caloriesPerServing )
		setFatPerServing( fatPerServing )
		setSodiumPerServing( sodiumPerServing )
		setCarbsPerServing( carbsPerServing )
		setSugarsPerServing( sugarsPerServing )
		setFiberPerServing( fiberPerServing )
		setProteinPerServing( proteinPerServing )
	}

	/**
	 * Construct an empty Food Diary. Used for Hibernate.
	 */
	public foodDiary () {
	}

	/**
	 * Construct a Food Diary object from the foodDiaryForm object provided
	 *
	 * @param fd
	 *            A FoodDiaryForm to convert to a FoodDiary
	 */
	public foodDiary ( final foodDiaryForm fd ) {
		setId( fd.getId )
		setDate( fd.getDate )
		setMealType( fd.getMealType )
		setFoodName( fd.getFoodName )
		setServingNumber( fd.getServingNumber )
		setCaloriesPerServing( fd.getCaloriesPerServing )
		setFatPerServing( fd.getFatPerServing )
		setSodiumPerServing( fd.getSodiumPerServing )
		setCarbsPerServing( fd.getCarbsPerServing )
		setSugarsPerServing( fd.getSugarsPerServing )
		setFiberPerServing( fd.getFiberPerServing )
		setProteinPerServing(fd.getProteinPerServing )
	}

	/**
	 * Update this Hospital object from the HospitalForm object provided
	 *
	 * @param fd
	 *            A FoodDiaryForm to convert to a FoodDiar
	 * @return `this` FoodDiary object, after updates
	 */
	public foodDiary update ( final foodDiaryForm fd ) {
		setId( fd.getId )
		setDate( fd.getDate )
		setMealType( fd.getMealType )
		setFoodName( fd.getFoodName )
		setServingNumber( fd.getServingNumber )
		setCaloriesPerServing( fd.getCaloriesPerServing )
		setFatPerServing( fd.getFatPerServing )
		setSodiumPerServing( fd.getSodiumPerServing )
		setCarbsPerServing( fd.getCarbsPerServing )
		setSugarsPerServing( fd.getSugarsPerServing )
		setFiberPerServing( fd.getFiberPerServing )
		setProteinPerServing(fd.getProteinPerServing )
		return this;
	}

	/**
	 * getter methods
	 */
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
	public void setId( final Long id ) {
		this.id = id;
	}

	public void setDate( final String date ) {
		this.date = date;
	}

	public void setMealType( final String mealType ) {
		this.mealType = mealType;
	}

	public void setFoodName( final String foodName ) {
		this.foodName = foodName;
	}

	public void setServingNumber( final Long servingNumber ) {
		this.servingNumber = servingNumber;
	}

	public void setCaloriesPerServing( final Long caloriesPerServing ) {
		this.caloriesPerServing = caloriesPerServing;
	}

	public void setFatPerServing( final Long fatPerServing ) {
		this.fatPerServing = fatPerServing;
	}

	public void setSodiumPerServing( final Long sodiumPerServing ) {
		this.sodiumPerServing = sodiumPerServing;
	}

	public void setCarbsPerServing( final Long carbsPerServing ) {
		this.carbsPerServing = carbsPerServing;
	}

	public void setSugarsPerServing( final Long sugarsPerServing ) {
		this.sugarsPerServing = sugarsPerServing;
	}

	public void setFiberPerServing( final Long fiberPerServing ) {
		this.fiberPerServing = fiberPerServing;
	}

	public void setProteinPerServing( final Long proteinPerServing ) {
		this.proteinPerServing = proteinPerServing;
	}
}

}
