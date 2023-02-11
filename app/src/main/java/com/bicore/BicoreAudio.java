package com.bicore;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import com.bicore.NativeFuntion;
import java.io.IOException;

public class BicoreAudio implements NativeFuntion.AudioEventListener {
    private static final String TAG = "BicoreAudio";

    static float MP3Volume;
    private static BicoreAudio _audio;
    static float wavVolume;
    public String DataFolder = null;
    Context context;
    protected final IntHashMap<BicoreMusic> mMusicMap = new IntHashMap<>();
    private SoundPool mSoundPool = new SoundPool(8, 3, 100);
    private final AudioManager manager;
    Resources res;

    public BicoreAudio(Activity context2, String audioFolder) {
        wavVolume = 1.0f;
        MP3Volume = 1.0f;
        this.manager = (AudioManager) context2.getSystemService(Context.AUDIO_SERVICE);
        context2.setVolumeControlStream(3);
        NativeFuntion.setAudioEventListener(this);
        this.res = context2.getResources();
        this.context = context2;
        _audio = this;
    }

    public void dispose() {
        this.mSoundPool.release();
        for (BicoreMusic music : this.mMusicMap.values()) {
            music.unload();
        }
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public int CreateWAVAudioClip(String filename, int offset, int length) {
        try {
            AssetFileDescriptor afd = this.res.getAssets().openFd("Default/W/" + filename + ".ogg");
            if (afd != null) {
                return this.mSoundPool.load(afd, 1);
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public synchronized int PlayWAVAudio(int soundId) {
        float fStreamVolume;
        Log.i("BicoreAudio", "*PlayWAVAudio id=" + soundId);
        fStreamVolume = (((float) this.manager.getStreamVolume(3)) / ((float) this.manager.getStreamMaxVolume(3))) * wavVolume;
        return this.mSoundPool.play(soundId, fStreamVolume, fStreamVolume, 0, 0, 1.0f);
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public void StopWAVAudio(int streamId) {
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public int CreateMP3AudioClip(String filename, int offset, int length) {
        try {
            BicoreMusic music = new BicoreMusic(this.res.getAssets().openFd("Default/B/" + filename + ".mp3"), offset, length);
            this.mMusicMap.put(music.getMusicId(), music);
            return music.getMusicId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public int PlayMP3Audio(int musicId, boolean bloop) {
        BicoreMusic music = this.mMusicMap.get(musicId);
        if (music == null) {
            return musicId;
        }
        music.setVolume(MP3Volume);
        return music.play(bloop);
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public void StopMP3Audio(int musicId) {
        BicoreMusic music = this.mMusicMap.get(musicId);
        if (music != null) {
            music.stop();
        }
    }

    /* access modifiers changed from: protected */
    public void pause() {
        for (BicoreMusic music : this.mMusicMap.values()) {
            music.pause();
        }
    }

    /* access modifiers changed from: protected */
    public void resume() {
        for (BicoreMusic music : this.mMusicMap.values()) {
            music.resume();
        }
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public void SetMP3Volume(int musicId, int volume) {
        MP3Volume = ((float) volume) / 100.0f;
        BicoreMusic music = this.mMusicMap.get(musicId);
        if (music != null) {
            music.setVolume(((float) volume) / 100.0f);
        }
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public void SetWAVVolume(int soundId, int volume) {
        wavVolume = ((float) volume) / 100.0f;
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public void UnloadMP3AudioClip(int musicId) {
        this.mMusicMap.remove(musicId).dispose();
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public void UnloadWAVAudioClip(int soundId) {
        this.mSoundPool.unload(soundId);
    }

    public static BicoreAudio GetAudio() {
        return _audio;
    }

    @Override // com.bicore.NativeFuntion.AudioEventListener
    public int GetMP3AudioClipCurrentPosition(int musicId) {
        BicoreMusic music = this.mMusicMap.get(musicId);
        if (music != null) {
            return music.getCurrentPosition();
        }
        return 0;
    }
}
