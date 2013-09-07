/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gui;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class StartPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	Image bgImg;
	
	public StartPanel(){
		bgImg = new ImageIcon("imgs//Backgrounds//IntroPanelBackground.jpg").getImage();
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(bgImg, 0, 0, null);
	}
}
