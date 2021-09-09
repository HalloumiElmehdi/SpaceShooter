package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ressources.Art;

import java.util.Random;

public class Asteroid extends  Enemy {
    // vectors for position and velocity
    private static final Random randomNumberGen = new Random();
    // timer to keep track of when this moving circle should change direction
    float directionChangetimer = 0;


    public Asteroid(){
        super(Art.asteroidTexture, 10f, 1);
        generateSpawnPosition();
    }
    @Override
    public void generateSpawnPosition() {
        this.position = new Vector2((float)Math.random() * Gdx.graphics.getWidth() , (float)Math.random() * Gdx.graphics.getHeight());
    }
    public void update(float delta) {
        move(delta);
        updateBounds();
    }
    private void move(float delta){

        position.x += velocity * delta;
        position.y += velocity* delta;

        directionChangetimer  += delta;

        // the circles will modify their direction every 3 seconds
        // changing this to an instance variable (remove static) would allow different circles to
        // change directions at different frequencies, making some more erratic than others
        float directionChangeFrequency = 3f;
        if(directionChangetimer >= directionChangeFrequency){
            directionChangetimer -= directionChangeFrequency;
            // these determine how much of a direction change can occur per directionChangeFrequency
            float minDirectionChangeAmount = 0.1f;
            float maxDirectionChangeAmount = 10f;
            float directionChangeRange = maxDirectionChangeAmount - minDirectionChangeAmount;
            // calculate a random change amount between the minimum and max
            float directionChangeAmount = randomNumberGen.nextFloat() * directionChangeRange + minDirectionChangeAmount;
            // flip the sign half the time so that the velocity increases and decreases
            if(randomNumberGen.nextBoolean()){
                directionChangeAmount  = -directionChangeAmount;
            }
            // apply the change amount to the velocity;
            velocity += directionChangeAmount;
        }
    }
}


