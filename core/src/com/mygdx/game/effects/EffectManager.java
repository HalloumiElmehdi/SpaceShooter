package com.mygdx.game.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.powerups.PowerUp;
import com.mygdx.game.ressources.Art;

import java.util.ArrayList;
import java.util.ListIterator;

public class EffectManager {
    public  static Player player = Player.getInstance();
    public static ArrayList<Explosion> explosions =  new ArrayList<>();
    // for hit animation
    static int animationTimeCounter;

    public static void createExplosion(Rectangle enemyBounds, float totalAnimationTime){
        explosions.add(new Explosion(Art.explosionTexture, enemyBounds,1.5f));
    }

    public static void updateAndDrawExplosions(float deltaTime, SpriteBatch batch) {
        ListIterator<Explosion> explosionListIterator = explosions.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()) {
                explosionListIterator.remove();
            } else {
                explosion.draw(batch);
            }
        }
    }

    private void drawHitAnimation(float deltaTime, Batch batch){
        if(player.isHit){
            batch.draw(Art.hitAnimationTexture,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            animationTimeCounter+= deltaTime;
            float hitAnimationEnd = 2f;
            if(animationTimeCounter - hitAnimationEnd > 0){
                player.isHit= false;
                animationTimeCounter = 0;
            }
        }
    }
    public static void gameOverExplosion() {

        EffectManager.createExplosion(new Rectangle(player.bounds.x - player.Width(), player.bounds.y - player.Height(), 256, 256), 2f);

        for (Enemy enemy: EntityManager.enemies)
            EffectManager.createExplosion(enemy.bounds, 1.6f);

        for(PowerUp powerUp : EntityManager.powerUps)
            EffectManager.createExplosion(powerUp.bounds, 1.6f);

    }
}
