package edu.ncsu.csc.iTrust2.models;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;


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
	public FoodDiary () {
		
	}
}
