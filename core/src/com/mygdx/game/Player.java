package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

public final class Player extends Entity {

    private static Player instance;
    private  float spawnTimeshots = 0.1f;
    private  float lastTimeShot;
    public boolean canShoot = false;
    public boolean isDead ;
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


    public boolean addBonusLife(){
        this.life += 1;
        return false;
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
                this.texture.getWidth()/2,
                this.texture.getHeight()/2,
                        this.texture.getWidth() ,
                        this.texture.getHeight(), 1 , 1 , angle, 0,0,
                        this.texture.getWidth(),
                        this.texture.getHeight() , false, false
        );



    }


    private void handleInput(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.Q) && position.x > 0)
            this.position.x -= this.velocity  * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D) && this.position.x < Gdx.graphics.getWidth() - this.texture.getWidth())
            this.position.x += this.velocity * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.Z) && this.position.y < Gdx.graphics.getHeight() - this.texture.getHeight())
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
