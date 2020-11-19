package Symmetric_Cipher;

public class S_Box {

	public static void main(String[] args) {
		long start = System.nanoTime();
		System.out.println(S_Box.multi(64, 8));
//		S_Box.mod(123456789, 123);
		long end = System.nanoTime();
		
		System.out.println(end - start);
	}
	// hàm đối chiếu s_box
	public static int s_box(int input) {
		if(input == 0) return changeByte(0);
		
		return changeByte(euclidbit(283, input));
	}
	public static int inverse_s_box(int input) {
		if(input == 99) return inverchangeByte(99);
		
		return euclidbit(283, inverchangeByte(input));
		
	}
	
	
	public static int multi(int a,int b) {
		
		int s = Integer.parseInt(Integer.toBinaryString(a)),result = 0;
		for(int i=0;i<8;i++) {
			if(s%10==1) result ^=(b<<i);
			s/=10;
		}
		return result;
	}
	public static int mod(int a,int b) {
		int s = a,result = 0,
				lb = Integer.toBinaryString(b).length(),
				la = Integer.toBinaryString(a).length();
		String str = Integer.toBinaryString(a);
		int turn = (str.length()-lb+1);
		
		for(int i=0;i<turn;i++) {
			if(str.length() == la) {
				result =(result<<1)^1;
				s^=(b<<turn-i-1);
				str = Integer.toBinaryString(s);
			}
			else {
				result <<= 1;
			}
			la--;
		}
		return s;
	}
	public static int Mod(int a,int b) {
		int div, mod = a;
		byte i = 1,j;
		for(i = 1;i<32;i++) {
			div = b >> i;
			if(div == 1 ) break;
		}
		j = i;
		for(;i<32;i++) {
			div = a >> i;
			if(div == 1) break;
		}
		i = (byte)(i - j);
		div = a;
		System.out.println(i);
		for(j = i;j>-1;j--) {
			div ^= b << j;
			i--;
		}
		
		return mod;
	}
	public static int dis(int a,int b) {
		int s = a,result = 0,lb = Integer.toBinaryString(b).length(),
				la = Integer.toBinaryString(a).length();
		String str = Integer.toBinaryString(a);
		int turn = (str.length()-lb+1);
		
		for(int i=0;i<turn;i++) {
			if(str.length() == la) {
				result <<=1;
				result ^=1;
				s^=(b<<turn-i-1);
				str = Integer.toBinaryString(s);
			}
			else {
				result <<= 1;
			}
			la--;
		}
		return result;
	}
	
	public static int UCLN(int a,int b) {
		if(a%b==0) return b;
		else if(b%a==0) return a;
		
		if(a>b) return UCLN(b,a%b);
		else return UCLN(a,b%a);
	}
	public static int GCD(int x,int y) {
		int a = x, b =y;
		while(b> 0) {
			int c = a % b;
			a = b;
			b = c;
		}
		return a;
	}
	public static int euclid(int m,int b) {
		int a1=1,a2=0,a3=m,b1=0,b2=1,b3=b;
		
		if(b3 == 0) return a3 = GCD(m,b);
		if(b3 == 1) return b3 = GCD(m, b);
		
		while(b3!=1) {
			int c=a3 / b3,c1 = a1 - c*b1, c2 = a2 - c*b2,c3 = a3 - c* b3;
			a1 = b1; a2 = b2; a3 = b3;
			b1 = c1 ; b2 = c2 ; b3 = c3;
			
		}
		if(b2<0) return b2+m;
		return b2;
		
	}
	public static int euclidbit(int m,int b) {
		int a2=0,a3=m,b2=1,b3=b;
		
		while(b3!=1) {
			int c= dis(a3, b3),c2 = a2 ^ multi(c, b2),c3 = a3 ^ multi(c, b3);
			a2 = b2; a3 = b3;
			b2 = c2 ; b3 = c3;
		}
		return b2;
		
	}
	public static int changeByte(int input) {
		byte[] c = {1,1,0,0,0,1,1,0};
		byte[][] x = {
				{1,0,0,0,1,1,1,1},
				{1,1,0,0,0,1,1,1},
				{1,1,1,0,0,0,1,1},
				{1,1,1,1,0,0,0,1},
				{1,1,1,1,1,0,0,0},
				{0,1,1,1,1,1,0,0},
				{0,0,1,1,1,1,1,0},
				{0,0,0,1,1,1,1,1},
		};
		byte[] b = toBinary(input);
		byte[] b_ = {0,0,0,0,0,0,0,0};
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++)
				b_[i] ^= (x[i][j]&b[j]);
			b_[i] ^= c[i];
		}
		String s = new String();
		for(int i =0;i<8;i++)
			s+= Integer.toString(b_[7-i]);
		
		int a= Integer.parseInt(s, 2);
//		System.out.println();
//		System.out.println("binary: "+s);
//		System.out.println("int: "+ a);
//		System.out.println("hex: " +Integer.toHexString(a));
		return a;
	}
	public static int inverchangeByte(int input) {
		byte[] c = {1,0,1,0,0,0,0,0};
		byte[][] x = {
				{0,0,1,0,0,1,0,1},
				{1,0,0,1,0,0,1,0},
				{0,1,0,0,1,0,0,1},
				{1,0,1,0,0,1,0,0},
				{0,1,0,1,0,0,1,0},
				{0,0,1,0,1,0,0,1},
				{1,0,0,1,0,1,0,0},
				{0,1,0,0,1,0,1,0},
		};
		byte[] b = toBinary(input);
		byte[] b_ = {0,0,0,0,0,0,0,0};
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++)
				b_[i] ^= (x[i][j]&b[j]);
			b_[i] ^= c[i];
		}
		String s = new String();
		for(int i =0;i<8;i++)
			s+= Integer.toString(b_[7-i]);
		
		int a=Integer.parseInt(s, 2);
//		System.out.println();
//		System.out.println("binary: "+s);
//		System.out.println("int: "+ (a= Integer.parseInt(s, 2)));
//		System.out.println("hex: " +Integer.toHexString(a));
		return a;
	}
	
	private static byte[] toBinary(int input) {
		byte[] a = new byte[8];
		for(int i=0;i<8;i++) {
			a[i] = (byte)(input%2);
			input /= 2;
		}
		return a;
	}
}
