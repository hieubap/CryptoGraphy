package Signature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import Test.SHA256;

public class SignatureRSA {
	private String hashcode,signature;
	private File file;
	private RSA rsa;
	
	public SignatureRSA(File file) {
		this.file = file;
		SHA256 sha = new SHA256();
		hashcode = sha.hashfile(file,false);
	}
	public void creatSignature() {
		// tạo chữ lý gán vào signature
		rsa = new RSA();
		rsa.renderPublic();
		signature = rsa.encrypt(hashcode,16,true);
		
		try {
			// lưu lại giá trị RSA vừa tạo
			File privatekey = new File("D:/signature.txt");
			FileWriter savekey = new FileWriter(privatekey);
			savekey.write("File: "+file.getPath()+'\n');
			savekey.write("SHA256 value: "+hashcode+'\n');
			savekey.write("Signature: "+signature+'\n');
			savekey.write("D: "+rsa.getD().toString(16)+'\n');
			savekey.write("N: "+rsa.getN().toString(16)+'\n');
			savekey.close();
			
			// gán kèm thông điệp signature vào file cần ký
			FileWriter write = new FileWriter(file.getAbsoluteFile(),true);
			write.write(signature);
			write.close();
			
			
			System.out.println("hascode: "+hashcode);
			System.out.println("signature: "+signature);
			System.out.println("signature length: "+signature.length());
			System.out.println("done");
			
		}
		catch(IOException e) {
			
		}
	}
	
	public static void main(String[] args) {
		SignatureRSA check = new SignatureRSA(new File("D:/fileki.txt"));
		check.creatSignature();
//		long time = System.currentTimeMillis();
//		for(long i=0;i<2000000000;i++)
//		{
//		}
//		long time2 = System.currentTimeMillis();
//		System.out.println(time2-time+" ----");
	}
	
	public static String hashvalue(File file) {
		try {
			InputStream input = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("SHA-256");// dba
			byte[] buffer = new byte[64];
			
            int nread;
            while ((nread = input.read(buffer)) != -1) {
//            	System.out.println(nread);
                md.update(buffer, 0, nread);
            }
            StringBuilder result = new StringBuilder();
            
            for (byte b : md.digest()) {
                result.append(String.format("%02x", b));
            }
            input.close();
            return result.toString();
		}
			catch(IOException ex) {
				JOptionPane.showMessageDialog(null,"không tìm thấy file");
			}
			catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "bạn chưa chọn file");
			}
			catch(NoSuchAlgorithmException ex) {
				
			}
		return null;
	}
}
