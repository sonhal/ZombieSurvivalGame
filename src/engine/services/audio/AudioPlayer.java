package engine.services.audio;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Singleton class that handles all the playing of audio in the game.
 * Both sound effects and background music.
 */
public class AudioPlayer {

    private ArrayList<SoundData> soundBuffer;

    private HashMap<Sound, Media> audioCollection;
    private List<MediaPlayer> backGroundSounds;
    private static AudioPlayer instance;
    private static final double PLAY_DELAY = 100;
    private double lastPlay;

    private AudioPlayer(){
        soundBuffer = new ArrayList<>();
        audioCollection = new HashMap<>();
        backGroundSounds = new ArrayList<>();
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
        audioCollection.put(Sound.HIT_1, new Media(new File("gamefiles/8bit_bomb_explosion.wav").toURI().toString()));
        audioCollection.put(Sound.BACKGROUND_MUSIC_1, new Media(new File("gamefiles/happy.mp3").toURI().toString()));
        audioCollection.put(Sound.ZOMBIE_ATTACK, new Media(new File("gamefiles/zombie_sound.wav").toURI().toString()));
    }

    /**
     * Method to call to play sounds queued in the AudioPlayer
     */
    public synchronized void playSounds(){
        if(System.currentTimeMillis() > lastPlay + PLAY_DELAY){
            if(soundBuffer.size() > 0){
                SoundData soundData= soundBuffer.get(soundBuffer.size() - 1);
                Platform.runLater(() -> {
                    MediaPlayer mediaPlayer = new MediaPlayer(audioCollection.get(soundData.sound));
                    mediaPlayer.play();
                    mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
                });
                soundBuffer.remove(soundData);
            }
            lastPlay = System.currentTimeMillis();
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
                MediaPlayer mediaPlayer = new MediaPlayer(audioCollection.get(sound));
                backGroundSounds.add(mediaPlayer);
                mediaPlayer.play();
            });
        });
    }

    public void stopBackgroundMusic(){
        backGroundSounds.stream().forEach(mediaPlayer -> mediaPlayer.stop());
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
