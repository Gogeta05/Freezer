package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Location {

	@Id
	@GeneratedValue
	public long id;

	public int PLZ;
	public String municipality; 
	
	public Location(int PLZ, String municipality) {
		this.PLZ = PLZ;
		this.municipality = municipality;
	}
}