package models;

public class Message {

	public User from;
	public User to;
	public String msg;
	
	protected Message(User from, User to, String msg) {
		this.from = from;
		this.to = to;
		this.msg = msg;
	}
	
	protected void reply(String msg) {
		to.sendMsg(msg, from);
	}
	
}