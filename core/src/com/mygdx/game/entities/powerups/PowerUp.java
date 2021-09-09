package com.mygdx.game.entities.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;

public abstract class PowerUp extends Entity {

    private float timeCounter;
    private  float timeBetweenAngleSwitch ;
    public PowerUp(Texture texture, Vector2 position, float velocity, int life){
        super(texture,
                position,
                velocity,
                life
        );
        generateSpawnPosition();
    }
    protected void generateSpawnPosition(){
        float randX = (float) Math.random() * Gdx.graphics.getWidth() - this.Width();
        float randY = (float) Math.random() * Gdx.graphics.getHeight() - this.Height();
        this.timeBetweenAngleSwitch = 0.01f;
        this.timeCounter = 0f;
        if(randX < 0)
            randX = 100;
        if(randY < 0)
            randY = 100;
        this.position = new Vector2(randX, randY);
    }

    public void update(float delta){
        this.updateBounds();
        this.timeCounter+= delta;
        if(this.timeCounter - timeBetweenAngleSwitch > 0) {
            this.rotationAngle += 5f;
            this.timeCounter = 0f;
        }
    }
    public void draw(Batch batch){
        batch.draw(this.texture ,
                this.position.x,
                this.position.y,
                this.Width()/2f,
                this.Height()/2f,
                this.Width() ,
                this.Height(), 1 , 1 , this.rotationAngle, 0,0,
                this.Width(),
                this.Height() , false, false
        );
    }
    public abstract void feature();

}


