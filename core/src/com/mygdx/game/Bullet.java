package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Bullet extends Entity {
    public Bullet(){
        this.texture = Art.smallBulletTexture;
        this.position = new Vector2(Player.getInstance().position.x + (Player.getInstance().texture.getWidth()/2) - this.texture.getWidth()/2 ,Player.getInstance().position.y + (Player.getInstance().texture.getHeight()/2) - this.texture.getHeight()/2);
        this.box2D = new Rectangle(this.position.x, this.position.y, this.Width(), this.Height());
        this.velocity = 600f;
        this.life = 1;
        this.direction = Player.getInstance().getDirection();

        angle = (float) Math
                .atan2(direction.y, direction.x);
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
                this.texture.getHeight() , false, true
        );

    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }
    public void update(float delta) {
        this.position.x += (float) Math.cos(angle) * this.velocity
                *delta;
        this.position.y += (float) Math.sin(angle) * this.velocity
                * delta;
        this.box2D.x = this.position.x;
        this.box2D.y = this.position.y;
        this.box2D.width = this.Width();
        this.box2D.height = this.Height();
    }
}
