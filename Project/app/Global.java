import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

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
 
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

}
