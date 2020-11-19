package Symmetric_Cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


public class SecurityKeyPairGenerator {

	public static void main(String[] args) {
		try {
			SecureRandom sr = new SecureRandom();
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024, sr);

			KeyPair kp = kpg.genKeyPair();
			// PublicKey
			PublicKey publicKey = kp.getPublic();
			// PrivateKey
			PrivateKey privateKey = kp.getPrivate();

			File publicKeyFile = createKeyFile(new File("C:/publicKey.rsa"));
			File privateKeyFile = createKeyFile(new File("C:/privateKey.rsa"));

			FileOutputStream fos = new FileOutputStream(publicKeyFile);
			fos.write(publicKey.getEncoded());
			fos.close();

			fos = new FileOutputStream(privateKeyFile);
			fos.write(privateKey.getEncoded());
			fos.close();

			System.out.println("Generate key successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static File createKeyFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		return file;
	}
}