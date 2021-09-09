package com.mygdx.game.ressources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Config;

public class Sounds {
    public static Music menuMusic;
    public static Music gameMusic;
    public static Sound laserSound;
    public static Sound explosionSound;
    public static Sound gameOverSound;
    public static Sound powerUpSound;
    public static float volume = 0.1f;

    public static void load(){
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menuMusic.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/bgMusic.mp3"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/LaserShot.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion_1.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/GameOver.mp3"));
        powerUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/powerup.mp3"));
    }
    public static void shoot(){
        long id = Sounds.laserSound.play(Config.volume);
        Sounds.laserSound.setPitch(id, 1);
        Sounds.laserSound.setLooping(id, false);
    }
    public static void gameOverVoice(){
        long id = Sounds.laserSound.play(Config.volume);
        Sounds.laserSound.setPitch(id, 1);
        Sounds.laserSound.setLooping(id, false);
    }
    public static void playGameOverExplosion(){
        long id = Sounds.explosionSound.play(Config.volume);
        Sounds.explosionSound.setPitch(id, 0.8f);
        Sounds.explosionSound.setLooping(id, false);
    }
    public static void PlayPowerUpSound(){
        long idPowerUp = Sounds.powerUpSound.play(1f);
        Sounds.powerUpSound.setPitch(idPowerUp, 1f);
        Sounds.powerUpSound.setLooping(idPowerUp, false);
    }
    public static void playCollisionExplosion(){
        long id = Sounds.explosionSound.play(Config.volume);
        Sounds.explosionSound.setPitch(id, 1f);
        Sounds.explosionSound.setLooping(id, false);
    }

    public static void decreaseVolume(float delta) {
        volume-= delta;
        if(volume < 0)
            Sounds.volume = 0f;
        gameMusic.setVolume(Sounds.volume);
    }

    public static void increaseVolume(float delta) {
        volume+= delta;
        if(volume > 1)
            volume = 1f;
        gameMusic.setVolume(Config.volume);
    }

}

