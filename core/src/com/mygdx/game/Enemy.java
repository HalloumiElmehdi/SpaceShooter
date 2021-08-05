package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {
    private Texture redShadowTexture;
    public static float speed ;
    public boolean isHit = false;
    private float hitCounter ;
    private float timeBetweenHit = 1f;
    public Enemy()
    {
        this.texture = Art.enemyTexture;
        this.position = generateSpawnPosition();
        this.box2D = new Rectangle(this.position.x, this.position.y, this.Width(), this.Height());
        this.life = 10;
        this.redShadowTexture = new Texture(Gdx.files.internal("enemies/enemyHitBox.png"));
    }




    public void draw(Batch batch) {
        this.direction = new Vector2((Player.getInstance().position.x - position.x) + this.texture.getWidth()/2f
                ,  Player.getInstance().position.y  - position.y  + this.texture.getHeight()/2f);
        direction = direction.nor();
        this.angle = (float)(Math.atan2(direction.x, direction.y));
        angle = (float)Math.toDegrees(angle);
        angle *= -1;

        if(isHit){
            batch.draw(this.redShadowTexture ,
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

        batch.draw(Art.liveTextures.get(this.life), position.x  , position.y + (this.Height()/2)  + this.Height()/2, 40, 6);
    }

    public void update(float delta) {
        hitCounter+= delta;
        if(hitCounter - timeBetweenHit > 0){
            isHit = false;
            hitCounter = 0;
        }
        float angle;
        angle = (float) Math
                .atan2(Player.getInstance().getPosition().y  - this.position.y, Player.getInstance().getPosition().x  - this.position.x);
        this.position.x += (float) Math.cos(angle) * speed
                *delta;
        this.position.y += (float) Math.sin(angle) * speed
                * delta;
        this.box2D.x = this.position.x;
        this.box2D.y = this.position.y;
        this.box2D.width = this.Width();
        this.box2D.height = this.Height();

    }


    private  Vector2 generateSpawnPosition(){
        Vector2  RightRandomPosition = new Vector2(Gdx.graphics.getWidth(), (float)Math.random() * Gdx.graphics.getHeight());
        Vector2  LeftRandomPosition = new Vector2(0, (float)Math.random() * Gdx.graphics.getHeight());
        Vector2  BottomRandomPosition = new Vector2(Gdx.graphics.getWidth() * (float)Math.random(), 0);
        Vector2  UpRandomPosition = new Vector2(Gdx.graphics.getWidth() * (float)Math.random(), Gdx.graphics.getHeight());
        Vector2 chosen ;
        int rand = (int)(Math.random() * 4);
        switch (rand){
            case 1: chosen =  RightRandomPosition; break;
            case 2: chosen = LeftRandomPosition; break;
            case 3: chosen = UpRandomPosition; break;
            default: chosen = BottomRandomPosition; break;
        }

        return chosen;
    }



}
