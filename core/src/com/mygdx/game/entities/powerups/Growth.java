package com.mygdx.game.entities.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.Player;
import com.mygdx.game.screens.game.HUD;
import com.mygdx.game.ressources.Art;

public class Growth extends PowerUp{
    public Growth(){
        super(Art.powerUpTexture,
                new Vector2(),
                0f,
                1
        );
    }
    public void update(float delta){
        super.update(delta);
    }
    public void feature(){
        HUD.countdown = 10;
        Player.getInstance().life++;
        Player.getInstance().isBig = true;
        Bullet.power = 2f;
    }

    public static Growth create() {
        return new Growth();
    }
}
