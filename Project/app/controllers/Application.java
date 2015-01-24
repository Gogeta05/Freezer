package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;

import controllers.Application.Register;
import backend.Database;
import models.Interests;
import models.Lift;
import models.User;
import play.*;
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
		return ok(index.render(users, loginForm));
	}

	public static Result home() {
		return ok(home.render());
	}

	public static Result login() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			List<User> users = new Model.Finder<String, User>(String.class, User.class).all();
			return badRequest(index.render(users, loginForm));
		} else {
			session().clear();
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

	public static Result privateMessage() {
		return ok(privateMessage.render());
	}

	public static Result privateMessageList() {
		return ok(privateMessageList.render());
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

	public static Result userPopup() {
		return ok(userPopup.render(Util.getSessionUser()));
	}

	public static Result contactRequest() {
		return ok(contactRequest.render());
	}
	
	public static Result logout() {
		session().clear();
		return redirect(routes.Application.index());
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
}
