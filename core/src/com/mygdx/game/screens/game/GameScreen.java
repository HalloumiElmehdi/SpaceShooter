package com.mygdx.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.effects.EffectManager;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.Player;
import com.mygdx.game.ressources.Art;
import com.mygdx.game.Config;
import com.mygdx.game.common.ScrollingBackground;
import com.mygdx.game.ressources.Sounds;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.MainScreen;

public class GameScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private MainScreen game;
    //
    private final ScrollingBackground parallaxBackground = ScrollingBackground.getInstance();
    private boolean win = false;
    private boolean isPause = false;
    private boolean gameOver = Player.getInstance().life == 0;
    private boolean blink = false ;
    private float blinkTimeCounter;
    private float resetTime;
    private float animationTimeCounter = 0f;

    com.mygdx.game.screens.game.HUD HUD;


    //constructor
    public GameScreen(MainScreen game){
        this.game = game;
        parallaxBackground.setSpeed(0.5f);
        Sounds.gameMusic.setVolume(Sounds.volume);
        Sounds.gameMusic.setLooping(true);
        HUD = new HUD();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();
        drawBackground();
        EffectManager.updateAndDrawExplosions(deltaTime, batch);
       if(!gameOver) {
           drawHUD(deltaTime);
           handleCommands(deltaTime);
           EntityManager.drawEntities(batch);
           if(!isPause) {
               EntityManager.handleBulletShoot();
               EntityManager.spawnEntities(deltaTime);
               EntityManager.updateEntities(deltaTime);
               EntityManager.handleExpiredBullets();
               EntityManager.handleCollision();
               EntityManager.updateLevel();
               updateHUD(deltaTime);
               Sounds.gameMusic.play();
           }
           else {
               drawPauseButton(deltaTime);
               Sounds.gameMusic.pause();
           }

    }
        if(gameOver){
            batch.draw(Art.hitAnimationTexture,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            resetTime+= deltaTime;
            float resetFrameTime = 4f;
            if( resetTime - resetFrameTime > 0){
                game.setScreen(new MainMenuScreen(game, "GAME OVER", EntityManager.player.score));
            }
        }
        batch.end();
    }
    private void drawBackground(){
        parallaxBackground.draw(batch);
        parallaxBackground.update();
    }

    private void drawHUD(float deltaTIme){
        HUD.draw(batch, Player.getInstance().level, deltaTIme);
    }

    private void drawPauseButton(float deltaTime){
        blinkTimeCounter+= deltaTime;
        float blinkTimePause = 0.5f;
        if(blinkTimeCounter - blinkTimePause > 0){
            blinkTimeCounter = 0;
            blink = !blink;
        }
        if(blink){
            batch.draw(Art.pauseBtnBlueTexture, Gdx.graphics.getWidth()/2 - (Art.pauseBtnBlueTexture.getWidth()/2), Gdx.graphics.getHeight()/2, Art.pauseBtnBlueTexture.getWidth(), Art.pauseBtnBlueTexture.getHeight());
        }
        else{
            batch.draw(Art.pauseBtnRedTexture, Gdx.graphics.getWidth()/2 - (Art.pauseBtnRedTexture.getWidth()/2), Gdx.graphics.getHeight()/2, Art.pauseBtnRedTexture.getWidth(), Art.pauseBtnRedTexture.getHeight());
        }
    }

    private void updateHUD(float deltaTime){
        HUD.update(deltaTime);
    }

    private void handleCommands(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            if(isPause)
                resume();
            else
                pause();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            Sounds.decreaseVolume(delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            Sounds.increaseVolume(delta);
        }
    }

    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {
        isPause = true;
    }
    @Override
    public void resume() {
        isPause = false;
    }
    @Override
    public void hide() {}
    @Override
    public void show() {}
    @Override
    public void dispose() {}

}
