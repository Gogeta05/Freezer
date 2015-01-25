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

	/*
	 * For testing purposes only - a bit of eyecandy in addition to the unit tests.
	 */
	public static Result displayLifts() {
		Database.readSpreadsheet();
		List<Lift> lifts = new Model.Finder<>(String.class, Lift.class).all();
		return ok(displayLifts.render(lifts));
	}

	public static Result filterPopup() {
		return ok(filterPopup.render());
	}

	public static Result locationTracker() {
		return ok(locationTracker.render());
	}

	public static Result privateMessage(long id) {
		Message msg = new Model.Finder<>(String.class, Message.class).where(Expr.idEq(id)).findUnique();
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
		return ok(settingsMatching.render());
	}

	public static Result settingsProfile() {
		return ok(settingsProfile.render());
	}

	public static Result userPopup(String username) {
		User usr = Database.getUser(username);
		
		if (usr == null) {
			return badRequest("nope");
		}
		return ok(userPopup.render(usr));
	}

	/**
	 * Controls the view that allows a user to arrange a meeting
	 * @param toUser	the recipient of the message
	 * @return
	 */
	public static Result contactRequest(String user) {
		Form<ContactRequest> contactRequestForm = Form.form(ContactRequest.class);
		return ok(contactRequest.render(user, contactRequestForm));
	}
	
	public static Result contactRequestSubmit(String user) {
		Form<ContactRequest> contactRequestForm = Form.form(ContactRequest.class).bindFromRequest();
		if (contactRequestForm.hasErrors()) {
			return badRequest(contactRequest.render(user, contactRequestForm));
		} else {
			User usr = Database.getUser(user);
			if (usr == null) {
				return badRequest("nope");
			}
			String msg = contactRequestForm.field("message").value();
			Util.getSessionUser().sendMsg(msg, usr);
		}
		return redirect(routes.Application.home());
	}
	
	public static Result logout() {
		session().clear();
		return redirect(routes.Application.index());
	}

	public static Result setUserLocation() {
		Form<SetUserLocation> form = Form.form(SetUserLocation.class).bindFromRequest();
		
		Util.getSessionUser().setLocation(Integer.parseInt(form.field("locationInfo").value()));
		
		String liftName = form.field("liftname").value();

		if (!liftName.equals("default")) {
			Util.getSessionUser().setLiftName(liftName);
		}
		
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
	
	public static List<Lift> getLifts(int plz) {
		return Database.getLifts(plz);
	}
}
