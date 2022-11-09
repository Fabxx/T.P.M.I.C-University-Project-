package scct.Window.Types.Utils;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import scct.App;

/**
 * Semplice player di file .wav per musica e suoni
 */
public class Suono {

    private File path;
    private AudioInputStream stream;
    private Clip audio;

    public Suono(){
        try {
            audio = AudioSystem.getClip();
        } catch (LineUnavailableException e) {}
    }

    public Suono(String name){
        try {
            audio = AudioSystem.getClip();
            setNewAudioFile(name);
        } catch (LineUnavailableException e) {}
    }
    
    public final void setNewAudioFile(String newAudio){
        path = new File(newAudio);
        try {
            if(path.exists()){
                stream = AudioSystem.getAudioInputStream(path);
                audio.close();
                audio.open(stream);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}
    }

    public boolean isValid(){
        return audio != null;
    }

    public void play(){
        if(audio != null && stream != null){
            setVolume(App.OPTIONS.getMusicGainSetting());
            audio.start();
        }
            
    }

    public void stop(){
        if(audio != null && stream != null){
            audio.stop();
            try {
                stream.close();
            } catch (IOException e) {}
        }
    }

    public void enableLoop(){
        if(audio != null)
            audio.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void disableLoop(){
        if(audio != null)
            audio.loop(0);
    }

    public void setVolume(int level) {
        if(audio != null){
            FloatControl volume = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
            if (volume != null) {
                volume.setValue((float) (level));
            }
        }
    }

    public long getMicrosecondPosition(){
        return audio.getMicrosecondPosition();
    }

    public long getMicrosecondLenght(){
        return audio.getMicrosecondLength();
    }
    
}
