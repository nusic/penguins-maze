/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.*;

public class NewGamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public Image bgImg, btnGlow;
	
	public JButton startBtn;
	
	public JLabel numOfPlayersLbl;
	public JRadioButton playersRBtn1, playersRBtn2, playersRBtn3; 
	public JLabel modeLbl;
	public JRadioButton modeRBtn1, modeRBtn2; 
	
	public ButtonGroup btnGrp1, btnGrp2;
	
	public JLabel descriptionLbl;
	public JTextArea modeDescription;
	
	public NewGamePanel(){
		bgImg = new ImageIcon("imgs//Backgrounds//EmptyBackground.jpg").getImage();
		btnGlow = new ImageIcon("imgs//icons//btnGlow.png").getImage();
		
		int size = 576;
		int spaceing = 100;
		int lblLeft = 40;
		
		
		//Game Setup: Labels
		numOfPlayersLbl = new JLabel("Players:");
		numOfPlayersLbl.setFont(new Font("Apple ligothic", Font.PLAIN, 18));
		modeLbl = new JLabel("Game Mode:");
		modeLbl.setFont(new Font("Apple ligothic", Font.PLAIN, 18));		
		descriptionLbl = new JLabel("Description: ");
		descriptionLbl.setFont(new Font("Apple ligothic", Font.PLAIN, 18));

		numOfPlayersLbl.setBounds(lblLeft,50, 200, 50);
		modeLbl.setBounds(lblLeft,150,200,50);
		descriptionLbl.setBounds(lblLeft, 225, 200, 22);

	
		//Game Setup: RadioButtons
		playersRBtn1 = new JRadioButton(new ImageIcon("imgs//icons//2PlayersChecked.png"));
		playersRBtn1.setRolloverIcon(new ImageIcon("imgs//icons//2PlayersHoover.png"));
		playersRBtn1.setSelected(true);		
		
		playersRBtn2 = new JRadioButton(new ImageIcon("imgs//icons//3PlayersChecked.png"));
		playersRBtn2.setRolloverIcon(new ImageIcon("imgs//icons//3PlayersHoover.png"));
		
		playersRBtn3 = new JRadioButton(new ImageIcon("imgs//icons//4PlayersChecked.png"));
		playersRBtn3.setRolloverIcon(new ImageIcon("imgs//icons//4PlayersHoover.png"));
		
		modeRBtn1 = new JRadioButton(new ImageIcon("imgs//icons//normalModeChecked.png"));
		modeRBtn1.setRolloverIcon(new ImageIcon("imgs//icons//normalModeHoover.png"));
		modeRBtn1.setSelected(true);
		
		modeRBtn2 = new JRadioButton(new ImageIcon("imgs//icons//fishFeastChecked.png"));
		modeRBtn2.setRolloverIcon(new ImageIcon("imgs//icons//fishFeastHoover.png"));
			
		modeDescription = new JTextArea();
		modeDescription.setFont(new Font("Apple ligothic", Font.ITALIC, 18));

		
		//Game Setup: group for Buttons
		btnGrp1 = new ButtonGroup();
		btnGrp2 = new ButtonGroup();
		btnGrp1.add(playersRBtn1);
		btnGrp1.add(playersRBtn2);
		btnGrp1.add(playersRBtn3);
		
		btnGrp2.add(modeRBtn1);
		btnGrp2.add(modeRBtn2);
		
		
		//Game Setup: Positioning
		int left = 160;
		playersRBtn1.setBounds(left,50,70,50);
		playersRBtn2.setBounds(left + spaceing,50,70,50);
		playersRBtn3.setBounds(left + spaceing*2,50,70,50);
		
		modeRBtn1.setBounds(left,150,70,50);
		modeRBtn2.setBounds(left + spaceing,150,70,50);
		
		modeDescription.setBounds(left,230,400,200);
		modeDescription.setBackground(Color.WHITE);
		modeDescription.setOpaque(false);
		modeDescription.setEditable(false);
	
	
		//Start Button
		startBtn = new JButton(new ImageIcon("imgs//icons//startGameBtn1.png"));
		startBtn.setRolloverIcon(new ImageIcon("imgs//icons//startGameBtn2.png"));
		startBtn.setBounds(size*1/2 -50, size-100, 150, 40);
		
		
		//Add all to panel
		setLayout(null);
		add(numOfPlayersLbl);
		add(playersRBtn1);
		add(playersRBtn2);
		add(playersRBtn3);
		
		add(modeLbl);	
		add(modeRBtn1);
		add(modeRBtn2);
		
		add(descriptionLbl);
		add(modeDescription);
		
		add(startBtn);
	}
	
	public int getNumberOfPlayersSelected(){
		if(playersRBtn1.isSelected()) 
			return 2;
		if(playersRBtn2.isSelected()) 
			return 3;
		if(playersRBtn3.isSelected()) 
			return 4;
		return -1;
	}
	
	public int getModeSelected(){
		if(modeRBtn1.isSelected())
			return 0;
		if(modeRBtn2.isSelected())
			return 1;
		return -1;
	}
	
	public void updateModeDescription(){
		switch(getModeSelected()){
		case 0: modeDescription.setText(gameLogic.GS_ClassicGame.description); break;
		case 1: modeDescription.setText(gameLogic.GS_FishFeast.description); break;
		}
	}
	
	public void paintTextArea(){
		paintImmediately(modeDescription.getX(), modeDescription.getY(), modeDescription.getWidth(), modeDescription.getHeight());	
	}
	
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.repaint();
		g.drawImage(bgImg, 0, 0, null);
		paintPlayerGlow(g);
		paintModeGlow(g);
		
		updateModeDescription();
	}
	
	public void paintPlayerGlow(Graphics g){
		switch (getNumberOfPlayersSelected()){
		case 2: g.drawImage(btnGlow, playersRBtn1.getX() - 8, playersRBtn1.getY() - 10, null); break;
		case 3: g.drawImage(btnGlow, playersRBtn2.getX() - 8, playersRBtn2.getY() - 10, null); break;
		case 4: g.drawImage(btnGlow, playersRBtn3.getX() - 8, playersRBtn3.getY() - 10, null); break;	
		}
	}
	
	public void paintModeGlow(Graphics g){
		switch (getModeSelected()){
		case 0: g.drawImage(btnGlow, modeRBtn1.getX() - 8, modeRBtn1.getY() - 10, null); break;
		case 1: g.drawImage(btnGlow, modeRBtn2.getX() - 8, modeRBtn2.getY() - 10, null); break;
		}
	}
}
