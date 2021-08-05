package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public final class ScrollingBackground{
    private static ScrollingBackground instance;
    private float scroll;
    private Array<Texture> layers;
    private final float LAYER_SPEED_DIFFERENCE = 1f;

    float x,y,width, height,scaleX,scaleY;
    float originX, originY,rotation,srcX,srcY;
    boolean flipX,flipY;

    private float speed;

    private ScrollingBackground(Array<Texture> textures){
        layers = textures;
        for(int i = 0; i <textures.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        scroll = 0;
        speed = 0;

        x = y = originX = originY = rotation = srcY = 0;
        width =  Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        scaleX = scaleY = 1;
        flipX = flipY = false;
    }


    public static ScrollingBackground getInstance() {
        if (instance == null) {
            instance = new ScrollingBackground(Art.prepareBackgrounds());
        }
        return instance;
    }


    public void setSpeed(float newSpeed){
        this.speed = newSpeed;
    }

    public void update(){
        scroll+= speed;
    }

    public float getSpeed(){
        return this.speed;
    }
    public void draw(Batch batch) {
        for(int i = 0;i<layers.size;i++) {
            srcX = scroll + i*this.LAYER_SPEED_DIFFERENCE *scroll;
            batch.draw(layers.get(i), x, y, originX, originY, width, height,scaleX,scaleY,rotation,(int)srcX,(int)srcY,layers.get(i).getWidth(),layers.get(i).getHeight(),flipX,flipY);
        }
    }
}
