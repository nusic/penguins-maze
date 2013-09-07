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

public abstract class BO_BoardObject {
	
	public static final int D0 = 0, D90 = 1, D180 = 2, D270 = 3;
	protected int direction;
	protected MapPiece mapPiece;
	protected Image[][] imgs;
	protected Image[] bigImgs;
	
	public BO_BoardObject(MapPiece mp){
		
		imgs = (this instanceof BO_Penguin) ? new Image[4][4] : new Image[4][1];
		bigImgs = new Image[4];
		direction = D0;
		mapPiece = mp;
	}
	
	// --- GET --- //
	public MapPiece getMapPiece(){
		return mapPiece;
	}
	
	public Board getBoard(){
		return mapPiece.getBoard();
	}
	
	public Image getImage(){
		return imgs[direction][0];
	}
	
	public Image getBigImage(){
		return bigImgs[direction];
	}
		
	public int getX(){
		return getBoard().getPositionOf(mapPiece)[0];
	}
	
	public int getY(){
		return getBoard().getPositionOf(mapPiece)[1];
	}
	
	public boolean isOnBoard(){
		return getBoard() != null;
	}
	
	
	
	// --- SET --- //
	protected void setMapPiece(MapPiece mp){
		mapPiece = mp;
	}
	
	protected void setMapPiece(int x, int y){
		mapPiece = getBoard().getMapPieceAt(x, y);
	}
	
	// --- HANDLING DIRECTIONS --- //
	public void turnLeft(){
		direction--;
		if(direction < D0){
			direction = D270;
		}
	}
	
	public void turnRight(){
		direction++;
		if(direction > D270){
			direction = D0;
		}
	}
	
	public void setDirection(int dir){
		direction = 0;
		for(int i = D0; i<dir; i++)
			turnRight();
	}
	
	
	
	// --- HAS --- //
	protected boolean hasFullPassageNorth(){
		return mapPiece.hasFullPassageNorth();
	}
	
	protected boolean hasFullPassageEast(){
		return mapPiece.hasFullPassageEast();
	}
	
	protected boolean hasFullPassageSouth(){
		return mapPiece.hasFullPassageSouth();
	}
	
	protected boolean hasFullPassageWest(){
		return mapPiece.hasFullPassageWest();
	}
	
	
	// --- LOAD --- //
	
	protected void loadImages(){
		for (int i = 0; i < imgs.length; i++){
			imgs[i][0] = new ImageIcon("imgs//BoardObjects//" + this.getClass().getSimpleName() + i + ".png").getImage();
		}
		for (int i = 0; i < bigImgs.length; i++){
			bigImgs[i] = new ImageIcon("imgs//BoardObjects//" + this.getClass().getSimpleName() + "_Big" + i + ".png").getImage();
		}
	}
	
	
	/**
	 * Calculates all reachable coordinates from the position of this object. Uses calcReachableR() as the recursive method. 
	 * @return A vector with coordinates
	 */
	public Vector<int[]> calcReachable(){
		int[] pos = mapPiece.getPosition();
		return calcReachableR(pos[0], pos[1], new Vector<int[]>());
	}
	
	private Vector<int[]> calcReachableR(int x, int y, Vector<int[]> posVec){
		int[] pos = {x, y};
		posVec.add(pos);
		
		if(vectorIndexFor(x, y-1, posVec) == -1 && getBoard().hasFullPassageNorthFrom(x, y))
			calcReachableR(x, y-1, posVec);
		if(vectorIndexFor(x+1, y, posVec) == -1 && getBoard().hasFullPassageEastFrom(x, y))
			calcReachableR(x+1, y, posVec);
		if(vectorIndexFor(x, y+1, posVec) == -1 && getBoard().hasFullPassageSouthFrom(x, y))
			calcReachableR(x, y+1, posVec);
		if(vectorIndexFor(x-1, y, posVec) == -1 && getBoard().hasFullPassageWestFrom(x, y))
			calcReachableR(x-1, y, posVec);
		
		return posVec;
	}
	
	protected static int vectorIndexFor(int x, int y, Vector<int[]> v){
		for(int i = 0; i<v.size(); i++){
			if(x == v.elementAt(i)[0] && y == v.elementAt(i)[1])
				return i;
		}
		return -1;
	}
	
	protected static int[] getDoubletIndices(Vector<int[]> v){
		for(int i = 0; i<v.size(); i++){
			for(int j = 0; j<v.size(); j++){
				if(i != j){
					if(v.elementAt(i)[0] == v.elementAt(j)[0]
					&& v.elementAt(i)[1] == v.elementAt(j)[1]){
						int[] indices = {Math.min(i, j), Math.max(i, j)};
						return indices;
					}	
				}
			}
		}
	return null;
	}
}
