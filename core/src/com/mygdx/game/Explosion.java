package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Explosion {

    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;

    private Rectangle boundingBox;

    Explosion(Texture texture, Rectangle boundingBox, float totalAnimationTime) {
        this.boundingBox = boundingBox;

        //split texture
        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, 256, 256);

        //convert to 1D array
        TextureRegion[] textureRegion1D = new TextureRegion[64];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                textureRegion1D[index] = textureRegion2D[i][j];
                index++;
            }
        }

        explosionAnimation = new Animation<TextureRegion>(totalAnimationTime/64, textureRegion1D);
        explosionTimer = 0;
    }

    public void update(float deltaTime) {
        explosionTimer += deltaTime;
    }

    public void draw (SpriteBatch batch) {
        batch.draw(explosionAnimation.getKeyFrame(explosionTimer),
                boundingBox.x - 20 ,
                boundingBox.y - 20 ,
                boundingBox.width + 70,
                boundingBox.height + 50);
    }

    public boolean isFinished() {
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }
}
