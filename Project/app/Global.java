import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import play.*;
import utils.Util;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        Logger.info("Application has started");
        
        //set the string encrypter (encryption algorithms by bouncy castle)
        String encryptionAlgorithm = "PBEWITHSHA256AND128BITAES-CBC-BC";
        
        if (Util.encrypter == null) {
        	Util.encrypter = new StandardPBEStringEncryptor();
	        Util.encrypter.setProvider(new BouncyCastleProvider());
	        Util.encrypter.setAlgorithm(encryptionAlgorithm);
	        Util.encrypter.setPassword("WhatIsThisFor? I don't even!");
	        
	        Logger.info("Registered String Encrypter with Algorithm: " + encryptionAlgorithm);
			try {
				if (Cipher.getMaxAllowedKeyLength("PBEWITHSHA256AND128BITAES-CBC-BC") < Integer.MAX_VALUE) {
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
