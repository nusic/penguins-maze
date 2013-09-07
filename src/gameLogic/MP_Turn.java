/**
 * Link�pings Universitet
 * Medieteknik �r 2
 * Projektarbete i TNM040 - Kommunikation och Anv�ndargr�nssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;

public class MP_Turn extends MapPiece{
	private static final boolean[] PASSAGES_D0 = {false, true, true, false};
	
	public MP_Turn(Board b, int direction) {
		super(b, direction);
		loadImages();
	}

	public boolean[] getPassagesD0(){
		return PASSAGES_D0.clone();
	}
}
