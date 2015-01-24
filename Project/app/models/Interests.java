package models;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Interests extends Model {
	
	@Id
	@GeneratedValue
	public long id;
	
	/** The title of the interest, as displayed in the HTML later */
	@Id
	private String title;
	/** The sub-interests of this one */
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<Interests> subInterests;
	/** The User is interested in this interest if on == true */
	private Boolean interestOn;
	
	/**
	 * The Constructor
	 * @param title
	 * @param subs The sub-Interests, use null if none exist
	*/
	public Interests(String title, ArrayList<Interests> subs) {
		this.title = title;
		this.subInterests = subs;
		interestOn = false;
	}

	
	/* Getters */
	public String getTitle() {
		return title;
	}

	public ArrayList<Interests> getSubInterests() {
		return subInterests;
	}

	public Boolean isOn() {
		return interestOn;
	}

	/* Setters */
	public void setTitle(String title) {
		this.title = title;
	}

	public void addSubInterest(Interests sub) {
		this.subInterests.add(sub);
	}

	/* Methods */
	/**
	 * return whether this interest has subinterests or not
	 */
	public boolean hasSubs() {
		return (subInterests != null);
	}
	
	/**
	 * turn the interest on for the user
	 */
	public void turnOn() {
		this.interestOn = true;
	}
	
	/**
	 * turn the interest off for the user
	 */
	public void turnOff() {
		this.interestOn = false;
	}

	@Override
	public boolean equals(Object othat) {
		if ( this == othat ) return true;
		if ( !(othat instanceof Interests) ) return false;
		Interests that = (Interests) othat;
		
		return 
				this.title.equals(that.title) &&
				subEquals(that);
		
	}
	
	//check if the subInterests are equal
	private boolean subEquals(Interests that) {
		//check if any subinterests exist for both
		if (this.subInterests == null) {
			if (that.subInterests == null) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (that.subInterests == null) {
			return false;
		}
		
		//check if size of subinterests is equal
		if (this.subInterests.size() != that.subInterests.size()) return false;
		
		//check if subinterests equal each other respectively
		for (int i = 0; i < subInterests.size(); i++) {
			if (! (this.subInterests.get(i).equals(that.subInterests.get(i)))) {
				return false;
			}
		}
		
		return true;
	}
	
}
