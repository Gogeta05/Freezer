import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import models.User;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import backend.Database;
import play.*;
import utils.Util;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        Logger.info("Application has started");
        
        //set the string encrypter (encryption algorithms by bouncy castle)
        String encryptionAlgorithm = "WHIRLPOOL";
        
        if (Util.encrypter == null) {
        	Util.encrypter = new ConfigurablePasswordEncryptor();
	        Util.encrypter.setProvider(new BouncyCastleProvider());
	        Util.encrypter.setAlgorithm(encryptionAlgorithm);
	        
	        Logger.info("Registered String Encrypter with Algorithm: " + encryptionAlgorithm);
			try {
				if (Cipher.getMaxAllowedKeyLength(encryptionAlgorithm) < Integer.MAX_VALUE) {
					Logger.info("couldn't find JCE unlimited policy, please install it in your JDK folder");
				}
			} catch (NoSuchAlgorithmException e) {
				Logger.info(e.toString());
			}

        }
        
        /* create a mockup database for easier testing */
        
        //load lifts
        Database.readSpreadsheet();
        
        //create some users
        Database.addUser(new User("virgin_destroyer", "still@virg.in", "sadpanda", 42, 'm'));
        Database.addUser(new User("InnocentVIII", "Cybo@pope.org", "imapope", 80, 'm'));
        Database.addUser(new User("Bathory", "elisabeth@blood.in", "foreverYoung", 20, 'f'));
        Database.addUser(new User("GrueneGurke", "gg@theGurks.com", "guenther", 42, 'm'));
        Database.addUser(new User("BananaLama", "banan@lama.in", "thebananalama", 10, 'f'));
        Database.addUser(new User("Gandalf", "gandalf@grey.at", "w1zard", 202, 'm'));
        Database.addUser(new User("XXxHotGirl742xXX", "im@girl.com", "notAsc1m", 17, 'f'));
        Database.addUser(new User("shyPeter", "peter@peters.pete", "peter!", 19, 'm'));
        Database.addUser(new User("admin", "admin@uibk.ac.at", "admin", 20, 'm'));
        
 
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

}
