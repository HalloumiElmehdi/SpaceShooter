package com.mygdx.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.utils.Art;
import com.mygdx.game.Config;
import com.mygdx.game.entities.Player;

import java.util.Locale;

public class HUD {
    private BitmapFont font;
    private BitmapFont fontTime;

    private float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

    private float seconds = 0;
    public static byte countdown;

    private float powerUpSpeedSeconds;
    public static byte countdownSpeed ;


    public HUD()
    {
        this.font = new BitmapFont();
        this.fontTime  = new BitmapFont();
        //Create a BitmapFont from our font file
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);
        font = Art.fontGenerator.generateFont(fontParameter);
        //scale the font to fit world
        font.getData().setScale(0.5f);
        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        // fontParameter.color = new Color(1, 0, 0, 1f);
        fontParameter.color.set(16f / 255f, 232f / 255f, 15f / 255f, 1);
        fontParameter.borderColor = new Color(1, 1, 1, 0.3f);
        fontTime = Art.fontGenerator.generateFont(fontParameter);
        //calculate hud margins, etc.
        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = Config.world_width * 2 / 3 - hudLeftX;
        hudCentreX = Config.world_width / 3;
        hudRow1Y = Config.world_height - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = Config.world_width / 3;
    }

    public void draw(Batch batch, int level, float deltaTime) {
        //render top row labels
        font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Level", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "Shield", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%06d", Player.getInstance().score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", level), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", Player.getInstance().life), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
        if(Player.getInstance().isBig) {
            fontTime.draw(batch, String.format(Locale.getDefault(), "%02d", (int)countdown), Gdx.graphics.getWidth() - 600, hudRow1Y , hudSectionWidth, Align.right, false);
        }
        if(Player.getInstance().hasSpeed) {
            fontTime.draw(batch, String.format(Locale.getDefault(), "%02d", (int)countdownSpeed), Gdx.graphics.getWidth() - 700, hudRow1Y , hudSectionWidth, Align.right, false);
        }



    }

    public void update(float deltaTime){
        //countdown power up update
        if(Player.getInstance().isBig) {
            if(countdown == 0){
                Player.getInstance().isBig = false;
                countdown = 10;
            }
            seconds += deltaTime;
            if((int)seconds == 1){
                countdown--;
                seconds = 0;
            }
        }
        //countdown power up speed update
        if(Player.getInstance().hasSpeed) {
            if(countdownSpeed == 0){
                Player.getInstance().hasSpeed = false;
                countdownSpeed = 30;
            }
            powerUpSpeedSeconds += deltaTime;
            if((int)powerUpSpeedSeconds == 1){
                countdownSpeed--;
                powerUpSpeedSeconds = 0;
            }
        }
    }
}
