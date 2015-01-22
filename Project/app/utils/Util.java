package utils;
import models.User;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public final class Util {
	/* paths */
	static public String url_LiftXls = "https://gis.tirol.gv.at/ogd/sport_freizeit/Aufstiegshilfen.xls";
	static public String file_Interests = "app/assets/Interests.xls";
	
	/* file format constants */
	static public String seperator_Interests = ",";
	
	/* encryption */
	static public StandardPBEStringEncryptor encrypter = null;
	
	/* session/database */
	static public User getSessionUser() {
		return new User("VirginDestroyer69", "still@virg.in", "sadpanda", 42, 'm');
	}
}