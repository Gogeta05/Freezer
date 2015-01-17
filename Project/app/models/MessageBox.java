package models;

import java.util.ArrayList;

public class MessageBox {
	
	public ArrayList<Message> messages;
	
	public void deleteMsg(Message msg) {
		messages.remove(msg);
	}
	public void deleteAll() {
		messages.clear();
	}
	public void reply(String newMsg, Message msg) {
		msg.reply(newMsg);
	}
	
}