package GUI;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Sprite {
	protected int width,height,ID;
	protected int[] pixel;// 24 bit 
	
//	public Sprite(SpriteSheet sheet,int startX,int startY,int w,int h,int id) {
//		this.width = w;
//		this.height =h;
//		this.ID=id;
//		pixel= new int[width*height];
//		
//		for(int i=0;i<h;i++)
//			for(int j=0;j<w;j++)
//		{
//			pixel[j+w*i]=
//					sheet.getPixeli((j+startX)+(i+startY)*sheet.getSizeX());
//		}
////		sheet.getImage().getRGB(startX,startY,width,height,pixel,0,width);
//	}
	public Sprite(BufferedImage image) {
		width= image.getWidth();
		height = image.getHeight();
		
		pixel = new int[width*height];
//		image.getRGB(0,0,width,height,pixel,0,width);

		pixel= ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	public Sprite() {}
	public void setID(int id) {
		ID=id;
	}
	public int getID() {
		return ID;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int[] getpixel() {
		return pixel;
	}
	public int getPixeli(int i) {
		return pixel[i];
	}
}
