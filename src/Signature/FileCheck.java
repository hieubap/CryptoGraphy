package Signature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import Test.SHA256;

public class FileCheck {
	private String hashvalue,signature;
	private File file;
	private String publickey;
	
	public FileCheck(File file,String publickey) {
		this.file= file;
		this.publickey = publickey;
		}
	public void check() {
		try {
			InputStream input = new FileInputStream(file);
			byte[] read = new byte[64];// đọc toàn bộ byte trong file
			int size = input.available()/64 - 4;
			
			// băm file không tính 256 ký tự gắn cuối file
			SHA256 sha = new SHA256();
			for(int i=0;i< size  ; i++) {
				input.read(read);
				sha.update(read);
			}
			int off  = input.read(read, 0, input.available()-256);
			hashvalue = sha.hash(read, size*512 ,off );
			
			// dùng rsa giải mã chữ ký
			String s = "";
			read = input.readAllBytes();
			for(byte b : read) {
				s += (char)b;
			}
			signature = RSA.decrypt(s, publickey);
			
			System.out.println("hashcode  : "+hashvalue);
			System.out.println("signature : "+signature);
			 			 
			input.close();
		}
		catch(IOException e) {
			
		}
		if(signature.equals(hashvalue)) {
			System.out.println("Message is verified");
		}
		else {
			System.out.println("Message isn't verified");
		}
	}
	public static void main(String[] args) {
		String publickey = "731f251dd44ed3a4ddc1e04e341b0ef67d9914907d5b5f7bcf7cd72e23551b185189cbdc0bba42376736592e1097c3bf8dc01ab9d480983d2d9b484ab26d03f06341e694e9e57b8b370968b5578da5eb01bbd57c0f404b319ad4842f9827046cd6bcd42e81d7bf95f23e32ab0451916c538f36838184750d495d9215f8fcba85";
		File file = new File("D:/fileki.txt");
		FileCheck check = new FileCheck(file,publickey);
		check.check();
	}
	
	public String hashvalue(File file) {
		try {
			InputStream input = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
            int nread;
            while ((nread = input.read(buffer)) != -1) {
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
