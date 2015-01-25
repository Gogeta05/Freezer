package models;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

/**
 * The inbox of a User.
 */

@Entity
public class MessageBox extends Model {
	
	@Id
	@GeneratedValue
	public long id;
	
	@OneToOne
	@JoinColumn(name = "FK_OWNER")
	User owner;
	
	/**
	 * The messages currently in the inbox
	 */
	@OneToMany(mappedBy="box", cascade=CascadeType.ALL)
	public List<Message> messages;
	
	
	public MessageBox(User owner) {
		messages = new ArrayList<Message>();
		this.owner = owner;
	}
	
	/**
	 * Deletes a message
	 * @param msg the message to delete
	 */
	public void deleteMsg(Message msg) {
		messages.remove(msg);
		this.save();
	}
	/**
	 * adds a message
	 */
	public void addMsg(Message msg) {
		messages.add(msg);
		this.save();
	}
	/**
	 * Deletes all messages in the inbox.
	 */
	public void deleteAll() {
		messages.clear();
		this.save();
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