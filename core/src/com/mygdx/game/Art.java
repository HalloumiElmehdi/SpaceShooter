package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

public class Art {

    // game Texture assets
     static Texture asteroidTexture;
     static Texture smallBulletTexture;
     static Texture bigBulletTexture;
     static Texture enemyTexture;
     static Texture playerTexture;
     static Texture bigPlayerTexture;
     static Texture powerUpTexture;
     static Texture hitAnimationTexture;
     static Texture enemyHitBox;
     static Texture explosionTexture;
     static Pixmap cursorTexture;
     static Array<Texture> liveTextures;


     // menu Texture assets
     static Texture playButtonTexture;
     static Texture exitButtonTexture;
     static Texture playButtonActiveTexture;
     static Texture exitButtonActiveTexture;
     static Texture bg;
     static BitmapFont fontHeader;
     static BitmapFont fontFooter;
     static BitmapFont commandFont;
     static BitmapFont commandFontKeys;
     static FreeTypeFontGenerator fontGenerator;
     static Texture pauseBtnRedTexture;
     static Texture pauseBtnBlueTexture;



    // get the background texture while animating
     static Array<Texture> prepareBackgrounds(){
        Array<Texture> textures = new Array<Texture>();
        for(int i = 0; i < 8;i++){
            textures.add(new Texture(Gdx.files.internal("backgrounds/bg" + i + ".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        return textures;
    }


    static void load(){
         asteroidTexture = new Texture(Gdx.files.internal("enemies/asteroid.png"));
         smallBulletTexture = new Texture(Gdx.files.internal("bullets/smallBullet.png"));
         bigBulletTexture = new Texture(Gdx.files.internal("bullets/bigBullet.png"));
         enemyTexture = new Texture(Gdx.files.internal("enemies/enemy.png"));
         playerTexture = new Texture(Gdx.files.internal("player/player.png"));
         bigPlayerTexture = new Texture(Gdx.files.internal("player/bigPlayer.png"));
         powerUpTexture = new Texture(Gdx.files.internal("player/player.png"));
         hitAnimationTexture = new Texture(Gdx.files.internal("player/hitAnimation.png"));
         enemyHitBox = new Texture(Gdx.files.internal("enemies/enemyHitBox.png"));
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
