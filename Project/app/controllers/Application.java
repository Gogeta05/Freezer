package controllers;

import java.util.List;

import backend.Database;
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
    	//Query users from database
    	List<User> users = new Model.Finder<String, User>(String.class, User.class).all();
    	//Passing the list to the view
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
            
            return redirect(
                    routes.Application.home()
            );
       }
    }
    
    public static Result register() {
    	return ok(register.render());
    }
    
    public static Result submitRegistration() {
    	//Retrieve data from POST
    	Form<User> userForm = Form.form(User.class).bindFromRequest();
   
    	User user = new User(userForm.field("username").value(),userForm.field("email").value(),userForm.field("password").value(),
    				Integer.parseInt(userForm.field("age").value()),userForm.field("gender").value().charAt(0));
    	
    	//Save user into database
    	user.save();
    	
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
}
