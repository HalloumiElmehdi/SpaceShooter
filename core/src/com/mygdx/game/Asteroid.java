package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Asteroid extends Entity {


    float timeSincelastRandom ;
    float timeToupdate = 5f;
    double randX, randY;
    public static float speed ;
    public Asteroid(){
        this.texture = Art.asteroidTexture;
        this.position = new Vector2((float)Math.random() * 1920 , (float)Math.random() * 1080);
        this.box2D = new Rectangle(this.position.x, this.position.y, this.Width(), this.Height());
        speed = 100f;
        this.life = 1;
    }


    public void draw(Batch batch){
        this.direction = new Vector2(((float)randX - position.x) + this.texture.getWidth()/2f
                ,  (float)randY  - position.y  + this.texture.getHeight()/2f);
        direction = direction.nor();
        this.angle = (float)(Math.atan2(direction.x, direction.y));
        angle = (float)Math.toDegrees(angle);
        angle *= -1;

        batch.draw(this.texture ,
                this.position.x,
                this.position.y,
                this.Width()/2,
                this.Height()/2,
                this.Width(),
                this.Height(),
                1 , 1, angle, 0, 0,
                (int)this.Width(),
                (int)this.Height(),
                false,
                false
        );

    }




    public void update(float delta){
        timeSincelastRandom+= delta;
        if(timeSincelastRandom - timeToupdate > 0){
           randX = Math.random() * 1280 - this.Width();
           randY = Math.random() * 720 - this.Height();
           if(randX < 0)
               randX = Math.random() * 200;
           if(randY < 0)
               randY = Math.random() * 200;

         timeSincelastRandom = 0;
        }
        this.angle = (float) Math
                .atan2( randX - this.position.x, randY - this.position.y);
        this.position.x += (float) Math.cos(this.angle) * speed
                * delta;
        this.position.y += (float) Math.sin(this.angle) * speed
                * delta;
        this.box2D.x = this.position.x;
        this.box2D.y = this.position.y;
        this.box2D.width = this.Width();
        this.box2D.height = this.Height();
    }
}
