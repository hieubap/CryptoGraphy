package Symmetric_Cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class Based64 {
	public Based64() {
		
	}
	public void e(String input) {
		byte[] bit8 = input.getBytes();
		byte bit;
		bit = (byte)(bit8[0]>>>2);System.out.println(bit);
		bit = (byte)(((bit8[0]<<6)>>>2)|(bit8[1]>>>4));System.out.println(bit);
		bit = (byte)(((bit8[1]& 7)<<2)|(bit8[2]>>>6));System.out.println(bit);
		bit = (byte)((bit8[2]) & 63 );System.out.println(bit);
		bit = (byte)(127 << 2) ;
		System.out.println(bit);
		System.out.println(bit);
		
		
		
	}
	public static void main(String[] args) {
		try {
			File file = new File("E:\\com.ccmg.deadspreadingsurvival\\files/AchievementManager.json");
			InputStream read = new FileInputStream(file);
			byte[] buffer = read.readAllBytes();
			String s = new String(buffer);
			System.out.println(s);
			System.out.println(buffer.length);
			
			read.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public byte[] bit8to6(byte[] input,int start) {
		byte[] out = new byte[4];
		long a = 0;
		
		
		for(int i=0;i< 3;i++) {
			a |= (input[start + i]<<8*(2-i));
		}
		
		for(int i=0;i< 4 ;i++) {
			out[i]= (byte)(a>>>6*(3-i));
			System.out.print(Integer.toString(out[i]));
		}
		return out;
	}

}
