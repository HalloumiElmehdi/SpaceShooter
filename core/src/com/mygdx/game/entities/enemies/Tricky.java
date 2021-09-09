package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.ressources.Art;
import com.mygdx.game.entities.Player;

public class Tricky extends Enemy {
    public Tricky()
    {
        super(Art.trickyTexture, 100f, 5);
    }

    public void update(float delta) {
        super.update(delta);
        followPlayer(delta);
    }
    public void followPlayer(float delta){
        //increase enemy velocity when is near to player
        float ab = Math.abs(Player.getInstance().position.y - this.position.y);
        float bc = Math.abs(Player.getInstance().position.x - this.position.x);
        double ac2 = Math.pow(ab, 2) + Math.pow(bc, 2);
        double ac = Math.sqrt(ac2);
        if(ac <= 400f) velocity = 400f;
        else velocity = 50;
        //go to player
        super.followPlayer(delta);
    }



    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        drawHitEffect(batch, Art.trickyHitBoxTexture);
        drawEnemyLives(batch);
    }

}
