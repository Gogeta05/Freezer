package models;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class InterestsTest {
	private static Interests in;
	private static Interests jn;
	private static Interests diff;
	
	@Before
	public void setUp() throws Exception {
		//setup first Interests Object with some recursive subinterests
		ArrayList<Interests> alis = new ArrayList<>();
		alis.add(new Interests("Theology", null));
		alis.add(new Interests("Theologies", null));
		alis.add(new Interests("Policy", null));
		alis.add(new Interests("Polithic", null));
		alis.add(new Interests("Gothic", null));
		
		ArrayList<Interests> aliss = new ArrayList<>();
		alis.add(new Interests("Piranha", null));
		alis.add(new Interests("Shark", null));
		
		ArrayList<Interests> ali = new ArrayList<>();
		ali.add(new Interests("General", alis));
		ali.add(new Interests("Not General", aliss));
		
		in = new Interests("AllQuantor", ali);
		
		//setup second Interests Object (equal to the first one)
		ArrayList<Interests> blis = new ArrayList<>();
		blis.add(new Interests("Theology", null));
		blis.add(new Interests("Theologies", null));
		blis.add(new Interests("Policy", null));
		blis.add(new Interests("Polithic", null));
		blis.add(new Interests("Gothic", null));
		
		ArrayList<Interests> bliss = new ArrayList<>();
		blis.add(new Interests("Piranha", null));
		blis.add(new Interests("Shark", null));
		
		ArrayList<Interests> bli = new ArrayList<>();
		bli.add(new Interests("General", blis));
		bli.add(new Interests("Not General", bliss));
		
		jn = new Interests("AllQuantor", bli);
		
		//setup third Interests Object (not equal to the first two)
		ArrayList<Interests> clis = new ArrayList<>();
		clis.add(new Interests("Theology", null));
		clis.add(new Interests("Theologies", null));
		clis.add(new Interests("Policy", null));
		clis.add(new Interests("This is very different", null));
		clis.add(new Interests("Gothic", null));
		
		ArrayList<Interests> cliss = new ArrayList<>();
		cliss.add(new Interests("Piranha", null));
		cliss.add(new Interests("Shark", null));
		
		ArrayList<Interests> cli = new ArrayList<>();
		cli.add(new Interests("General", clis));
		cli.add(new Interests("Not General", cliss));
		
		diff = new Interests("AllQuantor", cli);

	}
	
	@Test
	public void testEquality() {
		assertTrue(in.equals(jn));
		assertFalse(in.equals(diff));
		assertFalse(jn.equals(diff));
	}

}
