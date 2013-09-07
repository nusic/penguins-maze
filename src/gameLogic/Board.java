/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gameLogic;


public class Board {

	private MapPiece[][] map;
	
	public Board(int size){
		map = new MapPiece[size][size];
		for(int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				map[i][j] = MapPiece.randomizeNew(this);
			}
		}
		
		for(int i = 2; i<size-1; i += 2){
			map[i][0] = new MP_TCross(this, MapPiece.D0);
			map[i][size-1] = new MP_TCross(this, MapPiece.D180);
		}
		
		for(int i = 2; i<size-1; i+= 2){
			map[0][i] = new MP_TCross(this, MapPiece.D270);
			map[size-1][i] = new MP_TCross(this, MapPiece.D90);
		}
		
		map[0][0] = new MP_Turn(this, MapPiece.D0);
		map[0][size-1] = new MP_Turn(this, MapPiece.D270);
		map[size-1][size-1] = new MP_Turn(this, MapPiece.D180);
		map[size-1][0] = new MP_Turn(this, MapPiece.D90);
		printMpFreqTab();
	}
	

	
	// --- GET --- //
	public MapPiece getMapPieceAt(int x, int y){
		return map[x][y];
	}
	
	public int getSize(){
		return map.length;
	}
	
	public int[] getPositionOf(MapPiece mp){
		for(int x = 0; x<map.length; x++){
			for(int y = 0; y<map.length; y++){
				if(map[x][y] == mp){
					int[] pos = {x, y};
					return pos;
				}
			}
		}
		return null;
	}

	// --- HANDELING MAP PIECES --- //
	public boolean containsMapPiece(MapPiece mp){
		for(int i = 0; i < map.length; i++){
			for (int j = 0; j < map.length; j++){
				if (map[i][j] == mp)
					return true;
			}
		}
		return false;
	}
	
	// --- INSERT --- //
	public static int posIndexToPrepos(int posIndex, int mapSize){
		return posIndex / (mapSize/2);
	}
	
	public static int posIndexToPos(int posIndex, int mapSize){
		return (posIndex % (mapSize/2)) ;
	}
	
	public static int posIndex(int prepos, int pos, int mapSize){
		return prepos*(mapSize/2) + pos;
	}
	
	public MapPiece insert(MapPiece m, int posIndex){
		if(posIndex < 0 || posIndex >= ((map.length)/2)*4){
			System.out.println("ERROR: invalid position!");
			return null;
		}

		int preposition = posIndexToPrepos(posIndex, map.length);
		int position = posIndexToPos(posIndex, map.length);
		int n = 2*position+1;
		
		switch(preposition){
		case 0: return insertFromNorth(m, n);
		case 1: return insertFromEast(m, n);
		case 2: return insertFromSouth(m, n);
		case 3: return insertFromWest(m, n);
		default: return null;
		}
	}
	
	public MapPiece insertFromNorth(MapPiece m, int n){
		m.setBoard(this);
		MapPiece toReturn = map[n][map.length-1];
		for(int i = map.length-1; i>0; i--){
			map[n][i] = map[n][i-1];
		}
		map[n][0] = m;
		toReturn.setBoard(null);
		return toReturn;
	}
	
	public MapPiece insertFromEast(MapPiece m, int n){
		m.setBoard(this);
		MapPiece toReturn = map[0][n];
		for(int i = 0; i<map.length-1; i++){
			map[i][n] = map[i+1][n];
		}
		map[map.length-1][n] = m;
		toReturn.setBoard(null);
		return toReturn;
	}
	
	public MapPiece insertFromSouth(MapPiece m, int n){
		m.setBoard(this);
		MapPiece toReturn = map[map.length-1-n][0];
		for(int i = 0; i<map.length-1; i++){
			map[map.length-1-n][i] = map[map.length-1-n][i+1];
		}
		map[map.length-1-n][map.length-1] = m;
		toReturn.setBoard(null);
		return toReturn;
	}
	
	public MapPiece insertFromWest(MapPiece m, int n){
		m.setBoard(this);
		MapPiece toReturn = map[map.length-1][map.length-1-n];
		for(int i = map.length-1; i>0; i--){
			map[i][map.length-1-n] = map[i-1][map.length-1-n];
		}
		map[0][map.length-1-n] = m;
		toReturn.setBoard(null);
		return toReturn;
	}
	
	
	// --- CHECKING PASSAGES --- //
	public boolean hasFullPassageNorthFrom(int x, int y){
		if(x < 0 || x >= map.length || y < 1 || y >= map.length)
			return false;
		if(map[x][y].hasPassageNorth() && map[x][y-1].hasPassageSouth())
			return true;
		return false;
	}
	
	public boolean hasFullPassageEastFrom(int x, int y){
		if(x < 0 || x >= map.length-1 || y < 0 || y >= map.length)
			return false;
		if(map[x][y].hasPassageEast() && map[x+1][y].hasPassageWest())
			return true;
		return false;
	}
	
	public boolean hasFullPassageSouthFrom(int x, int y){
		if(x < 0 || x >= map.length || y < 0 || y >= map.length-1)
			return false;
		if(map[x][y].hasPassageSouth() && map[x][y+1].hasPassageNorth())
			return true;
		return false;
	}
	
	public boolean hasFullPassageWestFrom(int x, int y){
		if(x < 1 || x >= map.length || y < 0 || y >= map.length)
			return false;
		if(map[x][y].hasPassageWest() && map[x-1][y].hasPassageEast())
			return true;
		return false;
	}
	
	public void printBoard(){
		String l;
		for(int y = 0; y<map.length; y++){
			l = "";
			for(int x = 0; x<map.length; x++){
				l += String.format("%-18s", map[x][y].toString() + " ");
			}
			System.out.println(l);
		}
	}
	
	public void printMpFreqTab(){
		int[] freqTab = new int[4];
		for(int x = 0; x < getSize(); x++){
			for(int y = 0; y< getSize(); y++){
				freqTab[(map[x][y] instanceof MP_Turn) ? 0 : (map[x][y] instanceof MP_Straight) ? 1 : (map[x][y] instanceof MP_TCross) ? 2 : (map[x][y] instanceof MP_Cross) ? 3 : -1]++; 
			}
		}
		System.out.println("---------------");
		System.out.println("    Turns: " + freqTab[0]);
		System.out.println("Straights: " + freqTab[1]);
		System.out.println(" TCrosses: " + freqTab[2]);
		System.out.println("  Crosses: " + freqTab[3]);
		System.out.println("---------------");
	}
}
