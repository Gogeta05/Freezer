package models;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.junit.Before;
import org.junit.Test;

import play.db.ebean.Model;
import utils.Util;

public class UserTest {
	private User user;
	
	@Before
	public void setUp() throws Exception {
		try {
			user = new User("VirginDestroyer69", "still@virg.in", "sadpanda", 42, 'm');
		} catch (Exception e) {
			fail ("error in updateInterests()");
		}
	}
	
	@Test
	public void checkConsistencyOfSpreadsheet() {
		Workbook workbook = null;
		File xls = null;
		try {
			xls = new File (Util.file_Interests);
			workbook = Workbook.getWorkbook(xls);
		} catch (BiffException | IOException e) {
			fail("File not found, searched in " + xls.getAbsolutePath());  
		}
		Sheet sheet = workbook.getSheet(0);
		
		if ( (!sheet.getCell(0,1).getContents().equals("")) || (!sheet.getCell(1,1).getContents().equals(""))) {
			fail("xls format fail : row 2 should be empty");
		}
			
		for (int i = 1; i < sheet.getRows(); i++) {
			String arr = sheet.getCell(1, i).getContents();
			String[] items = arr.split(Util.seperator_Interests);
			
			for (String s : items) {
				try {
					if (! s.equals("")) {
						Integer.parseInt(s);
					}
				} catch (NumberFormatException nfe) {
					fail("Format error in column B of " + xls.getName());
				}
			}
		}
	}
	
	@Test
	public void testUpdateInterests() {
		try {
			user.updateInterests();
			
			//adds recursive subInterests
			ArrayList<Interests> tmp = new ArrayList<>();
			tmp.add(new Interests("bull", null));
			tmp.add(new Interests("otterly", null));
			
			//add new interest with subinterests, in case that the initial list is empty
			user.interests.add(new Interests("tmpo", tmp));
			
			ArrayList<Interests> sub = user.interests.get(0).getSubInterests();
			
			//test on size update (due to removing/adding)
			int oldsize = sub.size();
			user.interests.get(0).addSubInterest(new Interests("on", tmp));
			
			assertTrue(sub.size() == oldsize + 1);
			user.updateInterests();
			assertTrue(sub.size() == oldsize);
			
			oldsize = sub.size();
			sub.remove(sub.size() - 1);
			
			assertTrue(sub.size() == oldsize - 1);
			user.updateInterests();
			assertTrue(sub.size() == oldsize);
			
			//add new interest with subinterests, in case that the initial list is empty
			user.interests.add(new Interests("tmpo", tmp));
			
			//test on title update
			String oldTitle = user.interests.get(0).getTitle();
			user.interests.get(0).setTitle("HEEEEEEEYAAA");
			user.updateInterests();
			
			assertEquals(oldTitle, user.interests.get(0).getTitle());
			
			
		} catch (Exception e) {
			fail ("error in testUpdateInterests()");
		}
	}
	
	@Test
	public void testResetInterests() {
		//adds recursive subInterests
		ArrayList<Interests> tmp = new ArrayList<>();
		tmp.add(new Interests("bull", null));
		tmp.add(new Interests("otterly", null));
		
		//add new interest with subinterests in case that the initial list is empty
		user.interests.get(0).addSubInterest(new Interests("on", tmp));
		ArrayList<Interests> sub = user.interests.get(0).getSubInterests();
		sub = sub.get(sub.size()-1).getSubInterests();
		
		//check if resetting is working properly (and recursively)
		assertTrue(!sub.get(sub.size()-1).isOn());
		sub.get(sub.size()-1).turnOn();
		assertTrue(sub.get(sub.size()-1).isOn());
		user.resetInterests();
		assertTrue(!sub.get(sub.size()-1).isOn());
	}

	@Test
	public void testMessaging() {
		User usr = new User("huehue", "ho@ho.ho", "adad", 13, 'f');
		assertTrue (usr.msgBox.messages.size() == 0);
		/* NOTE: further testing (the comments afterwards) requires proper user management and registration of users via the website
		 * 
		 */
		
		//user.sendMsg("I will DESTROY you", usr);
		//assertTrue (usr.msgBox.messages.size() == 1);
		
		//assertEquals(usr.msgBox.messages.get(0).msg,"I will DESTROY you");
		//assertEquals(usr.msgBox.messages.get(0).from,user);
		//assertEquals(usr.msgBox.messages.get(0).to,usr);
	}
}
