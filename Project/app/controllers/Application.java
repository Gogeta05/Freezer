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
            
            
            
/*****************************************************************************************************************
 * TODO: email and username are hardcoded because the corresponding fields in this class are still null after filling out the form -> we need to fix this ASAP.
 * Automatic form generation is needed for the validate method. But the controller login-action and the validate-action do get NULL as parameter, although I did everything the same as in the example files of the VO!
 */
            session("email", /*loginForm.get().email*/"nice@am.com");
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
    
    /**
     * Play generates forms automagically
     * HTML Form hier als Klasse definiert, wird im View automatisch gerendert
     * *
     */
    public static class Login {
        public String email;
        public String password;

        public String validate() {
            // Der passende Validator für die Form
        	
        	
        	
        	
/*****************************************************************************************************************
 * TODO: email and username are hardcoded because the corresponding fields in this class are still null after filling out the form -> we need to fix this ASAP.
 * Automatic form generation is needed for the validate method. But the controller login-action and the validate-action do get NULL as parameter, although I did everything the same as in the example files of the VO!
 */
            if (User.authenticate("nice@am.com", "nice") == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }
}
