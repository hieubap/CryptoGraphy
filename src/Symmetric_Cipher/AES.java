package Symmetric_Cipher;

import java.security.KeyFactory;

public class AES {
	KeyFactory k;
	public String 
			input="123456789123456789123456789111",
			keyin="abcd1234abcd1234abcd1234abcd1234",
			vectors="",
			out="";
	public char[] keyc,outputc,vectorc;// = {
//		"2b","28","ab","09",
//		"7e","ae","f7","cf",
//		"15","d2","15","4f",
//		"16","a6","88","3c",
//	},
//	public String[] outputs= {
//		"19","a0","9a","e9",
//		"3d","f4","c6","f8",
//		"e3","e2","8d","48",
//		"be","2b","2a","08",
//		"32","88","31","e0",
//		"43","5a","31","37",
//		"f6","30","98","07",
//		"a8","8d","a2","34",
//		"39","02","dc","19",
//		"25","dc","11","6a",
//		"84","09","85","0b",
//		"1d","fb","97","32",
//		};
	private int[] output,key,vector,Rcon = {1,2,4,8,16,32,64,128,27,54};
	private int numblock;
	private boolean hexa;
	public AES() {
		
		key = new int[16*11];
		output = new int[16];
		
		if(input.length()%16!=0) {
			int padding = 16 - input.length()%16;
			for(int i=0;i<padding;i++)
				input += " ";
		}
		
		
		outputc = input.toCharArray();
		
		for(int i=0;i<outputc.length;i++) {
			if(i%16==0) System.out.println();
			System.out.print((char)outputc[i]);
		}
		keyc = keyin.toCharArray();
		
		for(int i=0;i<16;i++) {
			output[i] = 16*chartoint(outputc[2*i])+chartoint(outputc[2*i+1]);
			key[i] = 16*chartoint(keyc[2*i])+chartoint(keyc[2*i+1]);
		}
		for(int i=1;i<11;i++) {
			expandkey(i);
		}
	}
	
	
	public AES(String string,String keyinput,boolean hexa) {
		key = new int[16*11];
		output = new int[16];
		
		
		if(hexa) {
			if(string.length()%32 != 0) {// chen them chuoi neu khong du dai
				int padding = 32 - string.length()%32;
				for(int i=0;i<padding;i++)
					string += "0";
			}
		}
		else if(string.length()%16!=0) {
			int padding = 16 - string.length()%16;
			for(int i=0;i<padding;i++)
				string += " ";
		}
		
		this.hexa = hexa;
		if(hexa)
			numblock = string.length()/32;
		else
			numblock = string.length()/16;
		
		outputc = string.toCharArray();
		keyc = keyinput.toCharArray();
		
		if(hexa) {
			for(int i=0;i<16;i++) {
				key[4*(i%4)+i/4] = 16*chartoint(keyc[2*i])+chartoint(keyc[2*i+1]);
				
			}
		}
		else 
			for(int i=0;i<16;i++) {
			key[i] = keyc[4*(i%4)+i/4];
		}
		
		for(int i=1;i<11;i++) {
			expandkey(i);
		}
	}
	public AES(String a,String b,String c,boolean bool) {
		this(a,b,bool);
		vectors = c;
		vector = new int[16];

		vectorc = vectors.toCharArray();
		
		for(int i=0;i<16;i++) {
			vector[4*(i%4)+i/4] = 16*chartoint(vectorc[2*i])+chartoint(vectorc[2*i+1]);
		}
	}

	
	public static void main(String[] args) {
		
		
		
		AES a = new AES("014BAF2278A69D331D5180103643E99A",
				        "E8E9EAEBEDEEEFF0F2F3F4F5F7F8F9FA",
				        true);
		final long start = System.nanoTime();
		
		a.encrypt();
		final long end = System.nanoTime();
//		System.out.println(start);
		System.out.println(end-start);
		
//		a.decrypt();
	}
	public void encrypt() {
		for(int j=0;j<numblock;j++) {
		if(hexa) {
			for(int i=0;i<16;i++) {
				output[4*(i%4)+i/4] = 16*chartoint(outputc[2*i+j*32])+chartoint(outputc[2*i+1+j*32]);
			}
		}
		else 
			for(int i=0;i<16;i++) {
			output[i] = outputc[4*(i%4)+i/4];
		}
		
		
		addroundkey(0);
		
		for(int i=1;i<10;i++) {
		substitutebyte();
		shiftrow();
		mixColumn();
		addroundkey(i);
		}
		
		substitutebyte();
		shiftrow();
		expandkey(10);
		addroundkey(10);
		
		render();
		for(int i=0;i<16;i++) {
			if(output[4*(i%4)+i/4]<16)
				out += "0";
			out += Integer.toHexString(output[4*(i%4)+i/4]);
		}
		}
		System.out.println(out);
	}
	public void encryptCBC() {
		if(hexa) {
			for(int i=0;i<16;i++) {
				output[4*(i%4)+i/4] =  vector[4*(i%4)+i/4];
			}
		}
		else 
			for(int i=0;i<16;i++) {
			output[i] ^= vectorc[4*(i%4)+i/4];
		}
		
		for(int j=0;j<numblock;j++) {
			if(hexa) {
				for(int i=0;i<16;i++) {
					output[4*(i%4)+i/4] ^= (16*chartoint(outputc[2*i+j*32])+chartoint(outputc[2*i+1+j*32]));
				}
			}
			else 
				for(int i=0;i<16;i++) {
				output[i] ^= outputc[4*(i%4)+i/4];
			}
			addroundkey(0);
			
			for(int i=1;i<10;i++) {
			substitutebyte();
			shiftrow();
			mixColumn();
			addroundkey(i);
			}
			
			substitutebyte();
			shiftrow();
			expandkey(10);
			addroundkey(10);
			
			render();
			for(int i=0;i<16;i++) {
				if(output[4*(i%4)+i/4]<16)
					out += "0";
				out += Integer.toHexString(output[4*(i%4)+i/4]);
			}
			}
		System.out.println(out);
	}
	public void decrypt() {
		for(int j=0;j<numblock;j++) {
			if(hexa) {
				for(int i=0;i<16;i++) {
					output[4*(i%4)+i/4] = 16*chartoint(outputc[2*i+j*32])+chartoint(outputc[2*i+1+j*32]);
				}
			}
			else 
				for(int i=0;i<16;i++) {
				output[i] = outputc[4*(i%4)+i/4];
			}
			addroundkey(10);
			invershiftrow();
			inversub();
		
			for(int i=9;i>=1;i--) {
				addroundkey(i);
				invermixColumn();
				invershiftrow();
				inversub();
			}
		
			addroundkey(0);
			for(int i=0;i<16;i++) {
				if(output[4*(i%4)+i/4]<16)
					out += "0";
				out += Integer.toHexString(output[4*(i%4)+i/4]);
			}
			render();
		}
		System.out.println(out);
	}
	
	public void substitutebyte() {
		for(int i=0;i<16;i++) {
			output[i] = S_Box.s_box(output[i]);
		}
	}
	public void inversub() {
		for(int i=0;i<16;i++) {
			output[i] = S_Box.inverse_s_box(output[i]);
		}
	}
	
	public void shiftrow() {
		int a = output[4];
		for(int i=4;i<7;i++) {
			output[i] = output[i+1];
		}
		output[7] = a;
		
		a = output[8];
		output[8] = output[10];
		output[10] = a;
		
		a= output[9];
		output[9] = output[11];
		output[11] = a;
		
		a = output[15];
		for(int i=15;i>12;i--) {
			output[i] = output[i-1];
		}
		output[12] =a;
	}
	public void invershiftrow() {
		int a = output[7];
		for(int i=7;i>=5;i--) {
			output[i] = output[i-1];
		}
		output[4] = a;
		
		a = output[8];
		output[8] = output[10];
		output[10] = a;
		
		a= output[9];
		output[9] = output[11];
		output[11] = a;
		
		a = output[12];
		for(int i=12;i<15;i++) {
			output[i] = output[i+1];
		}
		output[15] =a;
	}
	public void mixColumn() {
		int[] mix = {2,3,1,1,1,2,3,1,1,1,2,3,3,1,1,2};
		int[] stack = new int[16];
		
		int a = 0;
		for(int i=0;i<16;i++) {
			a = 0;
			for(int j=0;j<4;j++)
				a ^= S_Box.mod(S_Box.multi(mix[j+(i/4)*4], output[i%4+j*4]),283);
			stack[i] = a;
		}
		for(int i=0;i<16;i++)
			{
			output[i] = stack[i];
			}
	}
	public void invermixColumn() {
		int[] mix = {14,11,13,9,9,14,11,13,13,9,14,11,11,13,9,14};
		int[] stack = new int[16];
		
		int a = 0;
		for(int i=0;i<16;i++) {
			a = 0;
			for(int j=0;j<4;j++)
				a ^= S_Box.mod(S_Box.multi(mix[j+(i/4)*4], output[i%4+j*4]),283);
			stack[i] = a;
		}
		for(int i=0;i<16;i++)
			{
			output[i] = stack[i];
			}
	}
	
	public void addroundkey(int turn) {
		for(int i=0;i<16;i++) {
			output[i] ^= key[i+turn*16]; 
		}
	}
	public void expandkey(int turn,boolean a) {
		int[] old = new int[4];
		for(int i=0;i<4;i++) {
			old[i] = key[i*4+3];
		}
		
		rotWord(old,turn);
		subWord(old);
		old[0] = old[0]^Rcon[turn];
		
		
		for(int i=0;i<4;i++)
			{
			key[i*4] = key[i*4]^old[i];
			}
		for(int i=1;i<4;i++) {
			for(int j=0;j<4;j++) {
				key[j*4+i] = key[j*4+i]^key[j*4+i-1];
			}
		}
	}
	public void expandkey(int turn) {
		int[] old = new int[4];
		for(int i=0;i<4;i++) {
			old[i] = key[i*4+3+16*(turn-1)];
		}
		
		rotWord(old,turn);
		subWord(old);
		old[0] = old[0]^Rcon[turn-1];
		
		
		for(int i=0;i<4;i++)
			{
			key[i*4+16*turn] = key[i*4+16*(turn-1)]^old[i];
//			System.out.println(Integer.toHexString(key[i*4+16*turn]));
			}
		for(int i=1;i<4;i++) {
			for(int j=0;j<4;j++) {
				key[j*4+i+16*turn] = key[j*4+i+16*(turn-1)]^key[j*4+i-1+16*(turn)];
			}
		}
	}
	private void rotWord(int[] rot,int turn) {
		int change = key[3+16*(turn-1)];
		for(int i=0;i<3;i++) {
			rot[i] = rot [i+1];
//			System.out.println(Integer.toHexString(rot[i]));
		}
		rot[3] = change;
//		System.out.println(Integer.toHexString(rot[3]));
		
	}
	private void subWord(int[] sub) {
		for(int i=0;i<4;i++)
		{
			sub[i] = S_Box.s_box(sub[i]);
//			System.out.println(Integer.toHexString(sub[i]));
		}
		
	}
	
	public void render() {
		for(int i=0;i<16;i++) {
			if(i%4==0) System.out.println();
			System.out.print(Integer.toHexString(output[i]) + "  ");
		}
		System.out.println();
	}
	public void renderchar() {
		for(int i=0;i<16;i++) {
			if(i%4==0) System.out.println();
			System.out.print((char)(output[i]) + "  ");
		}
		System.out.println();
	}
	public void renderkey(int turn) {
		System.out.println();
		for(int i=0;i<16;i++) {
			if(i%4==0) System.out.println();
			System.out.print(Integer.toHexString(key[i+16*turn]) + "  ");
		}
		System.out.println();
	}
	public void renderkeychar(int turn) {
		System.out.println();
		for(int i=0;i<16;i++) {
			if(i%4==0) System.out.println();
			System.out.print((char)(key[i+16*turn]) + "  ");
		}
		System.out.println();
	}
	public static int hextoint(String s) {
		char[] c = s.toCharArray();
		for(int i=0;i<2;i++)
		if(c[i]>=65&&c[i]<=70) {
			c[i] -= 55;
		}
		else if(c[i]>=97&&c[i]<=102) {
			c[i] -= 87;
		}
		else {
			c[i] -= 48;
		}
		return c[0]*16+c[1];
		
	}
	public static int chartoint(char c) {
		
		if(c>=65&&c<=70) {
			c -= 55;
		}
		else if(c>=97&&c<=102) {
			c -= 87;
		}
		else {
			c -= 48;
		}
		return c;
		
	}
//	private String inttostring(int input) {
//		
//	}
	
}
