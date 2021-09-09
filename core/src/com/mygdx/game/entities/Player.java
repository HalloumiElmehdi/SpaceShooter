package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ressources.Art;


public  class Player extends Entity {
    private static Player instance;
    public boolean canShoot;
    public int score ;
    public int level;
    public boolean isBig;
    public boolean hasSpeed;
    public boolean isHit;
    public  boolean win;

    private  float lastTimeShot;


    private Player()
    {
        super(Art.playerTexture,
                new Vector2(Gdx.graphics.getWidth() /2f, Gdx.graphics.getHeight()/2f),
                200f,
                100);
        this.canShoot = false;
        this.isBig = false;
        this.hasSpeed = false;
        this.level = 1;
    }


    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }
    public static void  resetInstance(){
        instance = null;
    }


    public void update(float delta) {
        super.texture = isBig?Art.bigPlayerTexture:Art.playerTexture;
        Vector2 targetPoint = new Vector2(
                Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY()
        );
        super.update(targetPoint);
        handleInput(delta);
        lastTimeShot += delta;
    }

    public void draw(Batch batch) {
        batch.draw(this.texture ,
                this.position.x,
                this.position.y,
                this.Width()/2f,
                this.Height()/2f,
                this.Width(),
                this.Height(),
                1 ,
                1 ,
                rotationAngle,
                0,
                0,
                this.Width(),
                this.Height(),
                false,
                false
        );
    }
    @Override
    protected void generateSpawnPosition() {

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
            float spawnTimeshots = 0.1f;
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
