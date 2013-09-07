/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gui;

import gameLogic.GS_ClassicGame;
import gameLogic.GS_FishFeast;
import gameLogic.GameSession;
import gameLogic.GameSettings;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static final int IMG_SIDE = 64;
	public GameSettings settings;
	public GameSession gs;
	public GPSound gpSound;
	public JButton[] insBtns;
	public JButton[][] mpBtn;
	public Image bg;
	public Image[] winningScreens;
	
	private int dx = 0, dy = 0;
	private int pdx = 0, pdy = 0;
	private int posIndex = -1;
	public Color[] color = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
	
	
	public GamePanel(int players, int mode){
		if(mode == 0)
			settings = new GS_ClassicGame(5);
		else if(mode == 1)
			settings = new GS_FishFeast();
		else
			System.out.println("GamePanel error: mode = " + mode);
		settings.players = players;
		gs = new GameSession(settings);
		
		setLayout(null);
		bg = new ImageIcon("imgs//Backgrounds//GamePanelBackground.jpg").getImage();
		
		winningScreens = new Image[4];
		
		for (int i=0; i<4; i++){
			winningScreens[i] = new ImageIcon("imgs//Backgrounds//WinningScreen" + i + ".png").getImage();
		}
		
		insBtns = new JButton[4 * (settings.size/2)];
		for(int i = 0; i<insBtns.length; i++){
			insBtns[i] = new JButton();
			insBtns[i].setRolloverEnabled(true);
			insBtns[i].setOpaque(false);
			insBtns[i].setContentAreaFilled(false);
			insBtns[i].setBorderPainted(false);
			insBtns[i].setRolloverIcon(new ImageIcon(gs.getLooseMapPiece().getImage()));
			insBtns[i].setToolTipText("Click to insert ice block");
			addInsertBtn(insBtns[i], i);
		}

		mpBtn = new JButton[settings.size][settings.size];
		for(int i = 0; i<mpBtn.length; i++){
			for(int j = 0; j<mpBtn[0].length; j++){
				mpBtn[i][j] = new JButton();
				mpBtn[i][j].setRolloverEnabled(true);
				mpBtn[i][j].setOpaque(false);
				mpBtn[i][j].setContentAreaFilled(false);
				mpBtn[i][j].setBorderPainted(false);
				mpBtn[i][j].setBounds((i+1)*IMG_SIDE, (j+1)*IMG_SIDE, IMG_SIDE, IMG_SIDE);
				mpBtn[i][j].setIcon(new ImageIcon("imgs//icons//empty.png"));
				mpBtn[i][j].setRolloverIcon(new ImageIcon("imgs//icons//feet2.png"));
				mpBtn[i][j].setToolTipText("Click to walk here");
				add(mpBtn[i][j]);
			}
		}
		setMpBtnsVisible(false);
		gpSound = new GPSound();
	}

	
	public void walkTo(int x, int y){
		if(!gs.isTimeToWalk())
			return;

		if(gs.getPlayingPenguin().getBoard() == null){
			gs.finishTurn();
			setMpBtnsVisible(false);
			return;
		}
		Vector<int[]> path = gs.getPlayingPenguin().getPathTo(x, y);
		
		setMpBtnsVisible(false);

		for(int i = 1; i<path.size(); i++){
			int oldScore = gs.getPlayingPenguin().getScore();
			animateWalkTo(path.elementAt(i)[0], path.elementAt(i)[1]);
			gs.walkTo(path.elementAt(i)[0], path.elementAt(i)[1]);
			
			if(oldScore != gs.getPlayingPenguin().getScore())
				gpSound.play(GPSound.EAT);
		}
		
		paintImmediately(0, 0, getWidth(), getHeight());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gs.finishTurn();
	}
	
	public void insertLooseMapPiece(int posIndex){
		animateInsert(posIndex);
		gs.insertLooseMapPiece(posIndex);
		if(gs.isTimeToWalk()){
			setInsBtnsVisible(false);
			showOnlyMpBtns(gs.getPlayingPenguin().calcReachable());
		}
		disableOnlyInsBtn(gs.cantInsertAt());
	}
	
	
	
	
	// --- INSERT BUTTONS ---//
	
	private void addInsertBtn(JButton btn, int posIndex){
		int preposition = gs.indexToPrepos(posIndex);
		int position = gs.indexToPos(posIndex);
		int n = 2*position+1;
		switch(preposition){
		case 0: customizeInsBtnNorth(btn, n); break;
		case 1: customizeInsBtnEast(btn, n); break;
		case 2: customizeInsBtnSouth(btn, n); break;
		case 3: customizeInsBtnWest(btn, n); break;
		}
		add(btn);
	}
	
	private void customizeInsBtnNorth(JButton btn, int i){
		btn.setIcon(new ImageIcon("imgs//icons//insBtn0.png"));
		btn.setBounds(IMG_SIDE*(i+1), 0, IMG_SIDE, IMG_SIDE);
	}
	
	private void customizeInsBtnEast(JButton btn, int i){
		btn.setIcon(new ImageIcon("imgs//icons//insBtn1.png"));
		btn.setBounds(IMG_SIDE*(gs.getBoardSize()+1), IMG_SIDE*(i+1), IMG_SIDE, IMG_SIDE);
	}
	
	private void customizeInsBtnSouth(JButton btn, int i){
		btn.setIcon(new ImageIcon("imgs//icons//insBtn2.png"));
		btn.setBounds(IMG_SIDE*(gs.getBoardSize()) - IMG_SIDE*(i), IMG_SIDE*(gs.getBoardSize()+1), IMG_SIDE, IMG_SIDE);
	}
	
	private void customizeInsBtnWest(JButton btn, int i){
		btn.setIcon(new ImageIcon("imgs//icons//insBtn3.png"));
		btn.setBounds(0, IMG_SIDE*(gs.getBoardSize()) - IMG_SIDE*(i), IMG_SIDE, IMG_SIDE);
	}
	
	public void setInsBtnsRolloverIcon(ImageIcon icon){
		for(int i = 0; i<insBtns.length; i++){
			insBtns[i].setRolloverIcon(icon);
			insBtns[i].setPressedIcon(icon);
		}
	}
	
	public void setInsBtnsVisible(boolean visible){
		for(int i = 0; i<insBtns.length; i++){
			insBtns[i].setVisible(visible);
		}
	}
	
	
	public void disableOnlyInsBtn(int index){
		for(int i = 0; i<insBtns.length; i++){
			insBtns[i].setEnabled(i != index);
			insBtns[i].setToolTipText((i != index) ? "" : "Cannot push back!");
		}
	}

	
	
	
	// --- MapPiece Buttons --- //
	public void setMpBtnsVisible(boolean visible){
		for(int x = 0; x<mpBtn.length; x++){
			for(int y = 0; y<mpBtn[0].length; y++){
				mpBtn[x][y].setVisible(visible);
			}
		}
	}
	
	public void showOnlyMpBtns(Vector<int[]> v){
		for(int x = 0; x<mpBtn.length; x++){
			for(int y = 0; y<mpBtn[0].length; y++){
				mpBtn[x][y].setVisible(false);
				for(int i = 0; i<v.size(); i++){
					if(x == v.elementAt(i)[0] && y == v.elementAt(i)[1]){
						mpBtn[x][y].setVisible(true);
					}
				}
			}
		}
	}
	
	
	
	// --- PAINT METHODS --- //
	public void animateWalkTo(int x, int y){
		int pdxAdd = x-gs.getPlayingPenguin().getX();
		int pdyAdd = y-gs.getPlayingPenguin().getY();
		int dir = (pdyAdd == -1) ? 0 : (pdxAdd == 1) ? 1 : (pdyAdd == 1) ? 2 : (pdxAdd == -1) ? 3 : -1;
		gs.getPlayingPenguin().setDirection(dir);
		
		int speed = 4;
		int fotstepFrequency = 2;
		while(Math.abs(pdx) < IMG_SIDE && Math.abs(pdy) < IMG_SIDE){
			pdx += pdxAdd*speed;
			pdy += pdyAdd*speed;
			
			if(((pdyAdd == 0) && (Math.abs(pdx%(IMG_SIDE/fotstepFrequency)) <= speed)) || ((pdxAdd == 0) && (Math.abs((pdy)%(IMG_SIDE/fotstepFrequency)) <= speed)))
				gpSound.play(GPSound.FOOTSTEP);
			
			paintImmediately(gs.getPlayingPenguin().getX()*IMG_SIDE, gs.getPlayingPenguin().getY()*IMG_SIDE, 3*IMG_SIDE, 3*IMG_SIDE);
			sleep(20);
		}
		pdx = 0;
		pdy = 0;
	}
	
	public void animateInsert(int posIndex){
		this.posIndex = posIndex;
		int prepos = gs.indexToPrepos(posIndex);
		int pos = gs.indexToPos(posIndex);
		int n = 2*pos+1;
		
		int x = 0, y = 0, width = 0, height = 0;
		
		switch(prepos){
		case 0: 
			x = (n + 1)*IMG_SIDE;
			y = 0;
			width = IMG_SIDE;
			height = (gs.getBoardSize()+2)*IMG_SIDE;
			break;
		case 1: 
			x = 0;
			y = (n + 1)*IMG_SIDE;
			width = (gs.getBoardSize()+2)*IMG_SIDE;
			height = IMG_SIDE;
			break;
		case 2:
			x = (gs.getBoardSize()-n)*IMG_SIDE;
			y = 0;
			width = IMG_SIDE;
			height = (gs.getBoardSize()+2)*IMG_SIDE;
			break;
		case 3: 
			x = 0;
			y = (gs.getBoardSize()-n)*IMG_SIDE;
			width = (gs.getBoardSize()+2)*IMG_SIDE;
			height = IMG_SIDE;
		}
		setInsBtnsVisible(false);
		paintImmediately(0, 0, getWidth(), getHeight());
		int speed = 3;
		while(Math.abs(dx) < IMG_SIDE && Math.abs(dy) < IMG_SIDE){
			switch(prepos){
				case 0: dy += speed; break;
				case 1: dx -= speed; break;
				case 2: dy -= speed; break;
				case 3: dx += speed; break;
			}
			paintImmediately(x, y, width, height);
			sleep(20);
		}
		setInsBtnsVisible(true);
		dx = 0;
		dy = 0;
		this.posIndex = -1;
	}
	
	
	
	public void paintComponent(Graphics g){
		g.drawImage(bg, 0, 0, null);
	
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		paintPlayingPenguinColorBehindInsBtns(g2, 30, 0.5f);
		paintBoard(g2, 0.65f);
		paintItems(g2, 0.80f);
		paintPenguins(g2, 0.3f);
		
		if(gs.isTimeToWalk()){
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			if(!gs.getPlayingPenguin().isOnBoard())
				return;
			Vector<int[]> v = gs.getPlayingPenguin().calcReachable();
						
			paintSpecificMapPieces(g, v);
			paintSpecificItems(g, v);
			paintSpecificPenguins(g, v);
		}
		
		if(gs.isFinished()){
			paintWinningPenguinScreen(g2);
		}
	}
	
	public void paintPlayingPenguinColorBehindInsBtns(Graphics2D g2, int size, float alpha){
		if(posIndex != -1 || gs.isTimeToWalk())
			return;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.WHITE);
		for(int i = 0; i<insBtns.length; i++){
			if(i != gs.cantInsertAt())
				g2.fillOval(insBtns[i].getX()+insBtns[i].getWidth()/2-size/2, insBtns[i].getY()+insBtns[i].getHeight()/2-size/2, size, size);
		}
		g2.setColor(color[gs.getPlayingPenguinIndex()]);
		for(int i = 0; i<insBtns.length; i++){
			if(i != gs.cantInsertAt())
				g2.fillOval(insBtns[i].getX()+insBtns[i].getWidth()/2-size/2, insBtns[i].getY()+insBtns[i].getHeight()/2-size/2, size, size);
		}
	}



	public void paintBoard(Graphics2D g2, float alpha){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		for(int y = 0; y<gs.getBoardSize(); y++){
			for(int x = 0; x<gs.getBoardSize(); x++){
				g2.drawImage(gs.getMapPieceImageAt(x, y), (x + 1)*IMG_SIDE + dx, (y + 1)*IMG_SIDE + dy, null);
			}
		}
		if(posIndex >= 0 && posIndex < insBtns.length)
			g2.drawImage(gs.getLooseMapPiece().getImage(), insBtns[posIndex].getX() + dx, insBtns[posIndex].getY() + dy, null);
	}
	
	public void paintItems(Graphics2D g2, float alpha){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		for(int i = 0; i < gs.numberOfItems(); i++){
			if(gs.getItem(i).isOnBoard()){
				g2.drawImage(gs.getItem(i).getImage(), (gs.getItem(i).getX() + 1)*IMG_SIDE  + dx, (gs.getItem(i).getY() + 1)*IMG_SIDE + dy, null);
			}
		}
		if(posIndex >= 0 && posIndex < insBtns.length && gs.getItemOnLooseMapPiece() != null)
			g2.drawImage(gs.getItemOnLooseMapPiece().getImage(), insBtns[posIndex].getX() +dx, insBtns[posIndex].getY() + dy, null);
	}
	
	public void paintPenguins(Graphics2D g2, float alpha){
		for(int i = 0; i < gs.numberOfPenguins(); i++){
			if(gs.getPenguin(i).isOnBoard()){
				if(i != gs.getPlayingPenguinIndex()){
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
					g2.drawImage(gs.getPenguin(i).getImage(), (gs.getPenguin(i).getX() + 1)*IMG_SIDE + dx, (gs.getPenguin(i).getY() + 1)*IMG_SIDE + dy, null);
				}
				else if(pdx == 0 && pdy == 0){
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
					g2.drawImage(gs.getPenguin(i).getImage(), (gs.getPenguin(i).getX() + 1)*IMG_SIDE + dx, (gs.getPenguin(i).getY() + 1)*IMG_SIDE + dy, null);
				}
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			}
		}
		if(posIndex >= 0 && posIndex < insBtns.length && gs.getPenguinOnLooseMapPiece() != null){
			g2.drawImage(gs.getPenguinOnLooseMapPiece().getImage(), insBtns[posIndex].getX() +dx, insBtns[posIndex].getY() + dy, null);
		}
	}
	
	public void paintSpecificMapPieces(Graphics g, Vector<int[]> v){
		for(int i = 0; i<v.size(); i++){
			int x = v.elementAt(i)[0];
			int y = v.elementAt(i)[1];
			g.drawImage(gs.getMapPieceImageAt(x, y), (x + 1)*IMG_SIDE  + dx, (y + 1)*IMG_SIDE + dy, null);
		}
	}
	
	public void paintSpecificGoHere(Graphics g, Vector<int[]> v) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g.setColor(Color.YELLOW);
		int cs = IMG_SIDE/4;
		for(int i = 0; i<v.size(); i++){
			g.fillOval((v.elementAt(i)[0]+1)*IMG_SIDE+(IMG_SIDE-cs)/2, (v.elementAt(i)[1]+1)*IMG_SIDE+(IMG_SIDE-cs)/2, cs, cs);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}
	
	public void paintSpecificItems(Graphics g, Vector<int[]> v){
		for(int i = 0; i<gs.numberOfItems(); i++){
			for(int j = 0; j<v.size(); j++){
				if(gs.getItem(i).isOnBoard() && gs.getItem(i).getX() == v.elementAt(j)[0] && gs.getItem(i).getY() == v.elementAt(j)[1])
					g.drawImage(gs.getItem(i).getImage(), (gs.getItem(i).getX() + 1)*IMG_SIDE + dx, (gs.getItem(i).getY() + 1)*IMG_SIDE + dy, null);
			}				
		}
	}
	
	public void paintSpecificPenguins(Graphics g, Vector<int[]> v){
		Graphics2D g2 = (Graphics2D) g;
		for(int i = 0; i<gs.numberOfPenguins(); i++){
			for(int j = 0; j<v.size(); j++){
				if(gs.getPenguin(i).isOnBoard() && gs.getPenguin(i).getX() == v.elementAt(j)[0] && gs.getPenguin(i).getY() == v.elementAt(j)[1]){
					if(i != gs.getPlayingPenguinIndex()){
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
						g.drawImage(gs.getPenguin(i).getImage(), (gs.getPenguin(i).getX() + 1)*IMG_SIDE + dx, (gs.getPenguin(i).getY() + 1)*IMG_SIDE + dy, null);
					}
					else if(pdy != 0 || pdx != 0)
						g.drawImage(gs.getPenguin(i).getImage((int) ((System.currentTimeMillis()/100) % 4)), (gs.getPenguin(i).getX() + 1)*IMG_SIDE + dx + pdx, (gs.getPenguin(i).getY() + 1)*IMG_SIDE + dy + pdy, null);
					else
						g.drawImage(gs.getPenguin(i).getImage(), (gs.getPenguin(i).getX() + 1)*IMG_SIDE + dx + pdx, (gs.getPenguin(i).getY() + 1)*IMG_SIDE + dy + pdy, null);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				}
			}				
		}
	}
	
	
	public void paintWinningPenguinScreen(Graphics2D g2){
		g2.drawImage(winningScreens[gs.getWinningPenguinIndex()],
					getWidth()/2 - winningScreens[gs.getWinningPenguinIndex()].getWidth(null)/2,
					getHeight()/2 - winningScreens[gs.getWinningPenguinIndex()].getHeight(null)/2,
					null);
	}
	
	private void sleep(int timeMillis){
		try {
			Thread.sleep(timeMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
