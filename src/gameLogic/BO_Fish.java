/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;

import java.util.Random;

public class BO_Fish extends BO_Item{
	public BO_Fish(MapPiece mp) {
		super(mp);
		loadImages();
		rotateRandomly();
	}
	
	private void rotateRandomly(){
		Random r = new Random();
		int turns = r.nextInt()%4;
		for (int i=0; i<=turns; i++)
			this.turnRight();
	}
}
