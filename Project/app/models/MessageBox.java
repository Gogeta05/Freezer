package models;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

/**
 * The inbox of a User.
 */

@Entity
public class MessageBox extends Model {
	
	@Id
	@GeneratedValue
	public long id;
	
	/**
	 * The messages currently in the inbox
	 */
	@OneToMany(cascade=CascadeType.ALL)
	public ArrayList<Message> messages;
	
	
	public MessageBox() {
		messages = new ArrayList<Message>();
	}
	
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