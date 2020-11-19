package Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class Test {
	public static void main(String[] args) {
		try {
			SecureRandom sr = new SecureRandom();
			KeyPairGenerator keypair = KeyPairGenerator.getInstance("RSA");
			keypair.initialize(512);
			KeyPair k = keypair.generateKeyPair();
			
			PrivateKey privatekey = k.getPrivate();
			PublicKey publickey = k.getPublic();
			System.out.println(privatekey.toString());
			System.out.println(publickey.toString());
			
			
			
		}
		catch(Exception e) {
			
		}
	}

}
