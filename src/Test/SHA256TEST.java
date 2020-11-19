package Test;

import java.io.File;
import java.math.BigInteger;

public class SHA256TEST {
	String[] H = {
			"6A09E667", "BB67AE85", "3C6EF372", "A54FF53A", 
			"510E527F", "9B05688C", "1F83D9AB","5BE0CD19" 
	},
			k = {
				"428a2f98", "71374491", "b5c0fbcf", "e9b5dba5", "3956c25b", "59f111f1", "923f82a4","ab1c5ed5",
				"d807aa98", "12835b01", "243185be", "550c7dc3", "72be5d74", "80deb1fe", "9bdc06a7", "c19bf174",
				"e49b69c1", "efbe4786", "0fc19dc6", "240ca1cc", "2de92c6f", "4a7484aa", "5cb0a9dc", "76f988da",
				"983e5152","a831c66d", "b00327c8", "bf597fc7", "c6e00bf3", "d5a79147", "06ca6351", "14292967",
				"27b70a85", "2e1b2138","4d2c6dfc", "53380d13", "650a7354", "766a0abb", "81c2c92e", "92722c85", 
				"a2bfe8a1", "a81a664b", "c24b8b70","c76c51a3", "d192e819", "d6990624", "f40e3585", "106aa070", 
				"19a4c116", "1e376c08", "2748774c", "34b0bcb5","391c0cb3", "4ed8aa4a", "5b9cca4f", "682e6ff3", 
				"748f82ee", "78a5636f", "84c87814", "8cc70208", "90befffa","a4506ceb", "bef9a3f7", "c67178f2"
			};
	public String hash(String input) {
		// khÆ¡Ì‰i taÌ£o biÃªÌ�n
		BigInteger[] hash = new BigInteger[8];
		for(int i=0;i<8;i++) {
			hash[i] = new BigInteger(H[i],16);
		}
		
		String output = "";
		for(int i=0;i< input.length();i++) {
			int lengthbin = Integer.toBinaryString(input.charAt(i)).length();
			
			for(int k=0;k< 8 - lengthbin;k++)
				output += "0";
			
			output += Integer.toBinaryString(input.charAt(i));
		}
		long size = output.length();
		long num0add = 448 - size%512 + 64 - Long.toBinaryString(size).length();

		output += "1";
		for(int i=0;i< num0add - 1 ; i++)
			output += "0";
		
		output += Long.toBinaryString(size);
//		System.out.println(output);
//		System.out.println(output);
		int count = output.length()/512;
		String[] group = new String[count];
		for(int i=0;i < count ;i++) {
			group[i] = output.substring(i*512, 512*(i+1));
			
			String[] word = new String[16];
			// chia thaÌ€nh 16 nhoÌ�m
			for(int j=0;j< 16;j++) {
				word[j] = group[i].substring(j*32,(j+1)*32);
			}
			// mÆ¡Ì‰ rÃ´Ì£ng 16 nhoÌ�m thaÌ€nh 64 nhoÌ�m
			BigInteger[] w = new BigInteger[64];
			for(int j = 0;j< 16 ; j++)
				{
				w[j] = new BigInteger(word[j],2);
				renderfullbit(w[j]);
				}
			System.out.println();
			for(int j = 16;j< 64 ; j++)
				{
				BigInteger s0 = rotRight(w[j-15],7).xor(rotRight(w[j-15],18)).xor(w[j-15].shiftRight(3));
				BigInteger s1 = rotRight(w[j-2],17).xor(rotRight(w[j-2],19)).xor(w[j-2].shiftRight(10));

				w[j] = sum32(sum32(sum32(w[j - 16],s0),w[j-7]),s1);
				renderfullbit(w[j]);
				}
			// khÆ¡Ì‰i taÌ£o giaÌ� triÌ£ bÄƒm cho nhoÌ�m
			BigInteger a = hash[0];
			BigInteger b = hash[1];
			BigInteger c = hash[2];
			BigInteger d = hash[3];
			BigInteger e = hash[4];
			BigInteger f = hash[5];
			BigInteger g = hash[6];
			BigInteger h = hash[7];
			// voÌ€ng lÄƒÌ£p chiÌ�nh
			for(int j=0;j< 64; j++) {
				BigInteger s0 = rotRight(a, 2).xor(rotRight(a, 13)).xor(rotRight(a, 22));
//				if(j==test)
//					System.out.println(s0.toString(16)+"      ,");
				BigInteger maj = (a.and(b)).xor(a.and(c)).xor(b.and(c));
				BigInteger t2 = sum32(s0,maj);
				
				BigInteger s1 = rotRight(e,6).xor(rotRight(e,11)).xor(rotRight(e, 25));
				BigInteger ch = (e.and(f)).xor((e.not()).and(g));
				
				BigInteger p = new BigInteger(k[j],16);
				BigInteger t1 = sum32(sum32(sum32(sum32(h,s1),ch),p),w[j]);
				
				h = g;
				g = f;
				f = e;
				e = sum32(d,t1);
				d = c;
				c = b;
				b = a;
				a = sum32(t1,t2);
				
			}
//			System.out.println(h.toString(16)+"..");
			// cÃ´Ì£ng giaÌ� triÌ£ bÄƒm
			hash[0] = sum32(hash[0],a);
			hash[1] = sum32(hash[1],b);
			hash[2] = sum32(hash[2],c);
			hash[3] = sum32(hash[3],d);
			hash[4] = sum32(hash[4],e);
			hash[5] = sum32(hash[5],f);
			hash[6] = sum32(hash[6],g);
			hash[7] = sum32(hash[7],h);
//			System.out.println(hash[0].toString(16));
			
		}
		System.out.println();
		System.out.println(hash[0].toString(16) + hash[1].toString(16)
				+ hash[2].toString(16) + hash[3].toString(16)
				+ hash[4].toString(16) + hash[5].toString(16)
				+ hash[6].toString(16) + hash[7].toString(16)
				);
		return hash[0].toString(16) + hash[1].toString(16)
				+ hash[2].toString(16) + hash[3].toString(16)
				+ hash[4].toString(16) + hash[5].toString(16)
				+ hash[6].toString(16) + hash[7].toString(16)
				;
	}
	private BigInteger sum32(BigInteger a, BigInteger b) {
		BigInteger max = new BigInteger("4294967296", 10);
		return (a.add(b)).mod(max);
	}
	public static BigInteger rotRight(BigInteger a, int b) {
		String s = "1";
		for(int i=0;i<b;i++)
			s += "0";
		return a.shiftRight(b).or(a.mod(new BigInteger(s,2)).shiftLeft(32-b));
	}
	public void renderfullbit(BigInteger i) {
		int j = 32 - i.toString(2).length();
		String out = "";
		for(int k=0;k<j;k++)
			out = "0" + out;
		out += i.toString(2);
		
		System.out.print(out);
	}
	public static void main(String[] args) {
		 SHA256TEST has = new SHA256TEST();
		 has.hash("5");
		 SHA256 sha = new SHA256();
		 sha.hashString("5");
	}

}