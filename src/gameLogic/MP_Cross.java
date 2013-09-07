/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;


public class MP_Cross extends MapPiece{
	private static final boolean[] PASSAGES_D0 = {true, true, true, true};

	public MP_Cross(Board b, int direction) {
		super(b, direction);
		loadImages();
	}

	public boolean[] getPassagesD0() {
		return PASSAGES_D0.clone();
	}
}
