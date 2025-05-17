package audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private  Clip clip;
    private  FloatControl volumeControl;

    public void play(String filePath,boolean loop) {
        try{
            File audioFile = new File(filePath);
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);

            //针对不同操作系统，获取不同的control变量
            if(clip.isControlSupported(FloatControl.Type.VOLUME)){
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
            }else if(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)){
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }

            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else
            {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setVolume (float volume) {
        if (volumeControl != null) {
            // 确保音量值在有效范围内（通常为0.0~1.0）
            volume = Math.max(0.0f, Math.min(1.0f, volume));
            if (volumeControl.getType() == FloatControl.Type.VOLUME) {
                //针对windows系统
                volumeControl.setValue(volume);
            } else if (volumeControl.getType() == FloatControl.Type.MASTER_GAIN) {
                //针对linux系统
                float dB = (float) (Math.log(volume) / Math.log(10) * 20);
                volumeControl.setValue(dB);
            }
        }
    }

    public void stop(){
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public boolean isPlaying(){
        return clip.isRunning();
    }
}
