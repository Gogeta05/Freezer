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
import javax.persistence.OneToOne;

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
	public long id;
	
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
	private int locationPLZ;
	private String liftName;
	
	@OneToOne(mappedBy="owner", cascade=CascadeType.ALL)
	public MessageBox msgBox;
	
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
	public List<Interests> interests;
	
	@OneToOne(mappedBy="owner", cascade=CascadeType.ALL)
	public Settings settings;
	
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
			this.locationPLZ = -1;
			this.interests = null;
			this.msgBox = new MessageBox(this);
			this.settings = new Settings(this);
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
	public int getLocation() {
		return locationPLZ;
	}
	public String getLiftName() {
		return liftName;
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
	public void setLocation(int locationPLZ) {
		this.locationPLZ = locationPLZ;
	}
	public void setFirstName(String name) {
		this.firstName = name;
	}
	public void setLastName(String name) {
		this.firstName = name;
	}
	public void setLiftName(String name) {
		this.lastName = name;
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
		
		User user;
		
		//Constructor
		public XlsParser(int start, String filepath, User usr) {
			this.seperator = Util.seperator_Interests;
			this.start = start;
			this.xls = new File(filepath);
			this.readRows = null;
			this.user=usr;
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
					parsed.add(parseInterest(r, sheet, null));
				}
				
				if (parsed.isEmpty()) {
					parsed = null;
				}
				return parsed;
				
			} catch (BiffException | IOException e) {return null;}

		}

		//recursive sub method of parseInterests()
		private Interests parseInterest(int r, Sheet sheet, Interests parent) {
			
			//read the title
			String title = sheet.getCell(0, r).getContents();
			
			Interests in = null;
			if (parent == null) {
				in = new Interests(title, null, this.user, parent);
			}
			else {
				in = new Interests(title, null, null, parent);
			}
			
			
			//get the sub-interests
			String arr = sheet.getCell(1, r).getContents();
			String[] items = arr.split(seperator);

			
			if (! (items[0].equals("")) ) {
		
				for (int i = 0; i < items.length; i++) {
					try {
						int subcell = Integer.parseInt(items[i]);
						in.addSubInterest((parseInterest(subcell-1,sheet, in)));
						
					} catch (NumberFormatException nfe) {};
					
				}
			}
			
			//mark the row as read
			readRows[r] = true;
			
			//return the parsed interest
			return (in);
		}
	}
	
	/**
	 * update the current interests of the user with the .xls file (useful when changing the file contents)
	 */
	public void updateInterests() {
		
		//if there was no list saved for the user, just save the new one
		if (interests == null) {
			interests = new XlsParser(2, Util.file_Interests, this).parseInterests();
		}
		
		//otherwise update the old one with the new one
		else {
			List<Interests> parsed = new XlsParser(2, Util.file_Interests, this).parseInterests();
			rUpdateInterests(this.interests, parsed);
		}
	}
	
	//recursive submethod of updateInterests()
	private void rUpdateInterests(List<Interests> thiss, List<Interests> that) {
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
	 * set all interests to off/on
	 */
	public void allInterests(boolean status) {
		for (Interests i : interests) {
			if (status == true) {
				i.turnOn();
			}
			else {
				i.turnOff();
			}
			List<Interests> subs = i.getSubInterests();
			if (subs != null) {
				rAllInterests(status, subs);
			}
		}
	}
	private void rAllInterests(boolean status, List<Interests> interests) {
		if (interests == null) {
			return;
		}
		
		for (Interests i : interests) {
			if (status == true) {
				i.turnOn();
			}
			else {
				i.turnOff();
			}
			List<Interests> subs = i.getSubInterests();
			if (subs != null) {
				rAllInterests(status, subs);
			}
		}
	}
	
	/**
	 * find interest by title recursively
	 */
	public Interests findInterests(String title) {
		if (this.interests == null) {
			return null;
		}
		
		return rfindInterests(title, this.interests);
	}
	//recursive helper function for findInterests
	public Interests rfindInterests(String title, List<Interests> interests) {
		if (interests == null) {
			return null;
		}
		
		for (Interests i : interests) {
			if (i.getTitle().equalsIgnoreCase(title)) {
				return i;
			}
			else if (i.hasSubs()){
				Interests subInterests = rfindInterests(title, i.getSubInterests());
				if (subInterests != null) {
					return subInterests;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * receive a message
	 */
	public void receiveMsg (Message msg) {
		this.msgBox.addMsg(msg);
	}
	
	/**
	 * send a message to another user
	 */
	public void sendMsg (String msg, User to, String time) {
		to.receiveMsg(new Message(this, to, time, msg));
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
    		if (u.id == this.id) {
    			continue;
    		}
    		
    		i = 0;
    		int match_count = 0;
    		List<Interests> thatInterests = u.interests;
    		
    		for (Interests in : interests) {
    			if (in.isOn() == true && thatInterests.get(i).isOn() == true) {
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
