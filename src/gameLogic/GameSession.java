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

public class GameSession {
	protected Board board;
	protected Vector<BO_Penguin> pv;
	protected Vector<BO_Item> iv;
	public GameSettings settings;
	private MapPiece looseMapPiece;
	private BO_BoardObject looseBoardObject;

	private int penguinIndex;
	private boolean timeToWalk;
	private int cantInsertAt = -1;
	private int rounds;
	private int gameNr;
	private boolean gameFinished = false;
	
	public GameSession(GameSettings gSettings){
		settings = gSettings;
		settings.setGameSession(this);
		
		board = new Board(gSettings.size);
		pv = new Vector<BO_Penguin>();
		iv = new Vector<BO_Item>();
		looseMapPiece = MapPiece.randomizeNew(null);
		looseBoardObject = null;
		settings.initializeGame();
	}
	
	//	--- BOARD --- //
	public int getBoardSize(){
		return board.getSize();
	}
	
	public Image getMapPieceImageAt(int x, int y){
		return board.getMapPieceAt(x, y).getImage();
	}
	
	public int indexToPrepos(int posIndex){
		return Board.posIndexToPrepos(posIndex, getBoardSize());
	}
	
	public int posIndex(int prepos, int pos){
		return Board.posIndex(prepos, pos, getBoardSize());
	}
	
	public int indexToPos(int posIndex){
		return Board.posIndexToPos(posIndex, getBoardSize());
	}
	
	// --- PENGUINS --- //
	public int numberOfPenguins(){
		return pv.size();
	}
	
	public BO_Penguin getPlayingPenguin(){
		return pv.elementAt(penguinIndex);
	}
	
	public BO_Penguin getPenguin(int index){
		return pv.elementAt(index);
	}
	
	public BO_Penguin getPenguinOnLooseMapPiece(){
		for(int i = 0; i<pv.size(); i++){
			if(!pv.elementAt(i).isOnBoard())
				return pv.elementAt(i);
		}
		return null;
	}
	
	public int getPlayingPenguinIndex(){
		return penguinIndex;
	}
	
	public int getWinningPenguinIndex(){
		int index = 0;
		for(int i = 1; i<pv.size(); i++){
			if(getPenguin(i).score > getPenguin(index).score)
				index = i;
		}
		return index;
	}
	

	// --- ITEMS --- //
	public int numberOfItems(){
		return iv.size();
	}
	
	public BO_Item getItem(int index){
		return iv.elementAt(index);
	}
	
	public BO_Item getItemOnLooseMapPiece(){
		for(int i = 0; i<iv.size(); i++){
			if(!iv.elementAt(i).isOnBoard())
				return iv.elementAt(i);
		}
		return null;
	}
	
	public MapPiece getLooseMapPiece(){
		return looseMapPiece;
	}
	
	public BO_BoardObject getLooseBoardObject(){
		return looseBoardObject;
	}
	
	public String getColorString(int color){
		switch(color%4){
		case 0: return "blue";
		case 1: return "red";
		case 2: return "green";
		case 3: return "yellow";
		default: return "error";
		}
	}
	
	public int getRounds(){
		return rounds;
	}
	
	public int getGameNr(){
		return gameNr;
	}
	
	public boolean isTimeToWalk(){
		return timeToWalk;
	}
	
	public boolean isFinished(){
		return gameFinished;
	}
	
	public boolean testIfFinished(){
		gameFinished = settings.gameIsFinished();
		return gameFinished;
	}
	
	public int cantInsertAt(){
		return cantInsertAt;
	}
	
	protected void addPlayer(){
		switch(numberOfPenguins()){
		case 0: pv.add(new BO_Player(board.getMapPieceAt(0, getBoardSize()-1), getColorString(0))); break;
		case 1: pv.add(new BO_Player(board.getMapPieceAt(getBoardSize()-1, 0), getColorString(1))); break;
		case 2: pv.add(new BO_Player(board.getMapPieceAt(0, 0), getColorString(2))); break;
		case 3: pv.add(new BO_Player(board.getMapPieceAt(getBoardSize()-1, getBoardSize()-1), getColorString(3))); break;
		}
	}
	
	public void insertLooseMapPiece(int posIndex){
		if(timeToWalk || posIndex == cantInsertAt)
			return;
		looseMapPiece = board.insert(looseMapPiece, posIndex);
		if(getPlayingPenguin().getMapPiece().getBoard() == null){
			finishTurn();
		}
		
		else 
			timeToWalk = true;
		
		looseBoardObject = null;
		for(int i=0; i<pv.size(); i++){
			if(looseMapPiece == pv.elementAt(i).getMapPiece()){
				looseBoardObject = pv.elementAt(i);
			}
		}
		for(int i=0; i<iv.size(); i++){
			if(looseMapPiece == iv.elementAt(i).getMapPiece()){
				looseBoardObject = iv.elementAt(i);
			}
		}
		
		updateCantInsert(posIndex);
	}
	
	public void rotateLooseMapPiece(){
		looseMapPiece.turnRight();
		if(looseBoardObject != null){
			looseBoardObject.turnRight();
		}
	}
	
	private void updateCantInsert(int posIndex){
		int prepos = indexToPrepos(posIndex);
		int pos = indexToPos(posIndex);

		int oppPrepos = (prepos+2)%4;
		int oppPos = getBoardSize()/2-1-pos;

		cantInsertAt = posIndex(oppPrepos, oppPos);
	}
		
	private void pickUpItem(){
		BO_Item item = getItemAt(getPlayingPenguin().getMapPiece());
		if(item == null)
			return;
		else {
			getPlayingPenguin().score += 1;
			iv.remove(item);
		}
	}
	
	public void walkTo(int x, int y){
		getPlayingPenguin().walkTo(x, y);
		pickUpItem();
	}
	
	private BO_Item getItemAt(MapPiece mp){
		for(int i = 0; i<iv.size(); i++){
			if(iv.elementAt(i).getMapPiece() == mp)
				return iv.elementAt(i);
		}
		return null;
	}
	
	public void finishTurn(){
		if(testIfFinished())
			return;
		timeToWalk = false;
		penguinIndex++;
		penguinIndex %= pv.size();
		settings.toDoAfterEachTurn();
		if(penguinIndex == 0){
			rounds++;
			settings.toDoAfterEachRound();
		}
	}
	
	public void reset(){
		rounds = 0;
	}
}
