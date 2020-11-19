package Test;

import java.math.BigInteger;

public class SHA256error {

	private final String[] H = { "6A09E667", "BB67AE85", "3C6EF372", "A54FF53A", "510E527F", "9B05688C", "1F83D9AB",
			"5BE0CD19" };
	private BigInteger a = new BigInteger(H[0], 16);
	private BigInteger b = new BigInteger(H[1], 16);
	private BigInteger c = new BigInteger(H[2], 16);
	private BigInteger d = new BigInteger(H[3], 16);
	private BigInteger e = new BigInteger(H[4], 16);
	private BigInteger f = new BigInteger(H[5], 16);
	private BigInteger g = new BigInteger(H[6], 16);
	private BigInteger h = new BigInteger(H[7], 16);
	private BigInteger[] H_i = { a, b, c, d, e, f, g, h };
	private String[] K = { "428a2f98", "71374491", "b5c0fbcf", "e9b5dba5", "3956c25b", "59f111f1", "923f82a4",
			"ab1c5ed5", "d807aa98", "12835b01", "243185be", "550c7dc3", "72be5d74", "80deb1fe", "9bdc06a7", "c19bf174",
			"e49b69c1", "efbe4786", "0fc19dc6", "240ca1cc", "2de92c6f", "4a7484aa", "5cb0a9dc", "76f988da", "983e5152",
			"a831c66d", "b00327c8", "bf597fc7", "c6e00bf3", "d5a79147", "06ca6351", "14292967", "27b70a85", "2e1b2138",
			"4d2c6dfc", "53380d13", "650a7354", "766a0abb", "81c2c92e", "92722c85", "a2bfe8a1", "a81a664b", "c24b8b70",
			"c76c51a3", "d192e819", "d6990624", "f40e3585", "106aa070", "19a4c116", "1e376c08", "2748774c", "34b0bcb5",
			"391c0cb3", "4ed8aa4a", "5b9cca4f", "682e6ff3", "748f82ee", "78a5636f", "84c87814", "8cc70208", "90befffa",
			"a4506ceb", "bef9a3f7", "c67178f2" };

	public String[] paddingBit(String mes) { // mes la chuoi acsii
		String plai = "";
		for (int i = 0; i < mes.length(); i++) {
			String s = Integer.toBinaryString(mes.charAt(i));
			while (s.length() < 8) {
				s = '0' + s;
			}
			plai += s;
		}
		int chieuDaiBanTin = plai.length();
		plai = plai + '1';
		while ((plai.length() + 64) % 512 != 0) {
			plai += '0';
		}
		String demChieuDai = Integer.toBinaryString(chieuDaiBanTin);
		while (demChieuDai.length() < 64) {
			demChieuDai = '0' + demChieuDai;
		}
		plai += demChieuDai;
		String[] result = new String[plai.length() / 512];
		for (int i = 0; i < plai.length() / 512; i++) {
			result[i] = plai.substring(i * 512, i * 512 + 512);
		}
		return result;
	}

	public String hash(String plai) {
		String[] p = paddingBit(plai);
		for (int sk = 0; sk < p.length; sk++) {
			BigInteger[] w = tinhW(p[sk]);
			BigInteger a = H_i[0];
			BigInteger b = H_i[1];
			BigInteger c = H_i[2];
			BigInteger d = H_i[3];
			BigInteger e = H_i[4];
			BigInteger f = H_i[5];
			BigInteger g = H_i[6];
			BigInteger h = H_i[7];
			for (int i = 0; i < 64; i++) {
//				System.out.println("Vong thu " + i);
				String k = K[i];
				BigInteger k_t = new BigInteger(k, 16);
//				System.out.println("k_t: " + k_t.toString(16));
				BigInteger ch_efg = (e.and(f)).xor(e.not().and(g));
//				System.out.println("ch_egh: " + ch_efg.toString(16));
				// System.out.println("k_t: "+k_t);
				BigInteger xich_e = ROTR(e, 6).xor(ROTR(e, 11)).xor(ROTR(e, 25));
				BigInteger xich_a = ROTR(a, 2).xor(ROTR(a, 13)).xor(ROTR(a, 22));
//				System.out.println("xicha: " + xich_a.toString(16));
				BigInteger maj_abc = (a.and(b)).xor(a.and(c)).xor(b.and(c));
				// BigInteger t1 = SUM32(SUM32(h, ch_efg), SUM32(SUM32(xich_e, w[i]), k_t));
				BigInteger t1 = SUM32(SUM32(SUM32(SUM32(h, xich_e), ch_efg), k_t), w[i]);
//				System.out.println("SUM32: " + SUM32(h, ch_efg).toString(16));
//				System.out.println("t1: " + t1.toString(16));
				BigInteger t2 = SUM32(xich_a, maj_abc);
				h = g;
				g = f;
				f = e;
				e = SUM32(d, t1);
//				System.out.println("e: " + e.toString(16));
				d = c;
				c = b;
				b = a;
				a = SUM32(t1, t2);
//				System.out.println("a: " + a.toString(16));
			}
			H_i[0] = SUM32(H_i[0], a);
			H_i[1] = SUM32(H_i[1], b);
			H_i[2] = SUM32(H_i[2], c);
			H_i[3] = SUM32(H_i[3], d);
			H_i[4] = SUM32(H_i[4], e);
			H_i[5] = SUM32(H_i[5], f);
			H_i[6] = SUM32(H_i[6], g);
			H_i[7] = SUM32(H_i[7], h);
		}
		String ketQua = "";
		for (int i = 0; i < 8; i++) {
			String s = H_i[i].toString(16);
			while (s.length() < 8) {
				s = '0' + s;
			}
			ketQua += s;
		}
		return ketQua;
	}

	public BigInteger[] tinhW(String msg) {
		BigInteger[] w = new BigInteger[64];
		for (int i = 0; i < 16; i++) {
			w[i] = new BigInteger(msg.substring(32 * i, 32 * i + 32), 2);
		}
		for (int i = 16; i < 64; i++) {
			w[i] = SUM32(SUM32(o_1(w[i - 2]), w[i - 7]), SUM32(o_0(w[i - 15]), w[i - 16]));
		}
		return w;
	}

	private BigInteger SHR(BigInteger x, int n) {
		return x.shiftLeft(n);
	}

	private BigInteger o_0(BigInteger x) {
		return ROTR(x, 7).xor(ROTR(x, 18)).xor(SHR(x, 3));
	}

	private BigInteger o_1(BigInteger x) {
		return ROTR(x, 17).xor(ROTR(x, 19)).xor(SHR(x, 10));
	}

	private BigInteger ROTR(BigInteger x, int n) {
		BigInteger result = new BigInteger("1", 10);

		result = (x.shiftRight(n)).or(x.shiftLeft(32 - n));
		String s = result.toString(2);
		if (s.length() > 32) {
			s = s.substring(s.length() - 32);
		}

		result = new BigInteger(s, 2);
		return result;
	}

	private BigInteger SUM32(BigInteger a, BigInteger b) {
		BigInteger ss = new BigInteger("4294967296", 10);
		BigInteger result = (a.add(b)).mod(ss);
		return result;
	}

	public static void main(String[] args) {
		SHA256error sha = new SHA256error();
		System.out.println(sha.hash(""));
//		sha = new SHA256();
//		System.out.println(sha.hash(""));
//		BigInteger a = new BigInteger("60",10);
//		System.out.println(sha.ROTR(a, 3).toString(2));
//		BigInteger xich_a = sha.ROTR(a, 28).xor(sha.ROTR(a, 34)).xor(sha.ROTR(a, 39));
//		System.out.println("xich_a: "+xich_a.toString(16));
	}
}
