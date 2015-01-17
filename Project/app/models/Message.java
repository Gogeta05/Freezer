package models;

/**
 * A message.
 */
public class Message {

	/**
	 * The author of the message
	 */
	public User from;
	/**
	 * The recipient.
	 */
	public User to;
	/**
	 * The message that is sent.
	 */
	public String msg;
	
	protected Message(User from, User to, String msg) {
		this.from = from;
		this.to = to;
		this.msg = msg;
	}
	
	/**
	 * The reply to this message.
	 * @param msg the content of the reply
	 */
	protected void reply(String msg) {
		to.sendMsg(msg, from);
	}
	
}