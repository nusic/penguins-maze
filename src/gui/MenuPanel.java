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

public class MenuPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public JButton newGameBtn, rematchBtn, learnBtn, menuBtn, quitGameBtn, exitBtn;
	public Image bgImg;
	
	public MenuPanel(){
		newGameBtn = new JButton(new ImageIcon("imgs//icons//newGameBtn1.png"));
		rematchBtn = new JButton(new ImageIcon("imgs//icons//rematchBtn1.png"));
		learnBtn = new JButton(new ImageIcon("imgs//icons//learnBtn1.png"));
		menuBtn = new JButton(new ImageIcon("imgs//icons//menuBtn1.png"));
		quitGameBtn = new JButton(new ImageIcon("imgs//icons//quitGameBtn1.png"));
		exitBtn = new JButton(new ImageIcon("imgs//icons//exitBtn1.png"));
		
		newGameBtn.setRolloverIcon(new ImageIcon("imgs//icons//newGameBtn2.png"));
		rematchBtn .setRolloverIcon(new ImageIcon("imgs//icons//rematchBtn2.png"));
		learnBtn.setRolloverIcon(new ImageIcon("imgs//icons//learnBtn2.png"));
		menuBtn.setRolloverIcon(new ImageIcon("imgs//icons//menuBtn2.png"));
		quitGameBtn.setRolloverIcon(new ImageIcon("imgs//icons//quitGameBtn2.png"));
		exitBtn.setRolloverIcon(new ImageIcon("imgs//icons//exitBtn2.png"));
		
		int xSize = 200;
		int ySize = 576 - 320;

		newGameBtn.setBounds(	xSize/2 - 75, ySize-225, 150, 40);
		rematchBtn.setBounds(	xSize/2 - 75, ySize-225, 150, 40);
		learnBtn.setBounds(		xSize/2 - 75, ySize-175, 150, 40);
		menuBtn.setBounds(		xSize/2 - 75, ySize-125, 150, 40);
		quitGameBtn.setBounds(	xSize/2 - 75, ySize-125, 150, 40);
		exitBtn.setBounds(		xSize/2 - 75, ySize-75, 150, 40);

		setLayout(null);
		
		this.add(newGameBtn);
		this.add(rematchBtn);
		this.add(learnBtn);
		this.add(menuBtn);
		this.add(quitGameBtn);
		this.add(exitBtn);
		
		rematchBtn.setVisible(false);
		quitGameBtn.setVisible(false);
		menuBtn.setVisible(false);
		bgImg = new ImageIcon("imgs//Backgrounds//MenuPanelBackground.jpg").getImage();
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(bgImg, 0, 0, null);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Apple ligothic", Font.PLAIN, 13));
		g2.drawString("©  Erik Broberg & Kalle Bladin 2012", exitBtn.getX()-6, getHeight()-10);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}
}
