package engine.services.audio;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton class that handles all the playing of audio in the game.
 * Both sound effects and background music.
 */
public class AudioPlayer {

    private ExecutorService soundPool;
    private ArrayList<SoundData> soundBuffer;

    private HashMap<Sound, AudioClip> audioEffectCollection;
    private HashMap<Sound, Media> backgroundMusicCollection;
    private List<MediaPlayer> backGroundSounds;
    private static AudioPlayer instance;

    private AudioPlayer(){
        soundBuffer = new ArrayList<>();
        audioEffectCollection = new HashMap<>();
        backgroundMusicCollection = new HashMap<>();
        backGroundSounds = new ArrayList<>();
        soundPool = Executors.newFixedThreadPool(10);
        innitAudioCollection();
    }

    public static AudioPlayer getInstance(){

    if(instance == null) {
        instance = new AudioPlayer();
    }
      return instance;
    }

    /**
     * Instantiate media files to play sounds from
     */
    private void innitAudioCollection(){
        audioEffectCollection.put(Sound.HIT_1, new AudioClip(new File("gamefiles/8bit_bomb_explosion.wav").toURI().toString()));
        backgroundMusicCollection.put(Sound.BACKGROUND_MUSIC_1, new Media(new File("gamefiles/happy.mp3").toURI().toString()));
        audioEffectCollection.put(Sound.ZOMBIE_ATTACK, new AudioClip(new File("gamefiles/zombie_sound.wav").toURI().toString()));
    }

    /**
     * Method to call to play sounds queued in the AudioPlayer
     */
    public synchronized void playSounds(){
        if(soundBuffer.size() > 0) {
            SoundData soundData = soundBuffer.get(soundBuffer.size() - 1);
            soundPool.execute(new Runnable() {
                @Override
                public void run() {
                    audioEffectCollection.get(soundData.sound).play();
                }
            });
            soundBuffer.remove(soundData);
            soundBuffer.trimToSize();
        }
    }

    /**
     * Method for other objects in the game to call to have a sound played
     * @param sound Enum for the sound to be played
     * @param playtime Duration the sound should be played
     */
    public synchronized void addToPlayerQueue(Sound sound, double playtime){
        if(soundBuffer.size() > 10) return;
        soundBuffer.add(new SoundData(sound, playtime));
    }

    /**
     * Method to set the background music played on a loop as long as the game is playing.
     * Up to three sounds can be played in parallel.
     * @param sounds Enum identifying the song to be played
     */
    public void setBackgroundMusic(List<Sound> sounds){
        sounds.parallelStream().forEach(sound -> {
            Platform.runLater(() -> {
                MediaPlayer mediaPlayer = new MediaPlayer(backgroundMusicCollection.get(sound));
                backGroundSounds.add(mediaPlayer);
                mediaPlayer.play();
            });
        });
    }

    public void stopBackgroundMusic(){
        backGroundSounds.forEach(mediaPlayer -> mediaPlayer.stop());
    }

    public void shutdown(){
        soundPool.shutdown();
        backGroundSounds.forEach(mediaPlayer -> mediaPlayer.dispose());
        instance = null;
    }

    /**
     * Data Object to keep the Audio request sent to the AudioPlayer in a nice structure
     */
    private class SoundData {

        final Sound sound;
        final double playtime;

        SoundData(Sound sound, double playtime){
            this.sound = sound;
            this.playtime = playtime;
        }
    }
}