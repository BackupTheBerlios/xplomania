import javax.sound.sampled.*;
import java.io.*;

public class SoundEffects extends Thread {
    String soundPath;
    public SoundEffects(String soundPath) {
    	this.soundPath = soundPath;
    }
    
    public void run(){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream
            						(new File(soundPath));
            AudioFormat af = audioInputStream.getFormat();
            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio  = new byte[size];
            DataLine.Info info  = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
            
           // for(int i=0; i < 32; i++) {
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(af, audio, 0, size);
                clip.start();
           // }
        }catch(Exception e){ e.printStackTrace(); }
        
    }
    public void playSound(){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream
            						(new File(soundPath));
            AudioFormat af = audioInputStream.getFormat();
            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio  = new byte[size];
            DataLine.Info info  = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
            
           // for(int i=0; i < 32; i++) {
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(af, audio, 0, size);
                clip.start();
           // }
        }catch(Exception e){ e.printStackTrace(); }
        
    }
    
}

