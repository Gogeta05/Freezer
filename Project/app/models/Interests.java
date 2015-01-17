package models;

public class Interests {
	
	/** The title of the interest, as displayed in the HTML later */
	private String title;
	/** The parent interest of which this one is a sub-interest */
	private Interests parent;
	/** The User is interested in this interest if on == true */
	private Boolean on;
	
	/**
	 * The Constructor
	 * @param title
	 * @param parent use null for this argument if no parent exists
	*/
	public Interests(String title, Interests parent) {
		this.title = title;
		this.parent = parent;
		on = false;
	}

	
	/* Getters */
	public String getTitle() {
		return title;
	}

	public Interests getParent() {
		return parent;
	}

	public Boolean isOn() {
		return on;
	}

	/* Setters */
	public void setTitle(String title) {
		this.title = title;
	}

	public void setParent(Interests parent) {
		this.parent = parent;
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


	
}
