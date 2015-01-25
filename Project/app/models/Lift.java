package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Lift extends Model {
	
	@Id
	@GeneratedValue
	private long id; 	//Use id of xls file instead?
	
	/**
	 * The name of the lift.
	 */
	public String name;
	/**
	 * The location of the lift, which contains the PLZ as integer and the corresponding municipality as String.
	 */
	public Location location;
	/**
	 * The location of the Lift as postal code
	 */
	public int locationPostal;
	/**
	 * Defines the type of the lift.s
	 */
	public String type;
	/**
	 * The number of seats available when using this lift.
	 */
	public int maxPersons;
	/**
	 * Determines whether the seats are heated.
	 */
	public boolean seatHeating;
	/**
	 * The kind of weather protection used.
	 */
	public String weatherProtection;
	
	/**
	 * @param name The name of the lift
	 * @param location The location of the lift
	 * @param type The type of lift
	 * @param maxPersons The maximum number of persons this lift can carry at once
	 * @param seatHeating If there is seat-heating
	 * @param weatherProtection Specifies the kind of weather protection, if any
	 */
	public Lift(String name, Location location, String type, int maxPersons, boolean seatHeating, String weatherProtection) {
		this.name = name;
		this.location = location;
		this.locationPostal = location.PLZ;
		this.type = type;
		this.maxPersons = maxPersons;
		this.seatHeating = seatHeating;
		this.weatherProtection = weatherProtection;
	}
	
	

}