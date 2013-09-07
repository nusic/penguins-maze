/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;

import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;

public abstract class BO_Penguin extends BO_BoardObject{
	public String color;
	protected int score;
	
	public BO_Penguin(MapPiece mp, String color) {
		super(mp);
		direction = D180;
		this.color = color;
		score = 0;
	}
	
	public int getScore(){
		return score;
	}
	
	public Image getImage(int spriteIndex){
		return imgs[direction][spriteIndex];
	}
	
	public boolean canReach(int[] pos){
		return canReach(pos[0], pos[1]);
	}
	
	public boolean canReach(int x, int y){
		Vector<int[]> v = calcReachable();
		for(int i = 0; i<v.size(); i++){
			if(x == v.elementAt(i)[0] && y == v.elementAt(i)[1])
				return true;
		}
		return false;
	}
	
	// --- MOVING --- //
	public void walkTo(MapPiece mp){
		setMapPiece(mp);
	}
	
	public void walkTo(int x, int y){
		walkTo(getBoard().getMapPieceAt(x, y));
	}
	
	public void walkSafelyTo(MapPiece mp){
		if(canReach(mp.getPosition()))
			walkTo(mp);
	}
	
	public void walkSafelyTo(int x, int y){
		walkSafelyTo(getBoard().getMapPieceAt(x, y));
	}

	public Vector<int[]> getPathTo(int x, int y){
		Vector<int[]> v = calcReachable();
		int index = vectorIndexFor(x, y, v);
		if(index == -1)
			return null;
		
		removeElementsBetween(index+1, v.size(), v);
		removeNotBorderingSequences(v);
		removeDetours(v);

		return v;
	}
	
	private void removeNotBorderingSequences(Vector<int[]> v){
		for(int i = 0; i<v.size(); i++){
			if (i+1 < v.size() && !bordering(v.elementAt(i)[0], v.elementAt(i)[1], v.elementAt(i+1)[0], v.elementAt(i+1)[1])){
				v.removeElementAt(i);
				i = -1;
			}
		}
	}
	
	private void removeDetours(Vector<int[]> v){
		for(int i = 0; i<v.size(); i++){
			for(int j = 0; j<v.size(); j++){
				if(Math.abs(i-j) > 1 && bordering(v.elementAt(i)[0], v.elementAt(i)[1], v.elementAt(j)[0], v.elementAt(j)[1]))
					removeElementsBetween(Math.min(i, j)+1, Math.max(i, j), v);
			}
		}
	}
	
	
	private boolean bordering(int x1, int y1, int x2, int y2){
		if( (x1 == x2-1 && y1 == y2 && getBoard().hasFullPassageEastFrom(x1, y1))|| 
			(x1 == x2+1 && y1 == y2 && getBoard().hasFullPassageWestFrom(x1, y1))|| 
			(y1 == y2-1 && x1 == x2 && getBoard().hasFullPassageSouthFrom(x1, y1))|| 
			(y1 == y2+1 && x1 == x2 && getBoard().hasFullPassageNorthFrom(x1, y1)))
			return true;
		return false;
	}
	
	public static void removeElementsBetweenDoublets(Vector<int[]> v){
		int[] indices = getDoubletIndices(v);
		while(indices != null){
			removeElementsBetween(indices[0], indices[1], v);
		}
	}
	
	private static void removeElementsBetween(int i, int j, Vector<int[]> v){
		int l = Math.abs(i-j);
		for(int k = 0; k<l && i<v.size(); k++)
			v.remove(i);
	}
	
	public void loadImages(){
		for (int i = 0; i < imgs.length; i++){
			for (int j = 0; j < imgs[0].length; j++){
				imgs[i][j] = new ImageIcon("imgs//BoardObjects//PenguinsSprites//BO_Penguin_" + color + i + "_" + j + ".png").getImage();
			}
		}
		for (int i = 0; i < bigImgs.length; i++){
			bigImgs[i] = new ImageIcon("imgs//BoardObjects//BO_Penguin_" + color + "_Big" + i + ".png").getImage();
		}
	}
	
}
