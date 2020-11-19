package Test;

import java.math.BigInteger;

public class RSASign {
	
	private SHA256error sha;
	private RSA rsa;
	public RSASign() {
		sha=new SHA256error();
		rsa=new RSA();
	}
	public String send(String mes) {
		String plai=mes;
		plai=sha.hash(plai);
		plai=mes+rsa.encryp(plai);
		return plai;
	}
	
	public void receive(String mesSign) {
		String plaiSign=mesSign.substring(mesSign.length()-768, mesSign.length());
		String mes=mesSign.substring(0, mesSign.length()-768);
		sha=new SHA256error();
		String sign=sha.hash(mes);
		String sign_2=rsa.dedecryp(new BigInteger(plaiSign,16), rsa.getN(), rsa.getE());
		if(sign.equals(sign_2)) {
			System.out.println("Xac thuc thanh cong!\nBan tin: "+mes);
		}else {
			System.out.println("Xac thuc khong thanh cong!");
		}
	}
	public static void main(String[] args) {
		RSASign rs=new RSASign();
		String mesSend=rs.send("123");
		System.out.println("ban tin gui: "+mesSend);
		rs.receive(mesSend);
	}
}