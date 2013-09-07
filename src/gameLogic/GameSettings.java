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

import extra.Tools;

public class GameSettings {
	private GameSession gs;
	private Random r = new Random();
	
	public int size;
	public int players;
	public int bots;
	
	
	public int scoreToReach;
	public int maxRoundsPerGame;
	public boolean finishWhenNoItemsLeft = false;
	public boolean finishWhenAlreadyDecided = false;
	public boolean spawnFishWhenNoItemsLeft = false;
	
	public int fishPopUpProbability;
	
	public boolean initFillBoardWithFish = false;
	public boolean initOneFishRandomly = false;
	public boolean initOneFishInCenter = false;
	
	public String goal = "";
	public String description = "";
	
	
	public String getGoal(){
		return goal;
	}
	
	public String getDescription(){
		return description;
	}
	
	protected void setGameSession(GameSession gs){
		this.gs = gs;
	}
	
	public void initializeGame(){
		for(int i = 0; i<players; i++)
			gs.addPlayer();

		if(initFillBoardWithFish)
			fillBoardWithFish();
		if(initOneFishRandomly)
			spawnFishRandomly();
		if(initOneFishInCenter)
			gs.iv.add(new BO_Fish(gs.board.getMapPieceAt(size/2, size/2)));
	}
	
	public void toDoAfterEachRound(){
		
	}
	
	public void toDoAfterEachTurn(){
		if(gameIsFinished())
			return;
		if(luckyFish())
			spawnFishRandomly();
		if(spawnFishWhenNoItemsLeft && gs.iv.size() == 0)
			spawnFishRandomly();
	}
	
	private void fillBoardWithFish(){
		for(int x = 0; x<gs.getBoardSize(); x++){
			for(int y = 0; y<gs.getBoardSize(); y++){
				if(!penguinAt(x, y))
				gs.iv.add(new BO_Fish(gs.board.getMapPieceAt(x, y)));
			}
		}
	}
	
	private boolean isScoreToReach(){
		return (scoreToReach != -1);
	}
	
	private boolean hasMaxMovesPerRound(){
		return maxRoundsPerGame > 0;
	}
	
	
	public void spawnFishRandomly(){
		int x, y;
		do{
			x = r.nextInt(gs.getBoardSize());
			y = r.nextInt(gs.getBoardSize());
		}while(penguinAt(x, y));
		gs.iv.add(new BO_Fish(gs.board.getMapPieceAt(x,y)));
	}
	
	private boolean penguinAt(int x, int y){
		for(int i = 0; i<gs.numberOfPenguins(); i++){
			if(gs.getPenguin(i).isOnBoard() && gs.getPenguin(i).getX() == x && gs.getPenguin(i).getY() == y)
				return true;
		}
		return false;
	}
	
	public boolean luckyFish(){
		return r.nextInt(100) < fishPopUpProbability;
	}
	
	public boolean gameIsFinished(){
		if(isScoreToReach()){
			for(int i = 0; i<gs.numberOfPenguins(); i++){
				if(gs.getPenguin(i).getScore() >= scoreToReach){
					System.out.println("Score reached");
					return true;
				}
			}
		}
	
		if(hasMaxMovesPerRound()){
			if(gs.getRounds() >= maxRoundsPerGame){
				System.out.println("max moves per round reached");
				return true;
			}
		}
		
		if(finishWhenNoItemsLeft){
			if(gs.numberOfItems() == 0){
				System.out.println("items = 0");
				return true;
			}
		}
		
		if(finishWhenAlreadyDecided){
			System.out.println("Game is already decided");
			return alreadyDecided();
		}
		return false;	
	}
	
	public boolean alreadyDecided(){		
		int[] penguinsScores = new int[gs.numberOfPenguins()];
		for (int i = 0; i < gs.numberOfPenguins(); i++){
			penguinsScores[i] = gs.getPenguin(i).score;
		}
		penguinsScores = Tools.bubbleSort(penguinsScores);
		
		int max = penguinsScores[0];
		int secondMax = penguinsScores[1];
		
		return ((max - secondMax) >= gs.numberOfItems());
	}
}
