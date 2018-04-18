package engine.services.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class AudioPlayer {

    private ArrayBlockingQueue<SoundData> soundBuffer;
    private HashMap<Sound, Media> audioColletion;
    private List<MediaPlayer> backGroundSounds;
    private AudioPlayer instance;

    private AudioPlayer(){
        soundBuffer = new ArrayBlockingQueue(20);
        audioColletion = new HashMap<>();
        backGroundSounds = new ArrayList<>();
        innitAudioCollection();
    }

    public AudioPlayer getInstance() throws IOException{

    if(instance == null) {
        instance = new AudioPlayer();
    }
      return instance;
    }


    private void innitAudioCollection(){
        audioColletion.put(Sound.HIT_1, new Media(new File("gamefiles/8bit_bomb_explosion.wav").toURI().toString()));
        audioColletion.put(Sound.BACKGROUND_MUSIC_1, new Media(new File("gamefiles/happy.mp3").toURI().toString()));
    }

    public void playSounds(){
        soundBuffer.parallelStream().forEach(soundData -> {

            MediaPlayer mediaPlayer = new MediaPlayer(audioColletion.get(soundData.sound));
            mediaPlayer.play();
            double playTime = System.currentTimeMillis() + soundData.playtime;
            while(true){
                if(System.currentTimeMillis() > playTime){
                    mediaPlayer.stop();
                    break;
                }
            }
        });
        soundBuffer.clear();
    }

    public void addToPlayerQueue(Sound sound, double playtime){
        soundBuffer.add(new SoundData(sound, playtime));
    }

    public void setBackgroundMusic(List<Sound> sounds){
        sounds.parallelStream().forEach(sound -> {
            MediaPlayer mediaPlayer = new MediaPlayer(audioColletion.get(sound));
            backGroundSounds.add(mediaPlayer);
            mediaPlayer.play();
        });

    }
    public void stopBackgroundMusic(){
        backGroundSounds.stream().forEach(mediaPlayer -> mediaPlayer.stop());
    }


    private class SoundData {

        final Sound sound;
        final double playtime;

        SoundData(Sound sound, double playtime){
            this.sound = sound;
            this.playtime = playtime;
        }
    }
}
