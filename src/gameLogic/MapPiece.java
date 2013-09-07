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
import java.util.Random;

import javax.swing.ImageIcon;


public abstract class MapPiece {
	
	private Board board;
	public boolean[] passages;
	private int direction;
	public static final int D0 = 0, D90 = 1, D180 = 2, D270 = 3;
	private Image[] imgs;
	private Image[] bigImgs;
	
	public MapPiece(Board b, int direction){
		board = b;
		imgs = new Image[4];
		bigImgs = new Image[4];
		setDirection(direction);
	}
	
	protected void loadImages(){
		for(int i = 0; i<imgs.length; i++){
			imgs[i] = new ImageIcon("imgs/MapPieces/" + getClass().getSimpleName() + i + ".png").getImage();
			bigImgs[i] = new ImageIcon("imgs/MapPieces/" + getClass().getSimpleName() + "_Big" + i + ".png").getImage();
		}
	}
		
	
	// --- GET --- //
	public int getDirection(){
		return direction;
	}
	
	public int[] getPosition(){
		return board.getPositionOf(this);
	}
	
	public Board getBoard(){
		return board;
	}
	
	public Image getImage(){
		return imgs[direction];
	}
	
	public Image getBigImage(){
		return bigImgs[direction];
	}
	
	// --- SET --- //
	protected void setBoard(Board b){
		board = b;
	}
	
	public void setDirection(int dir){
		direction = 0;
		passages = getPassagesD0();
		for(int i = D0; i<dir; i++)
			turnRight();
	}
	
	

	
	// --- HANDELING PASSAGES --- //	
	public abstract boolean[] getPassagesD0();

	public void turnLeft(){
		direction--;
		turnPassagesLeft();
		if(direction < D0){
			direction = D270;
			passages = getPassagesD0();
		}
	}
	
	public void turnRight(){
		direction++;
		turnPassagesRight();
		if(direction > D270){
			direction = D0;
			passages = getPassagesD0();
		}
	}
	
	private void turnPassagesLeft(){
		boolean temp = passages[0];
		for(int i = 0; i<3; i++){
			passages[i] = passages[i+1];
		}
		passages[3] = temp;
	}
	
	private void turnPassagesRight(){
		boolean temp = passages[3];
		for(int i = 3; i>0; i--){
			passages[i] = passages[i-1];
		}
		passages[0] = temp;
	}
	
	protected boolean hasPassageNorth(){
		return passages[0];
	}
	
	protected boolean hasPassageEast(){
		return passages[1];
	}
	
	protected boolean hasPassageSouth(){
		return passages[2];
	}
	
	protected boolean hasPassageWest(){
		return passages[3];
	}
	
	public boolean hasFullPassageNorth(){
		int[] pos = board.getPositionOf(this);
		return board.hasFullPassageNorthFrom(pos[0], pos[1]);
	}
	
	public boolean hasFullPassageEast(){
		int[] pos = board.getPositionOf(this);
		return board.hasFullPassageEastFrom(pos[0], pos[1]);
	}
	
	public boolean hasFullPassageSouth(){
		int[] pos = board.getPositionOf(this);
		return board.hasFullPassageSouthFrom(pos[0], pos[1]);
	}
	
	public boolean hasFullPassageWest(){
		int[] pos = board.getPositionOf(this);
		return board.hasFullPassageWestFrom(pos[0], pos[1]);
	}
	
	
	
	public static MapPiece randomizeNew(Board b){
		Random r = new Random();
		switch(r.nextInt(3)){
		case 0: return new MP_Straight(b, r.nextInt(4));
		case 1: return new MP_Turn(b, r.nextInt(4));
		case 2: return new MP_TCross(b, r.nextInt(4));

		}
		return null;
	}
	
	private String directionToString(){
		switch(direction){
		case D0: return "0*";
		case D90: return "90*";
		case D180: return "180*";
		case D270: return "270*";
		default: return "I'm RETARDED :D :D !!";
		}
	}
	
	public String toString(){
		return this.getClass().getSimpleName() + ("(" + directionToString() + ")");
	}

}
