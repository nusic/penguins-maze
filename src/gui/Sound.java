/**
 * Linköpings Universitet
 * Medieteknik år 2
 * Projektarbete i TNM040 - Kommunikation och Användargränssnitt
 * 
 * Erik Broberg och Kalle Bladin
 * Ht-2012
 */

package gui;

import java.io.File;
import java.applet.*;

public class Sound {
	public static final int WIND = 0, ICY_TONES = 1;
	public static final int ICY_KLINK = 2, ICY_CRASH = 3, SWISH = 4, FAST_SWISH = 5, FAST_SWOSH = 6, ICY_WATER_WHIRL = 7, WINNING_ROCK = 8, ACTIVATE = 9;
	public static final int YIPPIE_WIN = 10, YIPPIE1 = 11, YIPPIE2 = 12, YIPPIE3 = 13, YIPPIE4 = 14, YIPPIE_MANY = 15, WINNING_YIPPIE_ROCK = 16; 
	private AudioClip[] clip;
	public boolean music = true;
	public boolean effects = true;
	
	
	public Sound(){
		loadSounds();
	}
	
	public void stopAll(){
		for(int i = 0; i<clip.length; i++){
			if(clip[i] != null)
				clip[i].stop();
		}
	}
	
	public boolean canPlay(int sound){
		switch (sound){
		case 0: case 1: return music;
		default: return effects;
		}
	}
	
	public void play(int sound){
		stop(sound);
		if(canPlay(sound))
			clip[sound].play();
	}
	
	public void loop(int sound){
		if(canPlay(sound))
			clip[sound].loop();
	}
	
	public void stop(int sound){
		clip[sound].stop();
	}
	
	@SuppressWarnings("deprecation")
	public void loadSounds(){
		clip = new AudioClip[30];
		try{
			clip[WIND] = Applet.newAudioClip(new File("sound//PenguinMaze_Wind.wav").toURL());
			clip[ICY_TONES] = Applet.newAudioClip(new File("sound//PenguinMaze_IcyTones.wav").toURL());
			clip[ICY_KLINK] = Applet.newAudioClip(new File("sound//PenguinMaze_IcyKlink.wav").toURL());
			clip[ICY_CRASH] = Applet.newAudioClip(new File("sound//PenguinMaze_IcyCrash.wav").toURL());
			clip[SWISH] = Applet.newAudioClip(new File("sound//PenguinMaze_Swish.wav").toURL());
			clip[FAST_SWISH] = Applet.newAudioClip(new File("sound//PenguinMaze_FastSwish.wav").toURL());
			clip[FAST_SWOSH] = Applet.newAudioClip(new File("sound//PenguinMaze_FastSwosh.wav").toURL());
			clip[ICY_WATER_WHIRL] = Applet.newAudioClip(new File("sound//PenguinMaze_IcyWaterWhirl.wav").toURL());
			clip[WINNING_ROCK] = Applet.newAudioClip(new File("sound//PenguinMaze_WinningRock.wav").toURL());
			clip[ACTIVATE] = Applet.newAudioClip(new File("sound//PenguinMaze_Activate.wav").toURL());
			clip[YIPPIE_WIN] = Applet.newAudioClip(new File("sound//PenguinMaze_YippieWin.wav").toURL());
			clip[YIPPIE1] = Applet.newAudioClip(new File("sound//PenguinMaze_Yippie1.wav").toURL());
			clip[YIPPIE2] = Applet.newAudioClip(new File("sound//PenguinMaze_Yippie2.wav").toURL());
			clip[YIPPIE3] = Applet.newAudioClip(new File("sound//PenguinMaze_Yippie3.wav").toURL());
			clip[YIPPIE4] = Applet.newAudioClip(new File("sound//PenguinMaze_Yippie4.wav").toURL());
			clip[YIPPIE_MANY] = Applet.newAudioClip(new File("sound//PenguinMaze_YippieMany.wav").toURL());
			clip[WINNING_YIPPIE_ROCK] = Applet.newAudioClip(new File("sound//PenguinMaze_WinningYippieRock.wav").toURL());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
