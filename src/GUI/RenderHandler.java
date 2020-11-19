package GUI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
	private BufferedImage view;
	private int[] 	pixels;
	private Sprite[] sp;
//	private Random ran;
	public Rectangle2D[] tiletype;

	private boolean design=false;
	
	public RenderHandler(int width, int height) {
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels= ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
		
	}
	public RenderHandler(int width, int height,BufferedImage image,BufferedImage image2) {
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		
		pixels= ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
		
		sp = new Sprite[240];
		
//		for(int i=0;i<10;i++)
//			for(int j=0;j<24;j++) {
//				sp[j+i*24] = spSheet.getsprite(0,4);
////				sp[j+i*40] = spSheet.getsprite(Math.abs(ran.nextInt())%6, Math.abs(ran.nextInt())%9);
//			}
		
//		tiletype= new Rectangle2D[240];
//		for(int i=0;i<10;i++)
//			for(int j=0;j<24;j++)
//				tiletype[i*24+j]=new Rectangle2D.Float(camera.w+j*16, camera.h+600+i*16, 16, 16);
//		map2 = new int[288000];
//		for(int i=0;i<288000;i++)
//			map2[i]=110023;
	}
	public RenderHandler(){
		
		tiletype= new Rectangle2D[240];
		for(int i=0;i<10;i++)
			for(int j=0;j<24;j++)
				tiletype[i*24+j]= new Rectangle2D.Float(j*16,i*16, 16, 16);
	}
	public void renderArray(int[] renderpixel,int renderwidth,int renderheight,int 	xposition,int yposition,int xzoom,int yzoom) {
		for(int y=0;y<renderheight;y++) 
			for(int x=0;x<renderwidth;x++) 
				for(int yz=0;yz<yzoom;yz++)
					for(int xz=0;xz<xzoom; xz++) 
						setPixel(renderpixel[x+y*renderwidth], (x*xzoom)+ xposition+xz, (y*yzoom)+yposition+yz);
						
	}
	public void render(Graphics graphics) {
		graphics.drawImage(view,0,0,view.getWidth(),view.getHeight(),null);
		
		if(design)
		for(int i=0;i<10;i++)
			for(int j=0;j<24;j++) {
				graphics.drawRect(j*16,i*16, 16,16);
			}
	}
	
	private void setPixel(int pixel, int x, int y) {
		int pixelIndex = (x) + (y)*view.getWidth();
		
		if(pixels.length > pixelIndex)
		pixels[pixelIndex]=pixel;
		
		
	}
	public void clear() {
		for(int i=0;i<pixels.length;i++)
			pixels[i]=0;
	}
	
	public void renderMap(Sprite spr) {
		for(int i=0;i<40;i++)
			for(int j=0;j<40;j++) {
				renderSprite(sp[j+i*40],j*40,i*40,1,1);
				
		}
		//renderSprite(spr,100,40,3,3);
	}
	public void renderImage(BufferedImage image,int xposition, int yposition,int xzoom,int yzoom) {
		int[] imagePixel =((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		renderArray(imagePixel, image.getWidth(), image.getHeight(), xposition, yposition, xzoom, yzoom);
	}
	
	public void renderSprite(Sprite sprite,int xPosition,int yPosition,int xZoom,int yZoom) {
		renderArray(sprite.getpixel(),sprite.getWidth(),sprite.getHeight(),xPosition,yPosition,xZoom,yZoom);
		
	}
	
	public void changeDesign() {
		if(design) {
			
			design=false;
			return;
		}
		design=true;
	}
	public boolean isDesign() {
		return design;
	}
}
