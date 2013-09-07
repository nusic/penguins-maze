/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import extra.Tools;

public class Frame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	protected InfoPanel infoPnl;
	protected MenuPanel mPnl;
	public MainPanelHandler mph;
	public Sound sound;
	
	public Frame(int boardSize){
		JPanel eastPnl = new JPanel();
		infoPnl = new InfoPanel();
		mPnl = new MenuPanel();
		mph = new MainPanelHandler(boardSize); 
		
		eastPnl.setLayout(new BorderLayout());
		eastPnl.add(infoPnl, BorderLayout.NORTH);
		eastPnl.add(mPnl, BorderLayout.CENTER);
		
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(mph, BorderLayout.CENTER);
		c.add(eastPnl, BorderLayout.EAST);
		
		//ActionListeners
		mPnl.newGameBtn.addActionListener(this);
		mPnl.rematchBtn.addActionListener(this);
		mPnl.learnBtn.addActionListener(this);
		mPnl.menuBtn.addActionListener(this);
		mPnl.quitGameBtn.addActionListener(this);
		mPnl.exitBtn.addActionListener(this);
		
		mph.newGamePnl.startBtn.addActionListener(this);
		
		mph.learnPnl.nextBtn.addActionListener(this);
		mph.learnPnl.prevBtn.addActionListener(this);
		
		infoPnl.rotateBtn.addActionListener(this);
		infoPnl.soundOnCbx.addActionListener(this);

		
		//Listener 4 sound effect
		MenuBtnChecker menuChecker = new MenuBtnChecker();
		mPnl.newGameBtn.addMouseListener(menuChecker);
		mPnl.rematchBtn.addMouseListener(menuChecker);
		mPnl.learnBtn.addMouseListener(menuChecker);
		mPnl.menuBtn.addMouseListener(menuChecker);
		mPnl.quitGameBtn.addMouseListener(menuChecker);
		mPnl.exitBtn.addMouseListener(menuChecker);

		mph.newGamePnl.startBtn.addMouseListener(menuChecker);
		
		mph.learnPnl.nextBtn.addMouseListener(menuChecker);
		mph.learnPnl.prevBtn.addMouseListener(menuChecker);
		
		infoPnl.rotateBtn.addMouseListener(menuChecker);
		infoPnl.soundOnCbx.addMouseListener(menuChecker);
		
		NewGamePnlBtnChecker ngbc = new NewGamePnlBtnChecker();
		mph.newGamePnl.playersRBtn1.addMouseListener(ngbc);
		mph.newGamePnl.playersRBtn2.addMouseListener(ngbc);
		mph.newGamePnl.playersRBtn3.addMouseListener(ngbc);
		mph.newGamePnl.modeRBtn1.addMouseListener(ngbc);
		mph.newGamePnl.modeRBtn2.addMouseListener(ngbc);
		
		
		int mainSize = (boardSize+2)*GamePanel.IMG_SIDE;
		setSize(mainSize + 200, mainSize + 23);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setTitle("PenGUIns Maze");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		sound = new Sound();
		sound.loop(Sound.WIND);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		mph.repaint();
	}


	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == mPnl.newGameBtn){
			mph.show("newGame");
			mPnl.newGameBtn.setVisible(false);
			mPnl.learnBtn.setVisible(false);
			mPnl.menuBtn.setVisible(true);
			mPnl.quitGameBtn.setVisible(false);
			mPnl.exitBtn.setVisible(false);
		}
		else if(e.getSource() == mPnl.rematchBtn){
			mph.remove(mph.gamePnl);
			initNewGame();
			mPnl.rematchBtn.setVisible(false);
			mPnl.menuBtn.setVisible(false);
			mPnl.quitGameBtn.setVisible(true);
			sound.loop(Sound.ICY_TONES);
		}
		else if(e.getSource() == mPnl.learnBtn){
			mph.learnPnl.resetPanel();
			mph.show("learn");
			mPnl.newGameBtn.setVisible(false);
			mPnl.learnBtn.setVisible(false);
			mPnl.menuBtn.setVisible(true);
			mPnl.quitGameBtn.setVisible(false);
			mPnl.exitBtn.setVisible(false);
		}
		else if(e.getSource() == mPnl.menuBtn){
			dropGamePnlAndShowStart();
			sound.stop(Sound.ICY_TONES);
		}
		else if(e.getSource() == mPnl.quitGameBtn){
			dropGamePnlAndShowStart();
			sound.stop(Sound.ICY_TONES);
		}
		else if(e.getSource() == mPnl.exitBtn){
			sound.stopAll();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(ABORT);
		}
		
		
		else if(e.getSource() == mph.newGamePnl.startBtn){
			initNewGame();
			sound.loop(Sound.ICY_TONES);
		}
		
		
		else if(e.getSource() == mph.learnPnl.nextBtn){
			mph.learnPnl.nextPanel();
		}
		
		else if(e.getSource() == mph.learnPnl.prevBtn){
			mph.learnPnl.previousPanel();
		}
		
		else if(e.getSource() == infoPnl.soundOnCbx){
			boolean soundOn = infoPnl.soundOnCbx.isSelected();
			sound.effects = soundOn;
			sound.music = soundOn;
			if(mph.gamePnl != null)
				mph.gamePnl.gpSound.on = soundOn;
			
			if(soundOn){
				sound.loop(Sound.WIND);
				if(mph.gamePnl != null)
					sound.loop(Sound.ICY_TONES);
			}
			else{
				sound.stopAll();
			}
		}
		
		
		
		else if(mph.gamePnl != null){
			if(e.getSource() == infoPnl.rotateBtn){
				mph.gamePnl.gs.rotateLooseMapPiece();
			}
			else for(int i = 0; i < mph.gamePnl.insBtns.length; i++){
				if (e.getSource() == mph.gamePnl.insBtns[i]){
					sound.play(Sound.ICY_WATER_WHIRL);
					mph.gamePnl.insertLooseMapPiece(i);
						
					break;
				}
			}
			updateInfoImage();
			
			if(mph.gamePnl.gs.getLooseBoardObject() != null)
				mph.gamePnl.setInsBtnsRolloverIcon(new ImageIcon(Tools.mergeImages(mph.gamePnl.gs.getLooseMapPiece().getImage(),
				mph.gamePnl.gs.getLooseBoardObject().getImage())));
			else
			mph.gamePnl.setInsBtnsRolloverIcon(new ImageIcon(mph.gamePnl.gs.getLooseMapPiece().getImage()));
			repaint();
		}
		
		updateInfo();
	}
	
	// --- INITIALIZE NEW GAME --- //
	private void initNewGame(){
		mph.gamePnl = new GamePanel(mph.newGamePnl.getNumberOfPlayersSelected(), mph.newGamePnl.getModeSelected());
		mph.gamePnl.gpSound.on = infoPnl.soundOnCbx.isSelected();
		infoPnl.setGameOn(true);
		infoPnl.goal = mph.gamePnl.gs.settings.getGoal();
		mph.add(mph.gamePnl, "board");	
		mPnl.menuBtn.setVisible(false);

		
		InsBtnChecker insBtnChecker = new InsBtnChecker();
		for (int i = 0; i < mph.gamePnl.insBtns.length; i++){
			mph.gamePnl.insBtns[i].addActionListener(this);
			mph.gamePnl.insBtns[i].addMouseListener(insBtnChecker);
		}
		
		MpBtnChecker mpBtnChecker = new MpBtnChecker();
		for(int i = 0; i<mph.gamePnl.mpBtn.length; i++){
			for(int j = 0; j<mph.gamePnl.mpBtn[0].length; j++){
				mph.gamePnl.mpBtn[i][j].addActionListener(mpBtnChecker);
			}
		}
		
		mph.show("board");
		updateInfoImage();
		updateInfo();
		infoPnl.rotateBtn.setVisible(true);
		mPnl.quitGameBtn.setVisible(true);
		repaint();
	}

	
	private void updateInfo(){
		if(mph.gamePnl == null)
			return;
		for(int i = 0; i<mph.gamePnl.gs.numberOfPenguins(); i++)
			infoPnl.score[i] = mph.gamePnl.gs.getPenguin(i).getScore();
		
		infoPnl.penguinIndex = mph.gamePnl.gs.getPlayingPenguinIndex();
		infoPnl.penguins = mph.gamePnl.gs.numberOfPenguins();
		infoPnl.timeToWalk = mph.gamePnl.gs.isTimeToWalk();
		infoPnl.rotateBtn.setEnabled(!infoPnl.timeToWalk);
	}
	
	private void updateInfoImage(){
				
		if(mph.gamePnl.gs.getLooseBoardObject() != null){
			infoPnl.setMpImg(Tools.mergeImages(mph.gamePnl.gs.getLooseMapPiece().getBigImage(), mph.gamePnl.gs.getLooseBoardObject().getBigImage()));
		}
		else
			infoPnl.setMpImg(mph.gamePnl.gs.getLooseMapPiece().getBigImage());
	}
	
	private void dropGamePnlAndShowStart(){
		mph.show("start");
		mph.gamePnl = null;
		infoPnl.setGameOn(false);
		mPnl.newGameBtn.setVisible(true);
		mPnl.rematchBtn.setVisible(false);
		mPnl.learnBtn.setVisible(true);
		mPnl.menuBtn.setVisible(false);
		mPnl.quitGameBtn.setVisible(false);
		mPnl.exitBtn.setVisible(true);
		repaint();
	}
	
	private class MenuBtnChecker implements MouseListener{

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {
			if(e.getSource() == infoPnl.rotateBtn && !infoPnl.rotateBtn.isEnabled())
				return;
			sound.play(Sound.ICY_KLINK);
		}

		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {
			if(e.getSource() == infoPnl.rotateBtn)
				sound.play(Sound.SWISH);
			else
				sound.play(Sound.ICY_CRASH);
		}
		
	}

	
	private class InsBtnChecker implements MouseListener{

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {
			for(int i = 0; i<mph.gamePnl.insBtns.length; i++){
				if(e.getSource() == mph.gamePnl.insBtns[i] && mph.gamePnl.gs.cantInsertAt() == i){
					return;
				}
			}
			sound.play(Sound.FAST_SWISH);
			infoPnl.setMpImg(null);
			repaint();
		}
		public void mouseExited(MouseEvent e) {
			for(int i = 0; i<mph.gamePnl.insBtns.length; i++){
				if(e.getSource() == mph.gamePnl.insBtns[i] && mph.gamePnl.gs.cantInsertAt() == i){
					return;
				}
			}
			if(!mph.gamePnl.gs.isTimeToWalk())
				sound.play(Sound.FAST_SWOSH);
			updateInfoImage();
			repaint();
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	private class MpBtnChecker implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(!mph.gamePnl.gs.isTimeToWalk())
				return;

			for(int x = 0; x<mph.gamePnl.mpBtn.length; x++){
				for(int y = 0; y<mph.gamePnl.mpBtn[0].length; y++){
					if(e.getSource() == mph.gamePnl.mpBtn[x][y]){
						mph.gamePnl.walkTo(x, y);
						if(!mph.gamePnl.gs.isTimeToWalk()){
							mph.gamePnl.setInsBtnsVisible(true);
							mph.gamePnl.disableOnlyInsBtn(mph.gamePnl.gs.cantInsertAt());
						}
						if(mph.gamePnl.gs.isFinished()){
							sound.stop(Sound.ICY_TONES);
							sound.play(Sound.WINNING_YIPPIE_ROCK);
							
							mPnl.rematchBtn.setVisible(true);
							mPnl.quitGameBtn.setVisible(false);
							mPnl.menuBtn.setVisible(true);
						}
						updateInfo();
						repaint();
						if(mph.gamePnl.gs.isFinished()){
							
						}
						return;
					}
				}
			}
		}
	}
	
	private class NewGamePnlBtnChecker implements MouseListener{
		private int lastSelectedPlayers = 1;
		private int lastSelectedMode = 0;

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {
			sound.play(Sound.ICY_KLINK);
			if(e.getSource() == mph.newGamePnl.modeRBtn1)
				mph.newGamePnl.modeDescription.setText(gameLogic.GS_ClassicGame.description);
			else if(e.getSource() == mph.newGamePnl.modeRBtn2)
				mph.newGamePnl.modeDescription.setText(gameLogic.GS_FishFeast.description);
			mph.newGamePnl.paintTextArea();
		}
		public void mouseExited(MouseEvent e) {
			mph.newGamePnl.updateModeDescription();
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {
			if(lastSelectedPlayers != mph.newGamePnl.getNumberOfPlayersSelected()){
				lastSelectedPlayers = mph.newGamePnl.getNumberOfPlayersSelected();
				sound.play(Sound.ACTIVATE);
			}
			
			if(lastSelectedMode != mph.newGamePnl.getModeSelected()){
				lastSelectedMode = mph.newGamePnl.getModeSelected();
				sound.play(Sound.ACTIVATE);

			}	
		}
	}
}
