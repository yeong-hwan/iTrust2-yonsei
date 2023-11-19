package edu.ncsu.csc.iTrust2.models;

@Entity
public class FoodDiary extends DomainObject {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;
	public FoodDiary () {
		
	}
}
