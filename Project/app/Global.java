import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Cipher;

import models.Interests;
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
        
        if (Database.getUser("admin") == null) {
	        //load lifts
	        Database.readSpreadsheet();
	        
	        //create some users
	        Database.addUser(new User("virgin_destroyer", "still@virg.in", "sadpanda", 42, 'm'));
	        Database.addUser(new User("InnocentVIII", "Cybo@pope.org", "imapope", 80, 'm'));
	        Database.addUser(new User("Bathory", "elisabeth@blood.in", "foreverYoung", 20, 'f'));
	        Database.addUser(new User("GrueneGurke", "gg@theGurks.com", "guenther", 42, 'm'));
	        Database.addUser(new User("BananaLama", "banan@lama.in", "thebananalama", 10, 'f'));
	        Database.addUser(new User("Gandalf", "gandalf@grey.at", "w1zard", 202, 'm'));
	        Database.addUser(new User("XXxHotGirl742xXX", "im@girl.com", "not4scam", 17, 'f'));
	        Database.addUser(new User("shyPeter", "peter@peters.pete", "peter!", 19, 'm'));
	        Database.addUser(new User("admin", "admin@uibk.ac.at", "admin", 20, 'm'));
	        
	        //set some interests for the users
	        List<User> users = Database.getUsers();
	        Interests interest;
	        
	        //user 0
	        interest = users.get(0).findInterests("pcgames");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(0).findInterests("rpg");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(0).findInterests("hack'n'slash");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(0).findInterests("gaming");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(0).findInterests("boardgames");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        
	        //user 1
	        interest = users.get(1).findInterests("theology");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        
	        //user 2
	        interest = users.get(2).findInterests("movies");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(2).findInterests("horror");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(2).findInterests("medieval");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        
	        //user 3
	        interest = users.get(3).findInterests("pcgames");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(3).findInterests("rpg");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(3).findInterests("boardgames");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        
	        //user 4
	        interest = users.get(4).findInterests("theology");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        
	        //user 5
	        interest = users.get(5).findInterests("movies");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(5).findInterests("fantasy");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        interest = users.get(5).findInterests("theology");
	        if (interest != null) {
	        	interest.turnOn();
	        }
	        
	        //user 6
	        users.get(6).allInterests(true);
	        
	        //user 7
	        //shyPeter is not interested in anything
	        users.get(7).allInterests(false);
	        
	        //user 8
	        users.get(8).allInterests(true);
	        
	        //send some messages
	        users.get(0).sendMsg("How ya doin", users.get(1), "00:00", "Brandtallift");
	        users.get(1).sendMsg("How ya doin", users.get(0), "00:00", "Brandtallift");
	        users.get(2).sendMsg("How ya doin", users.get(2), "00:00", "Brandtallift");
	        users.get(3).sendMsg("How ya doin", users.get(3), "00:00", "Brandtallift");
	        users.get(4).sendMsg("How ya doin", users.get(0), "00:00", "Brandtallift");
	        users.get(5).sendMsg("How ya doin", users.get(5), "00:00", "Brandtallift");
	        users.get(6).sendMsg("How ya doin", users.get(7), "00:00", "Brandtallift");
	        users.get(7).sendMsg("How ya doin", users.get(6), "00:00", "Brandtallift");
	        users.get(8).sendMsg("How ya doin", users.get(4), "00:00", "Brandtallift");
	        users.get(5).sendMsg("How ya doin", users.get(8), "00:00", "Brandtallift");
	        users.get(2).sendMsg("How ya doin", users.get(2), "00:00", "Brandtallift");
	        
	        //set locations
	        //shyPeter is all alone
	        users.get(0).setLocation(6410);
	        users.get(1).setLocation(6410);
	        users.get(2).setLocation(6020);
	       	users.get(3).setLocation(6020);
	      	users.get(4).setLocation(6060);
	        users.get(5).setLocation(6060);
	        users.get(6).setLocation(6215);
	        users.get(7).setLocation(6236);
	        users.get(8).setLocation(6215);
	        														
        }
        
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

}
