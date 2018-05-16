package engine.services.audio;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton class that handles all the playing of audio in the game.
 * Both sound effects and background music.
 */
public class AudioPlayer {

    private ExecutorService soundPool;
    private Set<SoundData> soundBuffer;

    private HashMap<Sound, AudioClip> audioEffectCollection;
    private HashMap<Sound, Media> backgroundMusicCollection;
    private List<MediaPlayer> backGroundSounds;
    private static AudioPlayer instance;
    private boolean isMute;
    private List<Sound> backGroundMusicLog;

    private AudioPlayer(){
        soundBuffer = new LinkedHashSet<>();
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
        try {
            audioEffectCollection.put(Sound.HIT_1, new AudioClip(new File("gamefiles/8bit_bomb_explosion.wav").toURI().toString()));
            backgroundMusicCollection.put(Sound.BACKGROUND_MUSIC_1, new Media(new File("gamefiles/happy.mp3").toURI().toString()));
            audioEffectCollection.put(Sound.ZOMBIE_ATTACK, new AudioClip(new File("gamefiles/zombie_sound.wav").toURI().toString()));
            backgroundMusicCollection.put(Sound.BACKGROUND_MUSIC_2, new Media(new File("gamefiles/dream_raid_p1.mp3").toURI().toString()));
            audioEffectCollection.put(Sound.KNIFE_ATTACK, new AudioClip(new File("gamefiles/knife_attack.wav").toURI().toString()));
            audioEffectCollection.put(Sound.PICKUP_WEAPON, new AudioClip(new File("gamefiles/pickup_1.wav").toURI().toString()));
            audioEffectCollection.put(Sound.PICKUP_HEALTH, new AudioClip(new File("gamefiles/health_1.wav").toURI().toString()));
        }catch (Exception err){
            isMute = true;
        }

    }

    /**
     * Method to call to play sounds queued in the AudioPlayer
     */
    public synchronized void playSounds(){
        if(soundBuffer.size() > 0 && !isMute) {
            SoundData soundData = soundBuffer.iterator().next();
            soundPool.execute(new Runnable() {
                @Override
                public void run() {
                    audioEffectCollection.get(soundData.sound).play();
                }
            });
            soundBuffer.remove(soundData);
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
        backGroundMusicLog = sounds;
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

    public void startBackGroundMusic(){
        backGroundSounds.forEach(mediaPlayer -> mediaPlayer.play());
    }

    public void shutdown(){
        soundPool.shutdown();
        backGroundSounds.forEach(mediaPlayer -> mediaPlayer.dispose());
        instance = null;
    }

    public void mute(){
        isMute = true;
        stopBackgroundMusic();
    }

    public void unMute(){
        isMute = false;
        startBackGroundMusic();
    }

    public void toggleSound(){
        if(isMute){
            unMute();
        }
        else {
            mute();
        }
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

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof SoundData){
                return sound == ((SoundData)obj).sound;
            }
            return false;
        }
    }
}
