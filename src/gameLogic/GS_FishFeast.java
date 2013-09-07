/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;


public class GS_FishFeast extends GameSettings{
	public static String goal = "Eat all the fish!";
	public static String description = "Fish Feast\n\n" +
			"Once in while a pack of killer whales passes the\n" +
			"penguins maze while hunting. Alot of fish \n" + 
			"escape the whales by getting up onto the ice blocks.\n" +
			"\n" +
			"The maze will now be covered with fish.\n" +
			"The penguin who catches most of the fish wins.";
	
	public GS_FishFeast(){
		size = 7;
		scoreToReach = -1;
		maxRoundsPerGame = -1;
		fishPopUpProbability = -1;
		spawnFishWhenNoItemsLeft = false; 
		
		finishWhenAlreadyDecided = true;
		
		initFillBoardWithFish = true;
		initOneFishRandomly = false;
		initOneFishInCenter = false;
	}
	
	public String getGoal(){
		return goal;
	}
	
	public String getDescription(){
		return description;
	}
}
