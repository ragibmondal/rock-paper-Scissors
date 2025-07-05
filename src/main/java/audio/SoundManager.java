package audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Enhanced SoundManager that actually plays sound files
 */
public class SoundManager {
    private boolean soundEnabled = true;
    private float volume = 0.8f;
    private Map<String, Clip> soundClips = new HashMap<>();
    private ExecutorService soundExecutor = Executors.newCachedThreadPool();
    
    // Sound file names
    private static final String[] SOUND_FILES = {
        "countdown_tick", "draw", "game_end", "game_start", 
        "gesture_select", "lose", "win"
    };
    
    public SoundManager() {
        loadSounds();
        System.out.println("SoundManager initialized with real audio playback");
    }
    
    private void loadSounds() {
        for (String soundName : SOUND_FILES) {
            try {
                String resourcePath = "/sounds/" + soundName + ".wav";
                InputStream audioSrc = getClass().getResourceAsStream(resourcePath);
                
                if (audioSrc != null) {
                    InputStream bufferedIn = new BufferedInputStream(audioSrc);
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
                    
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    
                    // Set volume
                    FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float gain = 20f * (float) Math.log10(volume);
                    volumeControl.setValue(gain);
                    
                    soundClips.put(soundName, clip);
                    System.out.println("Loaded sound: " + soundName);
                } else {
                    System.out.println("Sound file not found: " + resourcePath);
                }
            } catch (Exception e) {
                System.out.println("Error loading sound " + soundName + ": " + e.getMessage());
            }
        }
    }
    
    public void playSound(String soundName) {
        if (!soundEnabled) return;
        
        soundExecutor.submit(() -> {
            try {
                Clip clip = soundClips.get(soundName);
                if (clip != null) {
                    // Stop if already playing
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                    
                    // Reset to beginning
                    clip.setFramePosition(0);
                    
                    // Play the sound
                    clip.start();
                    
                    System.out.println("Playing sound: " + soundName);
                } else {
                    System.out.println("Sound not found: " + soundName);
                }
            } catch (Exception e) {
                System.out.println("Error playing sound " + soundName + ": " + e.getMessage());
            }
        });
    }
    
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        System.out.println("Sound " + (enabled ? "enabled" : "disabled"));
    }
    
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
    
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
        
        // Update volume for all loaded clips
        for (Clip clip : soundClips.values()) {
            try {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float gain = 20f * (float) Math.log10(this.volume);
                volumeControl.setValue(gain);
            } catch (Exception e) {
                // Volume control not available for this clip
            }
        }
        
        System.out.println("Volume set to: " + (this.volume * 100) + "%");
    }
    
    public float getVolume() {
        return volume;
    }
    
    public void stopAllSounds() {
        for (Clip clip : soundClips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
        System.out.println("All sounds stopped");
    }
    
    public void cleanup() {
        stopAllSounds();
        for (Clip clip : soundClips.values()) {
            clip.close();
        }
        soundClips.clear();
        soundExecutor.shutdown();
        System.out.println("SoundManager cleanup completed");
    }
    
    // For testing - get available sounds
    public String[] getAvailableSounds() {
        return soundClips.keySet().toArray(new String[0]);
    }
}
