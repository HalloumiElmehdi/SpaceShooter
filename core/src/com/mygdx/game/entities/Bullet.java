package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Art;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;


public class Bullet extends Entity {
    public static float power;
    public Bullet(){
        super(Art.smallBulletTexture,
                new Vector2(),
                600f,
                1
        );
        this.power = 2f;
        this.position = new Vector2(
                        Player.getInstance().position.x + (Player.getInstance().Width()/2f) - this.Width()/2f ,
                        Player.getInstance().position.y + (Player.getInstance().Height()/2f) - this.Height()/2f
        );

        Vector2 direction = new Vector2(
                (Gdx.input.getX() - position.x) - this.texture.getWidth()/2f,
                (Gdx.graphics.getHeight() - Gdx.input.getY()) - position.y  - this.texture.getHeight()/2f
        ).nor();
        rotationAngle = (float) Math
                .atan2(direction.y, direction.x);

    }

    public void draw(Batch batch) {
        batch.draw(this.texture ,
                this.position.x,
                this.position.y,
                this.Width()/2f,
                this.Height()/2f,
                this.Width() ,
                this.Height(), 1 , 1 , rotationAngle, 0,0,
                this.Width(),
                this.Height() , false, true
        );

    }

    @Override
    protected void generateSpawnPosition() {

    }

    public void update(float delta) {
        texture = Player.getInstance().isBig ? Art.bigBulletTexture : Art.smallBulletTexture;

        super.updateBounds();

        this.position.x += (float) Math.cos(rotationAngle) * this.velocity
                *delta;
        this.position.y += (float) Math.sin(rotationAngle) * this.velocity
                * delta;

    }

}
