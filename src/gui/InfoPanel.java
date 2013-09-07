/**
 * Linkšpings Universitet
 * Medieteknik Œr 2
 * Projektarbete i TNM040 - Kommunikation och AnvŠndargrŠnssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gui;

import java.awt.*;

import javax.swing.*;

public class InfoPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Image mpImg;
	public Image bg;
	public JButton rotateBtn;
	public JCheckBox soundOnCbx;
	public Color[] color = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
	public int penguinIndex = -1;
	public int penguins = 0;
	public int[] score;
	public boolean timeToWalk;
	public String goal = "";
	private boolean gameOn = false;
	
	
	
	public InfoPanel(){
		setPreferredSize(new Dimension(200, 330));
		setLayout(null);
		
		rotateBtn = new JButton(new ImageIcon("imgs//icons//rotateBtn1.png"));
		rotateBtn.setRolloverIcon(new ImageIcon("imgs//icons//rotateBtn2.png"));
		int btnWidth = 40;
		int btnHeight = 40;
		rotateBtn.setBounds(100-btnWidth/2, 230-btnHeight, btnWidth, btnHeight);
		rotateBtn.setVisible(false);
		rotateBtn.setToolTipText("Click to rotate ice block");
		add(rotateBtn);
		
		soundOnCbx = new JCheckBox();
		soundOnCbx.setSelected(true);
		soundOnCbx.setBounds(169, 0, 27, 27);
		soundOnCbx.setIcon(new ImageIcon("imgs//icons//sound_off.png"));
		soundOnCbx.setSelectedIcon(new ImageIcon("imgs//icons//sound_on.png"));
		soundOnCbx.setRolloverIcon(new ImageIcon("imgs//icons//sound_off_rollover.png"));
		soundOnCbx.setRolloverSelectedIcon((new ImageIcon("imgs//icons//sound_on_rollover.png")));
		add(soundOnCbx);

		score = new int[4];
		bg = (new ImageIcon("imgs//Backgrounds//InfoPanelBackground.jpg")).getImage();
		
	}
	
	public Image GetMpImg(){
		return mpImg;
	}
	
	public void setMpImg(Image img){
		mpImg = img;
		repaint();
	}
	
	public void setGameOn(boolean gameOn){
		this.gameOn = gameOn;
		rotateBtn.setVisible(gameOn);
	}
	
	public void setBoImg(Image img){
		repaint();
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(bg, 0, 0, null);
		if(!gameOn)
			return;
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		
		int tabSize = 160;
		
		g2.setColor(Color.BLACK);
		
		if(penguinIndex >= 0){
			g2.setColor(color[penguinIndex]);
			g2.fillRoundRect(100-tabSize/2, 100-tabSize/2, tabSize, 250-100+tabSize/2, 40, 40);
			
			g2.setFont(new Font("Apple ligothic", Font.PLAIN, 25));
			
			for(int i = 0; i<penguins; i++){
				g2.setColor(color[i]);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
				g2.fillRect(40+30*i, 250, 30, 30);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g2.setColor(Color.BLACK);
				g2.drawString(Integer.toString(score[i]), 43+30*i, 275);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			}
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			g2.setColor(Color.WHITE);
			g2.fillRoundRect(100-tabSize/2, 290, tabSize, 30, 30, 30);
		}
		

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Apple ligothic", Font.PLAIN, 18));
		g2.drawString(goal, 30, 310);
		
		if(mpImg == null)
			return;
		if(timeToWalk)
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
		g.drawImage(mpImg, (getWidth()-mpImg.getWidth(null))/2, 35, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}
}
