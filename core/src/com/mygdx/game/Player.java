package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public final class Player extends Entity {

    private static Player instance;
    private  float spawnTimeshots = 0.1f;
    private  float lastTimeShot;
    public boolean canShoot = false;
    public int score ;

    private Player()
    {
        this.texture = Art.playerTexture;
        this.position = new Vector2(Gdx.graphics.getWidth() /2f, Gdx.graphics.getHeight()/2f);
        this.box2D = new Rectangle(this.position.x, this.position.y, this.Width(), this.Height());
        this.velocity = 200f;
        this.life = 10;
        this.score = 0;
    }


    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }


    public void setTexture(Texture texture){
        this.texture = texture;
    }
    public void update(float delta) {
        // handle player Input player input
        this.box2D.x = this.position.x;
        this.box2D.y = this.position.y;
        this.box2D.width = this.Width();
        this.box2D.height = this.Height();
        lastTimeShot += delta;
        handleInput(delta);
        this.direction = new Vector2((Gdx.input.getX() - position.x) - this.texture.getWidth()/2f
                ,  (Gdx.graphics.getHeight() - Gdx.input.getY()) - position.y  - this.texture.getHeight()/2f);
        direction = direction.nor();
        this.angle = (float)(Math.atan2(direction.x, direction.y));
        angle = (float)Math.toDegrees(angle);
        angle *= -1;
    }

    public void draw(Batch batch) {
        batch.draw(this.texture ,
                this.position.x,
                this.position.y,
                this.Width()/2,
                this.Height()/2,
                this.Width() ,
                this.Height(), 1 , 1 , angle, 0,0,
                this.Width(),
                this.Height() , false, false
        );



    }


    private void handleInput(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.Q) && position.x > 0)
            this.position.x -= this.velocity  * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D) && this.position.x < Gdx.graphics.getWidth() - this.Width())
            this.position.x += this.velocity * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.Z) && this.position.y < Gdx.graphics.getHeight() - this.Height())
            this.position.y += this.velocity * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S) && this.position.y > 0)
            this.position.y -= this.velocity * delta;
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            if(lastTimeShot - spawnTimeshots >= 0)
            {
                canShoot = true;
                lastTimeShot = 0;
            }
            else
            {
                canShoot = false;
            }

        }
    }
}
