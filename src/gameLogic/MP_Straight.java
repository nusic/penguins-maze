/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;

public class MP_Straight extends MapPiece{
	private static final boolean[] PASSAGES_D0 = {true, false, true, false};
	
	public MP_Straight(Board b, int direction){
		super(b, direction);
		loadImages();
	}
	
	public boolean[] getPassagesD0(){
		return PASSAGES_D0.clone();
	}
}
