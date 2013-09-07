/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */



package extra;
import java.awt.*;
import java.awt.image.*;


public class Tools {
	public Tools(){
	}
	
	public static Image mergeImages(Image img1, Image img2){

		BufferedImage image1 = toBufferedImage(img1);
		BufferedImage image2 = toBufferedImage(img2);

		int w = Math.max(image1.getWidth(), image2.getWidth());
		int h = Math.max(image1.getHeight(), image2.getHeight());
		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics g = combined.getGraphics();
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, 0, 0, null);

		return (Image) combined;
		
	}
	
	
	public static BufferedImage toBufferedImage(Image source){
		int w = 128;
		int h = 128;
		BufferedImage bImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)bImage.getGraphics();
		g2d.drawImage(source, 0, 0, null);
		g2d.dispose();
		return bImage;
	}
	

	public static int[] bubbleSort(int [] array){
		boolean stillSwaping = true;
		while(stillSwaping){
			stillSwaping = false;
			for(int i = 0; i < array.length - 1; i++){
				if(array[i] < array[i+1]){
					int temp = array[i];
					array[i] = array[i+1];
					array[i+1] = temp;
					stillSwaping = true;
				}
			}
		}
		return array;
	}
}
