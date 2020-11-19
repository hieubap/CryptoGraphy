package Test;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger e;
	private BigInteger d;
	private BigInteger c;
	// private BigInteger plai;
	private static final int LENGTH_N = 3072;

	public RSA() {
		this.e = new BigInteger("65537", 10);
		this.initPQ();
//		this.n=p.multiply(q);
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getE() {
		return e;
	}

	// tim
	// chuyen plain sang bigInteger
	// tim p, q voi n co do dai la 1024
	private void initPQ() {
		String sp = ""; // s="11....1" length s>=512
		Random rdp = new Random();
		Random rdq = new Random();
		for (int i = 0; i < LENGTH_N / 2 - 3; i++) {
			sp += rdp.nextInt(2);
		}
		sp = "11" + sp + "1";
		p = new BigInteger(sp, 2);
//		 System.out.println(sp.length());
		BigInteger two = new BigInteger("2", 10);
		while (!p.isProbablePrime(1)) {
			p = p.add(two);
		}
		// System.out.println("p = " + p.toString(16));
		String sq = "";
		for (int i = 0; i < LENGTH_N - LENGTH_N / 2 - 3; i++) {
			sq += rdq.nextInt(2);
		}
		// sq = "1" + sq + "1";
		q = new BigInteger("11" + sq + "1", 2);
		while (!q.isProbablePrime(2)) {
			q = q.add(two);
		}
		if (p.equals(q)) {
			while (!p.isProbablePrime(1)) {
				p = p.add(two);
			}
		}
//		System.out.println();
		if (p.compareTo(q) < 0) {
			two = p;
			p = q;
			q = two;
		}
//		 System.out.println("p = " + p.toString(16).length());
//		 System.out.println("q = " + q.toString(16).length());
//		 System.out.println(p.multiply(q).bitLength());
	}

	// ma hoa
	public String encryp(String plai) {
		// tim p,q
		//initPQ();
		// tim e
		// tach plain
		BigInteger pl = new BigInteger(plai, 16);
		
		n = p.multiply(q);
		BigInteger a = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		d = e.modPow(new BigInteger("-1", 10), a);
		c = pl.modPow(d, n);
//		System.out.println(n.bitLength());
		return pl.modPow(d, n).toString(16);
	}

	public String dedecryp(BigInteger cir, BigInteger n, BigInteger e) { // tham so truyen vao la c, d, n, e
		BigInteger kq = new BigInteger("1", 10);
		// BigInteger a =
		// (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		// d = e.modPow(new BigInteger("-1", 10), a);
		kq = cir.modPow(e, n);
		return cir.modPow(e, n).toString(16);
	}
	public String test(String input) {
		BigInteger out = new BigInteger(input,16);
		
		out = out.modPow(e.multiply(d), n);
//		System.out.println((e.multiply(d)).mod(N));
		return out.toString(16);
	}
	// gh so bigInteger khi thuc hien phep nhan cÃ³ chiá»�u dÃ i <= 9X10000
	public static void main(String[] args) {
//		BigInteger p = new BigInteger("74", 10);
//		BigInteger q = new BigInteger("151", 10);
//		BigInteger e = new BigInteger("11", 10);
//		BigInteger plai=new BigInteger("2414",10);
//		rsa.encryp(plai);
//		System.out.println("c: "+rsa.c);
//		rsa.dedecryp(rsa.c);
//		System.out.println("plain: "+rsa.plai);

		RSA rsa = new RSA();
//		System.out.println(rsa.getN());
//		Random rd=new Random();
//		for(int i=0;i<5;i++) {
//			System.out.println(rd.nextInt(2));
//		}
		// System.out.println();
		// rsa.initPQ();
		// System.out.println(rsa.p.multiply(rsa.q).toString(2).length());
		// System.out.println("P: "+rsa.p+"\nQ: "+rsa.q);
		// System.out.println(p.isProbablePrime(1));
		// rsa.encryp("123456");
//		System.out.println(rsa.encryp("aa6a1756a918746df4bcda890d0eaa4dc08c8a60718e4f5c6a87b497ce721aab"));
		rsa.encryp("a");
		System.out.println(rsa.test("a"));
//		System.out.println(rsa.dedecryp("aa6a1756a918746df4bcda890d0eaa4dc08c8a60718e4f5c6a87b497ce721aab"));
		
//		rsa.dedecryp(rsa.c, rsa.n, rsa.d);
//		System.out.println(rsa.dedecryp(rsa.c, rsa.n, rsa.e));
	}

}