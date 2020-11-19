package Signature;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
	private BigInteger N,q,p,d;
	public static BigInteger e = new BigInteger("65537",10);
	private final int bitlength = 512;
	
	public RSA() {
		q = randomPrime512(1);
		p = randomPrime512(2);
		while(p.equals(q)) {
			p = randomPrime512(2);
		}
		N = p.multiply(q);
		BigInteger n = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		d = e.modPow(new BigInteger("-1",10), n);
	}
	public BigInteger randomPrime512(int certainty) {
		String s = "1";
		
		Random random = new Random();
		for(int i=0;i< bitlength - 2; i++)
			s += random.nextInt(2);
		s += "1";
		BigInteger out = new BigInteger(s,2);
		
		while(!out.isProbablePrime(certainty)) {
			out = out.add(BigInteger.TWO);
		}
		return out;
	}
	public void renderPublic() {
		System.out.println("N: "+N.toString(16));
	}
	public String encrypt(String input,int radix,boolean signature) {
		BigInteger m = new BigInteger(input,radix);
		if(signature)
			m = m.modPow(d, N);
		else
			m = m.modPow(e, N);
		
		return full256bit(m.toString(16));
	}
	public static String decrypt(String input,String key) {
		BigInteger m = new BigInteger(input,16);
		
		m = m.modPow(e, new BigInteger(key,16));
		return m.toString(16);
	}
	public String test(String input) {
		BigInteger out = new BigInteger(input,16);
		
		out = out.modPow(e.multiply(d), N);
//		System.out.println((e.multiply(d)).mod(N));
		return out.toString(16);
	}
	public String full256bit(String s) {
		int leng = 256-s.length();
		String out ="";
		for(int i=0;i<leng;i++) {
			out = "0" + out;
		}
		out += s;
		return out;
	}
	public BigInteger getD() {
		return d;
	}
	public BigInteger getN() {
		return N;
	}
}
