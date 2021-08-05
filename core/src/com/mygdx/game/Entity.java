package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity {



    // attributes
    protected Texture texture;
    protected Vector2 position;
    protected  Vector2 direction;
    protected float velocity ;
    protected float angle;
    protected int life;
    public Rectangle box2D;

    //methods

    public Rectangle getBox2D(){
        return this.box2D;
    }
    public Texture getTexture() {
        return texture;
    }
    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getDirection() {
        return direction;
    }

    public float Width(){return this.texture.getWidth();}
    public float Height(){return this.texture.getHeight();}
    public boolean isColliding(Rectangle rec){
        return this.box2D.overlaps(rec);
    }


}
