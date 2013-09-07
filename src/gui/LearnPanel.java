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
import javax.swing.*;

public class LearnPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private int currentLearnPnlIndex;
	public JButton prevBtn, nextBtn;
	
	public Image bgImg1, bgImg2, bgImg3;;

	public LearnPanel(){
		currentLearnPnlIndex = 0;
		int size = 576;
		
		prevBtn = new JButton(new ImageIcon("imgs//icons//previousBtn1.png"));
		prevBtn.setRolloverIcon(new ImageIcon("imgs//icons//previousBtn2.png"));
		prevBtn.setBounds(size/4 -75, size-100, 150, 40);
		
		nextBtn = new JButton(new ImageIcon("imgs//icons//nextBtn1.png"));
		nextBtn.setRolloverIcon(new ImageIcon("imgs//icons//nextBtn2.png"));
		nextBtn.setBounds(size*3/4 -75, size-100, 150, 40);
		
		bgImg1 = new ImageIcon("imgs//Backgrounds//LearnPanelBackground1.jpg").getImage();
		bgImg2 = new ImageIcon("imgs//Backgrounds//LearnPanelBackground2.jpg").getImage();
		bgImg3 = new ImageIcon("imgs//Backgrounds//LearnPanelBackground3.jpg").getImage();

		setLayout(null);
		add(prevBtn);
		add(nextBtn);
		updatePanel();
	}
	
	public void updatePanel(){
		switch (currentLearnPnlIndex){
		case 0:
			prevBtn.setVisible(false);
			nextBtn.setVisible(true);
			break;
		case 1:
			prevBtn.setVisible(true);
			nextBtn.setVisible(true);
			break;
		case 2:
			prevBtn.setVisible(true);
			nextBtn.setVisible(false);
			break;
		}
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		switch (currentLearnPnlIndex){
		case 0:
			g.drawImage(bgImg1, 0, 0, null);
			break;
		case 1:
			g.drawImage(bgImg2, 0, 0, null);
			break;
		case 2:
			g.drawImage(bgImg3, 0, 0, null);
			break;
		}
	}
	
	public void resetPanel(){
		currentLearnPnlIndex = 0;
		updatePanel();
	}
	
	public void nextPanel(){
		currentLearnPnlIndex ++;
		updatePanel();
	}
	public void previousPanel(){
		currentLearnPnlIndex --;
		updatePanel();
	}
}
