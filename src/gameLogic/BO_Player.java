/**
 * Link�pings Universitet
 * Medieteknik �r 2
 * Projektarbete i TNM040 - Kommunikation och Anv�ndargr�nssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;


public class BO_Player extends BO_Penguin{
	public BO_Player(MapPiece mp, String color) {
		super(mp, color);
		loadImages();
	}
}
