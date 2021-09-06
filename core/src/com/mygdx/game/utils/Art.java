package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

public class Art {

    // game Texture assets
    public static Texture asteroidTexture;
    public static Texture bigBulletTexture;
    public static Texture criminalTexture;
    public static Texture trickyTexture;
    public static Texture playerTexture;
    public static Texture bigPlayerTexture;
    public static Texture powerUpTexture;
    public static Texture powerUpSpeedTexture;
    public static Texture hitAnimationTexture;
    public static Texture criminalHitBoxTexture;
    public static Texture explosionTexture;
    public static Pixmap cursorTexture;
    public static Texture smallBulletTexture;
    public static Array<Texture> liveTextures;


     // menu Texture assets
     public static Texture playButtonTexture;
     public static Texture exitButtonTexture;
     public static Texture playButtonActiveTexture;
     public static Texture exitButtonActiveTexture;
     public static BitmapFont fontHeader;
     public static BitmapFont fontFooter;
     public static BitmapFont commandFont;
     public static BitmapFont commandFontKeys;
     public static FreeTypeFontGenerator fontGenerator;
     public static Texture pauseBtnRedTexture;
     public static Texture pauseBtnBlueTexture;



    // get the background texture while animating
     public static Array<Texture> prepareBackgrounds(){
        Array<Texture> textures = new Array<Texture>();
        for(int i = 0; i < 8;i++){
            textures.add(new Texture(Gdx.files.internal("backgrounds/bg" + i + ".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        return textures;
    }


    public  static void load(){
         asteroidTexture = new Texture(Gdx.files.internal("enemies/asteroid.png"));
         smallBulletTexture = new Texture(Gdx.files.internal("bullets/smallBullet.png"));
         bigBulletTexture = new Texture(Gdx.files.internal("bullets/bigBullet.png"));
         criminalTexture = new Texture(Gdx.files.internal("enemies/criminal.png"));
         trickyTexture = new Texture(Gdx.files.internal("enemies/tricky.png"));
         playerTexture = new Texture(Gdx.files.internal("player/player.png"));
         bigPlayerTexture = new Texture(Gdx.files.internal("player/bigPlayer.png"));
         powerUpTexture = new Texture(Gdx.files.internal("player/player.png"));
         powerUpSpeedTexture = new Texture(Gdx.files.internal("powerUps/speed.png"));
         hitAnimationTexture = new Texture(Gdx.files.internal("player/hitAnimation.png"));
         criminalHitBoxTexture = new Texture(Gdx.files.internal("enemies/criminalHitBox.png"));
         cursorTexture = new Pixmap(Gdx.files.internal("cursor/cursor1.png"));
         explosionTexture = new Texture(Gdx.files.internal("explosions/explosion1.png"));
         playButtonTexture = new Texture(Gdx.files.internal("menu/playRed.png"));
         exitButtonTexture = new Texture(Gdx.files.internal("menu/exitRed.png"));
         playButtonActiveTexture = new Texture(Gdx.files.internal("menu/playBlue.png"));
         exitButtonActiveTexture = new Texture(Gdx.files.internal("menu/exitBlue.png"));
         fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/galaxy.otf"));
         pauseBtnRedTexture = new Texture(Gdx.files.internal("menu/pauseRed.png"));
         pauseBtnBlueTexture = new Texture(Gdx.files.internal("menu/pauseBlue.png"));

         // loop to load lives textures , consider texture atlas in the future
         liveTextures = new Array<Texture>();
         for(int i = 0; i <= 10; i++){
            liveTextures.add(new Texture(Gdx.files.internal("enemies/lives/" + i  + "h.png")));
         }
     }

}
