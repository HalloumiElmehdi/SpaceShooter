package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    // attributes
    public Texture texture;
    public Vector2 position;
    public Rectangle bounds;
    public int life;
    public float velocity ;
    protected float rotationAngle;

    public Entity(Texture texture, Vector2 position, float velocity, int life){
        this.texture = texture;
        this.position = position;
        this.velocity = velocity;
        this.life = life;
        this.bounds =  new Rectangle(this.position.x, this.position.y, this.Width(), this.Height());
    }
    public int Width(){return this.texture.getWidth();}
    public int Height(){return this.texture.getHeight();}
    public Vector2 Position(){
        return position;
    }
    protected void updateRotationAngle(Vector2 targetPoint){
        Vector2 direction = targetPoint.sub(position).nor();
        rotationAngle = (float)(Math.atan2(direction.x, direction.y));
        rotationAngle = (float)Math.toDegrees(rotationAngle);
        rotationAngle *= -1;
    }
    public void update(Vector2 targetPoint){
        updateBounds();
        updateRotationAngle(targetPoint);
    }
    protected void updateBounds(){
        this.bounds = new Rectangle(this.position.x , this.position.y , this.Width(), this.Height());
    }
    public boolean isColliding(Rectangle rec){
        return this.bounds.overlaps(rec);
    }

    protected abstract void draw(Batch batch);
    protected abstract void generateSpawnPosition();


}
