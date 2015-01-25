package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Settings extends Model {
	
	@Id
	@GeneratedValue
	public int id;
	
	@OneToOne
	@JoinColumn(name = "FK_OWNER")
	User owner;
	
	public String comparator;
	public boolean male;
	public boolean female;
	public int age;
	public boolean allowMatching;
	public boolean matchAroundLift;
	
	public Settings(User owner) {
		this.owner = owner;
		this.male = true;
		this.female = true;
		this.comparator = "gt";
		this.age = 0;
		this.allowMatching = false;
		this.matchAroundLift = false;
	}
	
	public void resetSettings() {
		this.male = true;
		this.female = true;
		this.comparator = "gt";
		this.age = 0;
		this.allowMatching = false;
		this.matchAroundLift = false;
		
		this.save();
	}
	
	
	
}
