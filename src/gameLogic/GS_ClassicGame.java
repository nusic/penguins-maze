/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;


public class GS_ClassicGame extends GameSettings{
	
	public static String goal = "Be first to eat XX fish";
	public static String description = "Classic Game\n\n" +
			"Fish can swim fast and jump high up in the air.\n" + 
			"However, some fish are unlucky and land on the ice.\n" +
			"\n" +
			"The maze will accommodate only one fish at the time.\n" +
			"When it has been caught, a new fish gets unlucky and \n" +
			"lands somewhere else on the ice.\n" +
			"The first penguin to catch five fish wins.\n";
	
	public GS_ClassicGame(int scoreToReach){
		size = 7;
		this.scoreToReach = scoreToReach;
		maxRoundsPerGame = -1;
		fishPopUpProbability = -1;
		spawnFishWhenNoItemsLeft = true; 
		
		finishWhenNoItemsLeft = false;
		
		initFillBoardWithFish = false;
		initOneFishRandomly = false;
		initOneFishInCenter = true;
	}
	
	public String getGoal(){
		return "Be first to eat " + scoreToReach + " fish";
	}
	
	public String getDescription(){
		return description;
	}
}
