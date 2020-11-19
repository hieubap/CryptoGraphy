package Symmetric_Cipher;

public class DES {
	public int[] output,keyinput,C,D,L,R;
	private static final int[] IP = {
			57, 49, 41, 33, 25, 17, 9, 1,
			59, 51, 43, 35, 27, 19, 11, 3,
			61, 53, 45, 37, 29, 21, 13, 5,
			63, 55, 47, 39, 31, 23, 15, 7,
			56, 48, 40, 32, 24, 16, 8, 0,
			58, 50, 42, 34, 26, 18, 10, 2,
			60, 52, 44, 36, 28, 20, 12, 4,
			62, 54, 46, 38, 30, 22, 14, 6 	
	},
			IP_ = {
					40,8,48,16,56,24,64,32,
					39,7,47,15,55,23,63,31,
					38,6,46,14,54,22,62,30,
					37,5,45,13,53,21,61,29,
					36,4,44,12,52,20,60,28,
					35,3,43,11,51,19,59,27,
					34,2,42,10,50,18,58,26,
					33,1,41,9,49,17,57,25
			},
			PC1 = {
					57, 49, 41, 33, 25,17,9,
					1,58,50,42,34,26,18,
					10,2,59,51,43,35,27,
					19,11,3,60,52,44,36,
					63,55,47,39,31,23,15,
					7,62,54,46,38,30,22,
					14,6,61,53,45,37,29,
					21,13,5,28,20,12,4
					
			},
			PC2 = {
					14,17,11,24,1,5,
					3,28,15,6,21,10,
					23,19,12,4,26,8,
					16,7,27,20,13,2,
					41,52,31,37,47,55,
					30,40,51,45,33,48,
					44,49,39,56,34,53,
					46,42,50,36,29,32
			},
			E = {
					32,1,2,3,4,5,
					4,5,6,7,8,9,
					8,9,10,11,12,13,
					12,13,14,15,16,17,
					16,17,18,19,20,21,
					20,21,22,23,24,25,
					24,25,26,27,28,29,
					28,29,30,31,32,1
			},
			P = {
					16,7,20,21,
					29,12,28,17,
					1,15,23,26,
					5,18,31,10,
					2,8,24,14,
					32,27,3,9,
					19,13,30,6,
					22,11,4,25
			};
	private int[][] S = {
			{
				14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,
				0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
				4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
				15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13,
			},
			{
				15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,
				3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
				0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
				13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9,
			},
			{
				10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,
				13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
				13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,
				1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12,
			},
			{
				7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,
				13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
				10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,
				3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14,
			},
			{
				2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,
				14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
				4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,
				11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3,
			},
			{
				12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,
				10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,
				9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,
				4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13,
			},
			{
				4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,
				13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
				1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,
				6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12,
			},
			{
				13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
				1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
				7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
				2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11,
			}
	};
	
	public DES() {
		output= new int[64];
//		input = new int[64];
		keyinput = new int[64];
		C = new int[28];
		D = new int[28];
		String in = "02468aceeca86420",
				k=  "0f1571c947d9e859";
//		             13345799BBCDDFF1  133457799BBCDFF1
		char[] inc = in.toCharArray(),
				kc = k.toCharArray();
		
		for(int i=0;i<64;i++) {
			output[i] = hextobin(inc[i/4])[i%4];
			keyinput[i] = hextobin(kc[i/4])[i%4];
		}
		
		int[] key56 = new int[56];
		for(int i=0;i<56;i++) {
			key56[i] = keyinput[PC1[i]-1];
		}
		for(int i=0;i<28;i++) {
			C[i] = key56[i];
			D[i] = key56[i+28];
		}
//		shiftleft(1);
//		render();
//		renderkey();
		
		L = new int[32];
		R = new int[32];
		
	}
	public static void main(String[] args) {
//		DES des  = new DES();
//		for(int i=0;i<8;i++)
//		System.out.print(des.inttobinary(7)[i]+" ");
//		des.encrypt();
//		des.inttobinary(1);
		byte a = 127;
//		for(int i=0;i<7;i++) {
//			a += Math.pow(2, i);
//		}
		a<<=1;
		System.out.println(a);
	}
	public void encrypt() {
		render();
		hoanvip(output, IP,true);
		for(int j=1;j<17;j++) {
			System.out.println("-------------");
		int[] k = expandkey(j);
//		renderArray(k, 4, 4);
		for(int i=0;i<32;i++) {
			L[i] = output[i+32];
		}
		
		int[] f = f(L,k);
		
		for(int i=0;i<32;i++) {
			R[i] = output[i] ^ f[i];
		}
		for(int i=0;i<output.length/2;i++) {
			output[i] = L[i];
			output[i+32] = R[i];
		}
//		render();
		}
		
		
		for(int i=0;i<output.length/2;i++) {
			output[i] = R[i];
			output[i+32] = L[i];
		}
		hoanvip(output, IP_,false);
		render();
	}
	public int[] f(int[] r,int[] k) {
		int[] e = new int[48];
		for(int i=0;i<48;i++) {
			e[i] = r[E[i]-1];
			e[i] ^= k[i];
		}
		renderArray(e, 7, 4);
		int[] p = new int[32];
		
		for(int i=0;i<8;i++) {
			int[] bin = inttobinary(S[i][(e[i*6]*2+e[i*6+5])*16+(e[i*6+1]*8+e[i*6+2]*4+e[i*6+3]*2+e[i*6+4])]);
			
//			System.out.println(S[i][(e[i*6]*2+e[i*6+5])*16+(e[i*6+1]*8+e[i*6+2]*4+e[i*6+3]*2+e[i*6+4])]);
			
			for(int j=0;j<4;j++) {
				p[i*4+j] = bin[j];
			}
		}
//		renderArray(p, 4, 4);
		
		hoanvip(p, P,false);
		return p;
	}
	public int[] expandkey(int turn) { // 48 bit done
		
		if(turn == 1|| turn == 2 || turn == 9 || turn == 16)
		shiftleft(1);
		else shiftleft(2);

		int[] key56 = new int[56];
		for(int i=0;i<28;i++) {
			key56[i] = C[i];
			key56[i+28] = D[i];
		}
		
		int[] key = new int[48];
		for(int i=0;i<48;i++) {
			key[i] = key56[PC2[i]-1];
		}

//		renderArray(key, 8, 4);
		return key;
		
	}
	public void render() {
		for(int i=0;i<64;i++) {
			if(i%8 == 0) System.out.println();
			System.out.print(output[i]+"   ");
		}
		for(int i=0;i<16;i++) {
			System.out.print(inttochar(output[i*4]*8+output[i*4+1]*4+output[i*4+2]*2+output[i*4+3]));
		}
		System.out.println();
	}
	public void renderkey() {
		for(int i=0;i<64;i++) {
			if(i%8 == 0) System.out.println();
			System.out.print(keyinput[i]+"   ");
		}
		for(int i=0;i<16;i++) {
			System.out.print(inttochar(keyinput[i*4]*8+keyinput[i*4+1]*4+keyinput[i*4+2]*2+keyinput[i*4+3]));
		}
		System.out.println();
	}
	
	public int[] f() {
		int[] result = new int[32];
		return result;
	}
	public static void hoanvip(int[] input,int[] p,boolean addone) {
		int[] creat = new int[input.length];
		for(int j=0;j<input.length;j++) {
			creat[j] = input[j];
		}
		for(int j=0;j<input.length;j++) {
			if(addone)
			input[j] = creat[p[j]];
			else
				input[j] = creat[p[j]-1];
		}
	}
	
	
	public int[] inttobinary(int input) {
		int[] out = new int[4];
		for(int i=0;i<4;i++) {
			out[3-i] = input%2;
			input /=2;
		}
		
		return out;
	}
	
	public void shiftleft(int f) {
		for(int i=0;i<f;i++) {
			int changeC = C[0],changeD = D[0];
			for(int j=0;j<27;j++)
				{
				C[j] = C[j+1];
				D[j] = D[j+1];
				}
			C[27] = changeC;D[27] = changeD;
		}
	}
	private int[] hextobin(char a) {
		int[] out = new int[4];
		int b = AES.chartoint(a);
		for(int i=3;i>-1;i--) {
			out[i] = b%2;
			b/=2;
		}
		return out;
	}
	public static char inttochar(int i) {
		if(i<10) return (char)(i+48);
		return (char)(i+55);
	}
	public static void renderArray(int[] in,int w,int bit) {
		System.out.println();
		for(int i=0;i<in.length;i++) {
			if(i%w ==0) System.out.println();
			System.out.print(in[i]+"    ");
		}
		for(int i=0;i<in.length/bit;i++) {
			int print = 0;
			for(int j=0;j<bit;j++) {
				print += (int)Math.pow(2, 3-j)*in[i*bit+j];
			}
			System.out.print(inttochar(print));
		}
		System.out.println();
	}

}
