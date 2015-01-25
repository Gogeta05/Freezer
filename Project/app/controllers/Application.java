package controllers;

import java.util.Date;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
//import com.sun.jmx.mbeanserver.Repository.RegistrationContext;






import controllers.Application.Register;
import backend.Database;
import models.Interests;
import models.Lift;
import models.Message;
import models.User;
import play.*;
import play.api.mvc.Session;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import utils.Util;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		Form<Login> loginForm = Form.form(Login.class);
		// Query users from database
		List<User> users = new Model.Finder<String, User>(String.class, User.class).all();
		
		// Passing the list to the view
		if (! session().isEmpty()) {
			Form<SetUserLocation> locationForm = Form.form(SetUserLocation.class);
			return ok(home.render(Util.getSessionUser(), locationForm));
		}
		return ok(index.render(users, loginForm));
	}

	public static Result home() {
		Form<SetUserLocation> locationForm = Form.form(SetUserLocation.class);
		return ok(home.render(Util.getSessionUser(), locationForm));
	}

	public static Result login() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			List<User> users = new Model.Finder<String, User>(String.class, User.class).all();
			return badRequest(index.render(users, loginForm));
		} else {
			session("email", loginForm.get().email);

			return redirect(routes.Application.home());
		}
	}

	public static Result register() {
		Form<Register> registrationForm = Form.form(Register.class);
		return ok(register.render(registrationForm));
	}

	public static Result submitRegistration() {
		// Retrieve data from POST
		Form<Register> registrationForm = Form.form(Register.class).bindFromRequest();
		if (registrationForm.hasErrors()) {
			return badRequest(register.render(registrationForm));
		} else {
			User user = new User(registrationForm.field("username").value(), registrationForm.field("email").value(), registrationForm.field("password").value(), Integer.parseInt(registrationForm.field("age").value()), registrationForm.field("gender").value()
					.charAt(0));
			// Save user into database
			user.save();
		}
		return redirect(routes.Application.index());
	}

	public static Result filterPopup() {
		return ok(filterPopup.render());
	}

	public static Result privateMessage(long id) {
		Message msg = new Model.Finder<>(String.class, Message.class).where(Expr.idEq(id)).findUnique();
		msg.read = true;
		return ok(privateMessage.render(msg));
	}

	public static Result privateMessageList() {
		return ok(privateMessageList.render(Util.getSessionUser().msgBox));
	}
	
	public static Result privateMessageReply(long id) {
		Message msg = new Model.Finder<>(String.class, Message.class).where(Expr.idEq(id)).findUnique();
		Form<Reply> replyForm = Form.form(Reply.class);
		return ok(privateMessageReply.render(msg, replyForm));
	}
	
	public static Result privateMessageReplySubmit(long id) {
		Message msg = new Model.Finder<>(String.class, Message.class).where(Expr.idEq(id)).findUnique();
		Form<Reply> replyForm = Form.form(Reply.class).bindFromRequest();
		if (replyForm.hasErrors()) {
			return badRequest(privateMessageReply.render(msg, replyForm));
		} else {
			String newMsg = replyForm.field("message").value();
			msg.reply(newMsg);
		}
		return redirect(routes.Application.home());
	}

	public static Result settingsAccount() {
		return ok(settingsAccount.render());
	}

	public static Result settingsGeneral() {
		return ok(settingsGeneral.render());
	}

	public static Result settingsMatching() {
		Form<SaveMatchingSettings> form = Form.form(SaveMatchingSettings.class);
		return ok(settingsMatching.render(form));
	}

	public static Result settingsProfile() {
		Form<SaveProfileSettings> form = Form.form(SaveProfileSettings.class);
		return ok(settingsProfile.render(Util.getSessionUser(), form));
	}

	public static Result userPopup(String username) {
		User usr = Database.getUser(username);
		
		if (usr == null) {
			return badRequest("nope");
		}
		return ok(userPopup.render(usr));
	}
	
	public static Result chooseLift(String user) {
		Form<ChooseLift> chooseLiftForm = Form.form(ChooseLift.class);
		User usr = Database.getUser(user);	
		if (usr == null) {
			return badRequest("nope");
		}
		return ok(contactRequestChooseLift.render(usr, chooseLiftForm));
	}
	
	public static Result chooseLiftSubmit(String user) {
		Form<ChooseLift> chooseLiftForm = Form.form(ChooseLift.class).bindFromRequest();
		User usr = Database.getUser(user);	
		if (usr == null) {
			return badRequest("nope");
		}
		if (chooseLiftForm.hasErrors()) {
			return badRequest(contactRequestChooseLift.render(usr, chooseLiftForm));
		} else {
			String lift = chooseLiftForm.field("liftname").value();
			return redirect(routes.Application.contactRequest(user, lift));
		}
	}
	
	public static Result readMe() {
		return ok(readMe.render());
	}

	/**
	 * Controls the view that allows a user to arrange a meeting
	 * @param toUser	the recipient of the message
	 * @return
	 */
	public static Result contactRequest(String user, String liftname) {
		Form<ContactRequest> contactRequestForm = Form.form(ContactRequest.class);
		User usr = Database.getUser(user);	
		if (usr == null) {
			return badRequest("nope");
		}
		return ok(contactRequest.render(usr, liftname, contactRequestForm));
	}
	
	public static Result contactRequestSubmit(String user, String liftname) {
		Form<ContactRequest> contactRequestForm = Form.form(ContactRequest.class).bindFromRequest();
		User usr = Database.getUser(user);	
		if (usr == null) {
			return badRequest("nope");
		}
		if (contactRequestForm.hasErrors()) {
			return badRequest(contactRequest.render(usr, liftname, contactRequestForm));
		} else {
			String time = contactRequestForm.field("proposeTime").value();
			String msg = contactRequestForm.field("message").value();
			Util.getSessionUser().sendMsg(msg, usr, time, liftname);
		}
		return redirect(routes.Application.home());
	}
	
	public static Result logout() {
		session().clear();
		return redirect(routes.Application.index());
	}

	public static Result setUserLocation() {
		Form<SetUserLocation> form = Form.form(SetUserLocation.class).bindFromRequest();
		User usr = Util.getSessionUser();
		
		String location = form.field("locationInfo").value();
		
		if (!location.equals("")) {
			usr.setLocation(Integer.parseInt(location));
		}
		
		String liftName = form.field("liftname").value();

		if (!liftName.equals("default")) {
			usr.setLiftName(liftName);
		}
		
		usr.save();
		return redirect(routes.Application.home());
	}
	
	public static Result saveMatchingSettings() {
		Form<SaveMatchingSettings> form = Form.form(SaveMatchingSettings.class).bindFromRequest();
		User usr = Util.getSessionUser();
		
		String value = form.field("comparator").value();
		if (value != null) {
			usr.settings.comparator = value;
		}
		
		value = form.field("male").value();
		if (value != null) {
			usr.settings.male = true;
		}
		else {
			usr.settings.male = false;
			
		}
		value = form.field("female").value();
		if (value != null) {
			usr.settings.female = true;
		}
		else {
			usr.settings.female = false;
		}
		
		value = form.field("age").value();
		if (value != null) {
			usr.settings.age = Integer.parseInt(value);
		}
		
		value = form.field("matchAroundLift").value();
		if (value != null) {
			usr.settings.matchAroundLift = true;
		}
		else {
			usr.settings.matchAroundLift = false;
		}
		
		usr.save();
		return redirect(routes.Application.home());
	}
	public static Result saveProfileSettings() {
		Form<SaveMatchingSettings> form = Form.form(SaveMatchingSettings.class).bindFromRequest();
		User usr = Util.getSessionUser();
		
		String value = form.field("firstName").value();
		if (value != null) {
			usr.setFirstName(value);
		}
		
		value = form.field("lastName").value();
		if (value != null) {
			usr.setLastName(value);
		}
		
		value = form.field("gender").value();
		if (value != null) {
			if (value.equals("male")) {
				usr.setGender('m');
			} else {
				usr.setGender('f');
			}
			
		}
		
		value = form.field("age").value();
		if (value != null) {
			usr.setAge(Integer.parseInt(value));
		}

		usr.save();
		return redirect(routes.Application.home());
	}
	/**
	 * don't touch, automatic playframework at work
	 */
	public static class Login {
		public String email;
		public String password;

		public String validate() {
			if (User.authenticate(email, password) == null) {
				return "Invalid user or password";
			}
			return null;
		}
	}

	public static class Register {
		public String username;
		public Integer age;
		public char gender;
		public String email;
		public String emailConfirm;
		public String password;
		public String passwordConfirm;

		public String validate() {
			if(!email.equalsIgnoreCase(emailConfirm)) {
				return "email fields must match";
			}
			if(!password.equalsIgnoreCase(passwordConfirm)) {
				return "password fields must match";
			}
			return null;
		}
	}
	
	public static class ContactRequest {
		public String proposeTime;
		public String toUser;
		public String message;
	}
	
	public static class Reply {
		public String message;
	}
	
	public static class SetUserLocation {
		public String locationInfo;
		public String liftname;
	}
	

	public static class ChooseLift {
		public String liftname;
	}
	public static class SaveMatchingSettings {
		public String comparator;
		public String age;
		public String male;
		public String female;
		public String matchAroundLift;
	}
	
	public static class SaveProfileSettings {
		public String firstName;
		public String lastName;
		public String age;
		public String gender;
	}
	
	public static List<Lift> getLifts() {
		return Database.getLifts();
	}
	
	public static List<Lift> getLifts(int plz) {
		return Database.getLifts(plz);
	}
}
