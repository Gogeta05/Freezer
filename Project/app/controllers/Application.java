package controllers;

import java.util.List;

import backend.Database;
import models.Lift;
import models.User;
import play.*;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	//Query users from database
    	List<User> users = new Model.Finder<String, User>(String.class, User.class).all();
    	//Passing the list to the view
        return ok(index.render(users));
    }
    
    public static Result register() {
    	//Retrieve data from POST
    	User user = Form.form(User.class).bindFromRequest().get();
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
    	return ok(userPopup.render());
    }

}
