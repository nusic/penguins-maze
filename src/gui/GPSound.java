/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gui;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.util.Random;


public class GPSound {

	public static final int FOOTSTEP = 0, EAT = 1;
	private AudioClip[] footSteps;
	private AudioClip[] eat;
	private Random r;
	public boolean on = true;
	
	public GPSound(){
		loadSounds();
		r = new Random();
	}
	
	public void play(int sound){
		if(!on)
			return;
		switch(sound){
		case FOOTSTEP: playFootstep(); break;
		case EAT: playEat(); break;
		}	
	}
	
	private void playFootstep(){
		footSteps[r.nextInt(footSteps.length)].play();
	}
	
	private void playEat(){
		eat[r.nextInt(eat.length)].play();
	}
	
	@SuppressWarnings("deprecation")
	public void loadSounds(){
		footSteps = new AudioClip[7];
		try{
			for(int i = 0; i<footSteps.length; i++)
				footSteps[i] = Applet.newAudioClip(new File("sound//GPSound_Footstep" + i + ".wav").toURL());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		eat = new AudioClip[8];
		try{
			for(int i = 0; i<eat.length; i++)
				eat[i] = Applet.newAudioClip(new File("sound//GPSound_Eat" + i + ".wav").toURL());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
