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
	 * The time of the meeting
	 */
	public String time;
	/**
	 * The lift of the meeting
	 */
	public String lift;
	/**
	 * The message that is sent.
	 */
	public String msg;
	
	public Boolean read;
	
	protected Message(User from, User to, String time, String lift, String msg) {
		this.from = from;
		this.to = to;
		this.time = time;
		this.lift = lift;
		this.msg = msg;
		read = false;
		box = to.msgBox;
	}
	
	/**
	 * The reply to this message.
	 * @param msg the content of the reply
	 */
	public void reply(String msg) {
		to.sendMsg(msg, from, time, lift);
	}
	
}