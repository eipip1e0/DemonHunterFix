package com.bicore;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* access modifiers changed from: package-private */
/* compiled from: BicoreAudio */
public class BicoreMusic implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private static int nextMusicId = 1;
    private final int MusicId;
    private AssetFileDescriptor afd;
    private float fVolume;
    private final int length;
    protected final IntHashMap<MediaPlayer> mStreamMap = new IntHashMap<>();
    private int nextStreamId;
    private final int offset;
    protected final List<MediaPlayer> wasPlaying = new ArrayList();

    BicoreMusic(AssetFileDescriptor afd2, int offset2, int length2) {
        this.afd = afd2;
        this.offset = offset2;
        this.length = length2;
        int i = nextMusicId;
        nextMusicId = i + 1;
        this.MusicId = i;
        this.nextStreamId = 1;
    }

    public int getMusicId() {
        return this.MusicId;
    }

    public void dispose() {
        for (MediaPlayer player : this.mStreamMap.values()) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        }
        this.mStreamMap.clear();
        BicoreAudio.GetAudio().mMusicMap.remove(this.MusicId);
    }

    public void pause() {
        this.wasPlaying.clear();
        for (MediaPlayer player : this.mStreamMap.values()) {
            if (player.isPlaying()) {
                player.pause();
                this.wasPlaying.add(player);
            }
        }
    }

    public void resume() {
        for (MediaPlayer player : this.wasPlaying) {
            player.start();
        }
    }

    public void unload() {
        for (MediaPlayer player : this.mStreamMap.values()) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        }
        this.mStreamMap.clear();
    }

    public int play(boolean isLooping) {
        MediaPlayer player;
        try {
            player = new MediaPlayer();
            player.setOnCompletionListener(this);
            player.setOnPreparedListener(this);
            player.setVolume(this.fVolume, this.fVolume);
            player.setDataSource(this.afd.getFileDescriptor(), this.afd.getStartOffset() + ((long) this.offset), (long) this.length);
            player.prepareAsync();
            player.setLooping(isLooping);
            this.mStreamMap.put(this.nextStreamId, player);
            int i = this.nextStreamId;
            this.nextStreamId = i + 1;
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            int i2 = this.nextStreamId;
            this.nextStreamId = i2 + 1;
            return i2;
        }
    }

    public void setVolume(float volume) {
        this.fVolume = volume;
        for (MediaPlayer player : this.mStreamMap.values()) {
            if (player.isPlaying()) {
                player.setVolume(volume, volume);
            }
        }
    }

    public float getVolume() {
        return this.fVolume;
    }

    public void stop() {
        for (MediaPlayer player : this.mStreamMap.values()) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        }
        this.mStreamMap.clear();
    }

    public int getCurrentPosition() {
        for (MediaPlayer player : this.mStreamMap.values()) {
            if (player.isPlaying()) {
                return player.getCurrentPosition();
            }
        }
        return 0;
    }

    public void onCompletion(MediaPlayer player) {
        Log.e("BicoreMusic", "*onCompletion");
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        this.mStreamMap.remove(this.mStreamMap.getKey(player));
    }

    public void onPrepared(MediaPlayer player) {
        Log.e("BicoreMusic", "*onPrepared");
        player.start();
    }
}
