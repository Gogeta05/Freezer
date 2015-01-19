package models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MessageBoxTest {

	private MessageBox msgBox;
	private Message msg;
	private User from;
	private User to;
	private Message msg1;
	private Message msg2;
	
	@Before
	public void setUp() throws Exception {
		msgBox = new MessageBox();
		User from = new User("alfons", "alfons2@mailnesia.com", "most_secure", 12, 'm');
		User to = new User("klaus", "klaus23@mailnesia.com", "secret", 56, 'm');
		msg1 = new Message(from, to, "test");
		msg2 = new Message(from, to, "bla");
		msgBox.messages.add(msg1);
		msgBox.messages.add(msg2);
	}

	@Test
	public void testDeleteMsg() {
		msgBox.deleteMsg(msg1);
		assertEquals(msgBox.messages.size(), 1);
	}
	
	@Test
	public void testDeleteAll() {
		msgBox.deleteAll();
		assertTrue(msgBox.messages.isEmpty());
	}

}
