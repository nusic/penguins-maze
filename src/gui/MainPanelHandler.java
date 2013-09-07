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
import javax.swing.JPanel;

public class MainPanelHandler extends JPanel {
	private static final long serialVersionUID = 1L;
	public StartPanel startPnl;
	public NewGamePanel newGamePnl;
	public LearnPanel learnPnl;
	public GamePanel gamePnl;

	public String currentPnl, previousPnl;
	public String previousPanel;

	public MainPanelHandler(int size) {
		startPnl = new StartPanel();
		newGamePnl = new NewGamePanel();
		learnPnl = new LearnPanel();
		
		currentPnl = "start";

		setLayout(new CardLayout());
		add(startPnl, "start");
		add(newGamePnl, "newGame");
		add(learnPnl, "learn");
	}

	public void show(String panel) {
		previousPnl = currentPnl;
		currentPnl = panel;
		
		CardLayout cl = (CardLayout) (getLayout());
		cl.show(this, currentPnl);
	}

	public void showPrevious() {
		currentPnl = previousPnl;
		CardLayout cl = (CardLayout) (getLayout());
		cl.show(this, currentPnl);
	}
}
