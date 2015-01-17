package models;

public class Location {

	public int PLZ;
	public String municipality; 
	
	public Location(int PLZ, String municipality) {
		this.PLZ = PLZ;
		this.municipality = municipality;
	}
}