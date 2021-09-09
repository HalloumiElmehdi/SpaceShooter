package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.ressources.Art;

public class Criminal extends Enemy{
        public Criminal(){
                super(Art.criminalTexture, 100f, 10);
        }

        public void update(float delta) {
                super.update(delta);
                followPlayer(delta);
        }

        public void draw(Batch batch) {
                super.draw(batch);
                drawHitEffect(batch, Art.criminalHitBoxTexture);
                drawEnemyLives(batch);
        }

}
