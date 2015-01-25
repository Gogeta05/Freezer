package models;
import javax.persistence.*;

import backend.Database;

/**
 * A message.
 */
@Entity
public class Message {

	@Id
	@GeneratedValue
	public long id;
	
	@ManyToOne
	@JoinColumn(name = "FK_BOX")
	MessageBox box;
	
	/**
	 * The author of the message
	 */
	@ManyToOne
	@JoinColumn(name = "FK_FROM")
	public User from;
	/**
	 * The recipient.
	 */
	@ManyToOne
	@JoinColumn(name = "FK_TO")
	public User to;
	/**
	 * The message that is sent.
	 */
	public String msg;
	
	protected Message(User from, User to, String msg) {
		this.from = from;
		this.to = to;
		this.msg = msg;
		box = to.msgBox;
	}
	
	/**
	 * The reply to this message.
	 * @param msg the content of the reply
	 */
	protected void reply(String msg) {
		to.sendMsg(msg, from);
	}
	
}