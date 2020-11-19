package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class SHA256 {
	int[] H = {
			0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A, 
			0x510E527F, 0x9B05688C, 0x1F83D9AB,0x5BE0CD19 
	},
			k = {
				0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
				0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
				0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
				0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
				0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 
				0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 
				0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 
				0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
			};
	int[] hash;
	// khởi tạo biê�?n
	public SHA256() {
		init();
	}
	public void init() {
		hash = new int[8];
		for(int i=0;i<8;i++) {
			hash[i] = H[i];
		}
	}
	public String hashString(String input) {// băm chuỗi
		init();
		byte[] a = input.getBytes();
		long size = a.length*8;
		int add0 = 64 - (a.length+9)%64;
		if(add0 < 0)
			add0 += 64;
		byte[] output = new byte[add0+a.length+9];
		Arrays.fill(output, (byte)0);
		System.arraycopy(a, 0, output, 0, a.length);
		output[a.length] = (byte) (1<<7);
		
		addBigendient(size, output);
		
		update(output);
		
		return (renderHex(hash[0]) + renderHex(hash[1])
		+renderHex(hash[2]) + renderHex(hash[3])
		+renderHex(hash[4]) + renderHex(hash[5])
		+renderHex(hash[6]) + renderHex(hash[7]));
	}
	public String hashfile(File file,boolean check) {// băm file
		init();
		try {
			 InputStream input = new FileInputStream(file);
			 byte[] buffer = new byte[64]; // tạo mảng đọc 64 byte của file lần lượt
			 int nbuffer;
			 int length = 0;
			 while((nbuffer = input.read(buffer))!= -1) {// lần lượt đọc 64 byte của file
				 if(nbuffer == 64) {
					 length+=512;
					 update(buffer);
				 }
				 else
					 break;
			};
			 input.close();
			 return hash(buffer, length,nbuffer);
			 }
			 catch(FileNotFoundException e) {
				 System.out.println("không tìm thấy file");
				 System.out.println("địa chỉ: " + file.getAbsolutePath());
				 
			 }
			 catch(IOException e) {
				 e.printStackTrace();
			 }
		return null;
	}
	
	public String hash(byte[] input,long leng,int offset) {// hỗ trợ băm file băm lượt cuỗi cùng
		
		long size = leng + offset*8;
		int add0 = 64 - (offset+9)%64;
		if(add0 < 0)
			add0 += 64;
		byte[] output = new byte[add0+offset+9];
		
		Arrays.fill(output, (byte)0);
		System.arraycopy(input, 0, output, 0, offset);
		
		output[offset] = (byte) (1<<7);
		addBigendient(size, output);
		
		update(output);
		
		System.out.println(renderHex(hash[0]) + renderHex(hash[1])
		+renderHex(hash[2]) + renderHex(hash[3])
		+renderHex(hash[4]) + renderHex(hash[5])
		+renderHex(hash[6]) + renderHex(hash[7]));
		
		return renderHex(hash[0]) + renderHex(hash[1])
		+renderHex(hash[2]) + renderHex(hash[3])
		+renderHex(hash[4]) + renderHex(hash[5])
		+renderHex(hash[6]) + renderHex(hash[7])
		;
	}
	public void update(byte[] input) {
		int count = input.length/64;
		
		for(int i=0;i < count ;i++) {
			// chia thành 16 nho�?m
			// mở rộng 16 nho�?m thành 64 nho�?m
			int[] w = new int[64];
			for(int j = 0;j< 16 ; j++)
				{
				w[j] = toint(input,j*4+i*64);
				}
			for(int j = 16;j< 64 ; j++)
				{
				int s0 = rotRight(w[j-15],7)^rotRight(w[j-15],18)^(w[j-15]>>>3);
				int s1 = rotRight(w[j-2],17)^rotRight(w[j-2],19)^(w[j-2] >>>10);

				w[j] = w[j - 16]+s0+w[j-7]+s1;
				
				}
			// khởi tạo gia�? trị băm cho nho�?m
			int a = hash[0],
			b = hash[1],
			c = hash[2],
			d = hash[3],
			e = hash[4],
			f = hash[5],
			g = hash[6],
			h = hash[7];
			// vòng lặp chi�?nh
			
			for(int j=0;j< 64; j++) {
				int s0 = rotRight(a, 2)^rotRight(a, 13)^rotRight(a, 22);
//				if(j == test)
//					System.out.println(Integer.toHexString(s0)+"      ,");
				int maj = (a&b)^(a&c)^(b&c);
				int t2 = s0+maj;
				
				int s1 = rotRight(e,6)^(rotRight(e,11))^(rotRight(e, 25));
				int ch = (e&f)^((~e)&g);
				int t1 = h+s1+ch+ k[j]+w[j];
				
				
				h = g;
				g = f;
				f = e;
				e = d+t1;
				d = c;
				c = b;
				b = a;
				a = t1+t2;
			}
			// cộng gia�? trị băm
			hash[0] += a;
			hash[1] += b;
			hash[2] += c;
			hash[3] += d;
			hash[4] += e;
			hash[5] += f;
			hash[6] += g;
			hash[7] += h;
		}
	}
	// quay a sang phải b bit
	private int rotRight(int a,int b) {
		return (a>>>b)|(a<<(32-b));
	}
	public static void main(String[] args) {// ----------------------------------  main -----
		 SHA256 has = new SHA256();

		 String data = "anh tuan co 1 bitcoin";
		 int dokho = 6;

		 boolean done = false;
		 long nonce = 0;
		 String test = "";

		 while(!done){
		 	String hashdata = data + (nonce++);
		 	test = has.hashString(hashdata);
		 	System.out.println(test);
			 done = true;
		 	for(int i=0;i<dokho;i++) {

				if (test.charAt(i) != '0') {
					done = false;
				}
			}

		 }

		 System.out.println("your bitcoin is : "+test);
//		 System.out.println(has.hashString("bam anh hoang"));
//		 has.hashfile(new File("E:/bt.png"),false);
//		 System.out.println(has.hashString("hoan thanh bai tap"));
//		 has.hashfile(new File("E:/lop10.docx"));
	}
	public void addBigendient(long bigendian,byte[] a) {// thêm sô�? bigendian vào cuỗi chuỗi
		int leng = a.length;
		a[leng - 8] = (byte) (bigendian>>>56);
		a[leng - 7] = (byte) (bigendian>>>48);
		a[leng - 6] = (byte) (bigendian>>>40);
		a[leng - 5] = (byte) (bigendian>>>32);
		a[leng - 4] = (byte) (bigendian>>>24);
		a[leng - 3] = (byte) (bigendian>>>16);
		a[leng - 2] = (byte) (bigendian>>> 8);
		a[leng - 1] = (byte) (bigendian    );
		
	}
	
	/* hàm chuyển đổi từ byte sang int
	* vd: byte :10110001 (sô�? âm) sang int : 00000000000000000000000010110001
	*/
	private int bytetoint(byte a) {
		if(a>0) return a;
		return a&255;
	}
	/*	nho�?m 4 byte 8 bit vào 1 int 32 bit
	 *  vd: 
	 *  byte1 00000001
	 *  byte2 00000011
	 *  byte1 00000111
	 *  byte2 00001111
	 *  int 00000001000000110000011100001111
	 */
	private int toint(byte[] a,int start) {
		int out = 0;
		for(int i=0;i<4;i++) {
			int j = bytetoint(a[i+start]);
			
			out |= (j<<(24-i*8));
		}
		return out;
	}
	/* ta�?ch chuỗi int thành chuỗi byte
	 * 
	
	private byte[] tobyte(int[] a) {
		byte[] out = new byte[a.length*4];
		for(int i=0;i< a.length;i++) {
			for(int j=0;j<4;j++) {
				out[i*4+j] = (byte) (a[i]>>>(24-8*j));
			}
		}
		return out;
	}
	*/
	// hàm bổ sung sô�? 0 trươ�?c chuỗi hex
	public String renderHex(int a) {
		int size = 8 - Integer.toHexString(a).length();
		String out ="";
		for(int i = 0;i< size ; i++) {
			out = "0" + out;
		}
		out += Integer.toHexString(a);
		return out;
	}
}
