package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PowerUp extends Entity {

    private float randX;
    private float randY;
    private float timeCounter;
    private final float timeBetweenAngleSwitch;
    public PowerUp(){
        this.texture = Art.playerTexture;
        this.randX = (float)Math.random() * 1280 - this.Width();
        this.randY =  (float)Math.random() * 720 - this.Height();
        this.timeBetweenAngleSwitch = 0.01f;
        this.timeCounter = 0f;
        if(randX < 0)
            randX = 100;
        if(randY < 0)
            randY = 100;
        this.position = new Vector2(randX, randY);
        this.box2D = new Rectangle(this.position.x , this.position.y , this.Width(), this.Height());
    }


    public void update(float delta){
        this.box2D.x = this.position.x;
        this.box2D.y = this.position.y;
        this.box2D.width = this.Width();
        this.box2D.height = this.Height();
        this.timeCounter+= delta;
        if(this.timeCounter - timeBetweenAngleSwitch > 0) {
            this.angle += 5f;
            this.timeCounter = 0f;
        }
    }
    public void draw(Batch batch){
        batch.draw(this.texture ,
                this.position.x,
                this.position.y,
                this.Width()/2,
                this.Height()/2,
                this.Width() ,
                this.Height(), 1 , 1 , this.angle, 0,0,
                this.Width(),
                this.Height() , false, false
        );
    }
}


