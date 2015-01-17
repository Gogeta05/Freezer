package models;

import java.util.ArrayList;

import play.db.ebean.Model;

public class MessageBox extends Model {
	public ArrayList<Message> messages;
	
}