package models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import backend.Database;
import play.Logger;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import utils.Util;

@Entity
public class User extends Model {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Required
	private String username;
	@Required
	private String email;
	@Required
	private String password;
	
	private String firstName;
	private String lastName;
	private Integer age;
	private char gender;
	private Location location;
	public MessageBox msgBox;
	@OneToMany(cascade=CascadeType.ALL)
	public ArrayList<Interests> interests;
	
	/**
	 * The Constructor
	 * @param username
	 * @param email
	 * @param password
	 * @param age
	 * @param gender
	 */
	public User(String username, String email, String password, Integer age, char gender) {
		try {
			
			this.username = username;
			this.email = email;
			this.password = Util.encrypter.encryptPassword(password);
			this.firstName = "";
			this.lastName = "";
			this.age = age;
			this.gender = gender;
			this.location = null;
			this.interests = null;
			this.msgBox = new MessageBox();
			updateInterests();
			
		} catch (Exception e) {
			Logger.info("an exception has occurred at user creation: " + e);
		}
	}
	
	/* Getters */
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getName() {
		return firstName + " " + lastName;
	}
	public Integer getAge() {
		return age;
	}
	public char getGender() {
		return gender;
	}
	public String getGenderString() {
		if (gender == 'm') {
			return "Male";
		}
		return "Female";
	}
	public Location getLocation() {
		return location;
	}
	
	/* Setters */
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = Util.encrypter.encryptPassword(password);
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public void setFirstName(String name) {
		this.firstName = name;
	}
	public void setLastName(String name) {
		this.firstName = name;
	}


	/* Methods */
	
	private class XlsParser {
		//The seperator used in the file
		String seperator;
		//The first row that should be read (= start+1)
		int start;
		//the xls file to be parsed
		File xls;
		//the rows that were already read (true == already read), allows for some row-jumping
		boolean[] readRows;
		
		//Constructor
		public XlsParser(int start, String filepath) {
			this.seperator = Util.seperator_Interests;
			this.start = start;
			this.xls = new File(filepath);
			this.readRows = null;
		}
		
		//parse the xls file
		public ArrayList<Interests> parseInterests() {
			try {
				
				Workbook workbook = Workbook.getWorkbook(xls);
				Sheet sheet = workbook.getSheet(0);
				ArrayList<Interests> parsed = new ArrayList<>();
				readRows = new boolean[sheet.getRows()];
				
				// start from row <start+1> until end of table
				for (int r = start; r < readRows.length; r++) {
					
					//skip already read rows
					if (readRows[r]) {
						continue;
					}
					
					//add the interest with all its sub-interests
					parsed.add(parseInterest(r, sheet));
				}
				
				if (parsed.isEmpty()) {
					parsed = null;
				}
				return parsed;
				
			} catch (BiffException | IOException e) {return null;}

		}

		//recursive sub method of parseInterests()
		private Interests parseInterest(int r, Sheet sheet) {
			
			//read the title
			String title = sheet.getCell(0, r).getContents();
			
			//get the sub-interests
			String arr = sheet.getCell(1, r).getContents();
			
			String[] items = arr.split(seperator);
			ArrayList<Interests> subs = null;
			
			if (! (items[0].equals("")) ) {
				subs = new ArrayList<>();
		
				for (int i = 0; i < items.length; i++) {
					try {
						int subcell = Integer.parseInt(items[i]);
						subs.add(parseInterest(subcell-1,sheet));
						
					} catch (NumberFormatException nfe) {};
					
				}
			}
			
			//mark the row as read
			readRows[r] = true;
			
			//return the parsed interest
			return (new Interests(title, subs));
		}
	}
	
	/**
	 * update the current interests of the user with the .xls file (useful when changing the file contents)
	 */
	public void updateInterests() {
		
		//if there was no list saved for the user, just save the new one
		if (interests == null) {
			interests = new XlsParser(2, Util.file_Interests).parseInterests();
		}
		
		//otherwise update the old one with the new one
		else {
			ArrayList<Interests> parsed = new XlsParser(2, Util.file_Interests).parseInterests();
			rUpdateInterests(this.interests, parsed);
		}
	}
	
	//recursive submethod of updateInterests()
	private void rUpdateInterests(ArrayList<Interests> thiss, ArrayList<Interests> that) {
		//null check
		if (that == null) {
			thiss = null;
			return;
		}
		else if (thiss == null) {
			thiss = new ArrayList<Interests>();
		}
		
		//update subinterests
		int thisSize = thiss.size();
		int thatSize = that.size();
		
		int diff = thisSize - thatSize;
		
		//some Interests were deleted in the file
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				thiss.remove(thisSize-1);
				thisSize -= 1;
			}
		}
		
		//some Interests were added in the file
		else if (diff < 0) {
			diff = diff *(-1);
			for (int i = 0; i < diff; i++) {
				thiss.add(that.get(thatSize-1));
				thatSize -= 1;
			}
		}
		
		//update the remaining interests
		for (int i = 0; i < thatSize; i++) {
			Interests thisI = thiss.get(i);
			Interests thatI = that.get(i);
			
			//update the title (note that the setting gets reset if the title has to be updated)
			if (! (thisI.getTitle().equals(thatI.getTitle()))) {
				thisI.setTitle(thatI.getTitle());
				thisI.turnOff();
			}
			
			//don't forget the sub-Interests
			rUpdateInterests(thisI.getSubInterests(), thatI.getSubInterests());
		}
	}
	
	/**
	 * set all interests to off
	 */
	public void resetInterests() {
		for (Interests i : interests) {
			i.turnOff();
			ArrayList<Interests> subs = i.getSubInterests();
			if (subs != null) {
				rResetInterests(subs);
			}
		}
	}
	private void rResetInterests(ArrayList<Interests> interests) {
		for (Interests i : interests) {
			i.turnOff();
			ArrayList<Interests> subs = i.getSubInterests();
			if (subs != null) {
				rResetInterests(subs);
			}
		}
	}
	
	/**
	 * receive a message
	 */
	public void recieveMsg (Message msg) {
		this.msgBox.messages.add(msg);
	}
	
	/**
	 * send a message to another user
	 */
	public void sendMsg (String msg, User to) {
		Database.getUser(to.getUsername()).recieveMsg(new Message(this, to, msg));
	}
	
	//Hier wird (auf absolut sichere Art und Weise) überprüft, ob der Benutzer existiert
    public static User authenticate(String email, String password) {
    	
        User usr = new Model.Finder<>(String.class, User.class).where().eq("email", email).findUnique(); 
    	if(usr != null && Util.encrypter.checkPassword(password, usr.getPassword())) {
    		return usr;
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * shallow matching algorithm (only matching top level of interests)
     * every top-level hit adds 1 to the matching
     * @param matchCriteria only match Users which matching at least succeeds this value
     * @return List of Users which fulfuill the matching criteria
     */
    public List<User> matching(int matchCriteria) {
    	
    	List<User> users = Database.getUsers();
    	List<User> matched = new ArrayList<>();
    	
    	int i = 0;
    	
    	for (User u : users) {
    		int match_count = 0;
    		List<Interests> thatInterests = u.interests;
    		
    		for (Interests in : interests) {
    			if (in.isOn() == thatInterests.get(i).isOn()) {
    				match_count += 1;
    			}
    			i += 1;
    		}
    		
    		if (match_count >= matchCriteria) {
    			matched.add(u);
    		}
    	}
    	
    	return matched;
    }
}
