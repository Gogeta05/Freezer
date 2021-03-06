package utils;
import models.User;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Http;

import com.avaje.ebean.Expr;
import com.ning.http.client.Request;

public final class Util {
	/* paths */
	static public String url_LiftXls = "https://gis.tirol.gv.at/ogd/sport_freizeit/Aufstiegshilfen.xls";
	static public String file_Interests = "app/assets/Interests.xls";
	
	/* file format constants */
	static public String seperator_Interests = ",";
	
	/* encryption */
	static public ConfigurablePasswordEncryptor encrypter = null;
	
	/* session/database */
	static public User getSessionUser() {

		return 
				new Model.Finder<>(String.class, User.class).where(Expr.eq("email", Controller.session().get("email"))).findUnique();
		
	}
}