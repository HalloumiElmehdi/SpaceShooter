package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class PowerUp extends Entity {

    private float randX , randY;
    public PowerUp(){
        this.texture = Art.playerTexture;
        randX = (float)Math.random() * 1280 - this.Width();
        randY =  (float)Math.random() * 720 - this.Height();
        if(randX < 0)
            randX = 100;
        if(randY < 0)
            randY = 100;
        this.position = new Vector2(randX, randY);
        this.box2D = new Rectangle(this.position.x , this.position.y , this.Width(), this.Height());

    }


    private float timeCounter ;
    private float timeBetweenAngleSwitch = 0.01f;
    public void update(float delta){
        this.box2D.x = this.position.x;
        this.box2D.y = this.position.y;
        this.box2D.width = this.Width();
        this.box2D.height = this.Height();
        timeCounter+= delta;
        if(timeCounter - timeBetweenAngleSwitch > 0) {
            this.angle += 5f;
            timeCounter = 0f;
        }


    }
    public void draw(Batch batch){
        batch.draw(this.texture ,
                this.position.x,
                this.position.y,
                this.texture.getWidth()/2,
                this.texture.getHeight()/2,
                this.texture.getWidth() ,
                this.texture.getHeight(), 1 , 1 , this.angle, 0,0,
                this.texture.getWidth(),
                this.texture.getHeight() , false, false
        );
    }

    public boolean isColliding(Rectangle rec){
        return this.box2D.overlaps(rec);
    }

}


