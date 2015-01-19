package models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MessageTest {
	private Message msg;
	private User to;
	private User from;

	@Before
	public void setUp() throws Exception {
		to = new User("VirginDestroyer69", "still@virg.in", "sadpanda", 42, 'm');
		from = new User("huehue", "ho@ho.ho", "adad", 13, 'f');
		msg = new Message(from, to, "awesome content");
	}
	
	@Test
	public void test() {
		assertEquals("awesome content", msg.msg);
		assertEquals(to, msg.to);
		assertEquals(from, msg.from);
	}

	@Test
	public void testReply() {
		String response = "I will DESTROY you";
		
		/* NOTE: further testing (the comments afterwards) requires proper user management and registration of users via the website
		 * msg.reply(response);
		 * assertTrue (from.msgBox.messages.size() == 1);
		 * assertEquals(from.msgBox.messages.get(0).msg,response);
		 * assertEquals(from.msgBox.messages.get(0).from, to);
		 * assertEquals(from.msgBox.messages.get(0).to, from);
		 */
	}

}
