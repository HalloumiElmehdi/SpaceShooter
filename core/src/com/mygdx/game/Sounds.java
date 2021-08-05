package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    static Music menuMusic;
    static Music gameMusic;
    static Sound laserSound;
    static Sound explosionSound;
    static Sound gameOverSound;
    static Sound powerUpSound;



    static void load(){
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menuMusic.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/bgMusic.mp3"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/LaserShot.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion_1.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/GameOver.mp3"));
        powerUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/powerup.mp3"));
    }
}

