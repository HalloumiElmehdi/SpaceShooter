package com.mygdx.game.entities.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Player;
import com.mygdx.game.screens.game.HUD;
import com.mygdx.game.ressources.Art;

public class Speed extends PowerUp{
    public Speed(){
        super(Art.powerUpSpeedTexture,
                new Vector2(),
                0f,
                1
        );
    }
    public void feature(){
        HUD.countdownSpeed = 30;
        Player.getInstance().hasSpeed = true;
        Player.getInstance().velocity *= 2;
    }

    public static Speed create() {
        return new Speed();
    }

}
