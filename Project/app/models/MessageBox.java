package models;

import java.util.ArrayList;

/**
 * The inbox of a User.
 */
public class MessageBox {
	
	/**
	 * The messages currently in the inbox
	 */
	public ArrayList<Message> messages;
	
	/**
	 * Deletes a message
	 * @param msg the message to delete
	 */
	public void deleteMsg(Message msg) {
		messages.remove(msg);
	}
	/**
	 * Deletes all messages in the inbox.
	 */
	public void deleteAll() {
		messages.clear();
	}
	/**
	 * Sends a reply to a message.
	 * @param newMsg	the reply
	 * @param msg		the message to reply to
	 */
	public void reply(String newMsg, Message msg) {
		msg.reply(newMsg);
	}
	
}