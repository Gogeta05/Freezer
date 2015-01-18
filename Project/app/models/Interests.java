package models;

import java.util.ArrayList;

public class Interests {
	
	/** The title of the interest, as displayed in the HTML later */
	private String title;
	/** The sub-interests of this one */
	private ArrayList<Interests> subInterests;
	/** The User is interested in this interest if on == true */
	private Boolean on;
	
	/**
	 * The Constructor
	 * @param title
	 * @param subs The sub-Interests, use null if none exist
	*/
	public Interests(String title, ArrayList<Interests> subs) {
		this.title = title;
		this.subInterests = subs;
		on = false;
	}

	
	/* Getters */
	public String getTitle() {
		return title;
	}

	public ArrayList<Interests> getSubInterests() {
		return subInterests;
	}

	public Boolean isOn() {
		return on;
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
	 * turn the interest on for the user
	 */
	public void turnOn() {
		this.on = true;
	}
	
	/**
	 * turn the interest off for the user
	 */
	public void turnOff() {
		this.on = false;
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
