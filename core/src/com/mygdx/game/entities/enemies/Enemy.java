package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.ressources.Art;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;

public class Enemy extends Entity {
    public boolean isHit;
    private float hitCounter ;
    public Enemy(Texture texture, float velocity, int life) {
        super(texture, new Vector2(), velocity, life);
        generateSpawnPosition();
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(this.texture ,
                this.position.x,
                this.position.y,
                (float)this.Width()/2,
                (float)this.Height()/2,
                this.Width(),
                this.Height(),
                1 ,
                1,
                rotationAngle,
                0,
                0,
                this.Width(),
                this.Height(),
                false,
                false
        );
    }

    protected void drawEnemyLives(Batch batch) {
        if(this instanceof Criminal)
            batch.draw(Art.liveTextures.get(this.life), position.x  , position.y + (this.Height()/2f)  + this.Height()/2f, 40, 6);

        if(this instanceof Tricky) {
            batch.draw(Art.liveTextures.get(this.life), position.x  , position.y + (this.Height()/2f)  + this.Height()/2f, 30, 4.5f);
        }

    }
    protected void drawHitEffect(Batch batch, Texture hitTexture){
        if(isHit){
            batch.draw(hitTexture ,
                    this.position.x,
                    this.position.y,
                    this.Width()/2f,
                    this.Height()/2f,
                    this.Width(),
                    this.Height(),
                    1 ,
                    1,
                    rotationAngle,
                    0,
                    0,
                    this.Width(),
                    this.Height(),
                    false,
                    false
            );
        }
    }

    public void generateSpawnPosition(){
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
        this.position = chosen;
    }
    public void update(float delta) {
        Vector2 targetPoint =  new Vector2(
                Player.getInstance().position.x ,
                Player.getInstance().position.y
        );
        super.update(targetPoint);

    }
    protected void followPlayer(float delta){
        float angle;
        angle = (float) Math
                .atan2(Player.getInstance().Position().y  - this.position.y, Player.getInstance().Position().x  - this.position.x);
        this.position.x += (float) Math.cos(angle) * velocity
                *delta;
        this.position.y += (float) Math.sin(angle) * velocity
                * delta;
    }

    public static Criminal createCriminal() {
        return new Criminal();
    }
    public static Tricky createTricky() {
        return new Tricky();
    }
    public static Asteroid createAsteroid() {
        return new Asteroid();
    }


}
