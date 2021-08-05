package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;


public class MainMenuScreen implements Screen {

    private ScrollingBackground parallaxBackground;
    private SpriteBatch batch;

    //
    private  int EXIT_BUTTON_WIDTH ;
    private  int PLAY_BUTTON_WIDTH ;
    private  int EXIT_BUTTON_HEIGHT ;
    private  int PLAY_BUTTON_HEIGHT ;
    private final int WORLD_WIDTH = 1280;;
    private final int WORLD_HEIGHT = 720;;

    private Vector2 EXIT_BUTTON_POSITION;
    private Vector2 PLAY_BUTTON_POSITION;
    //Game
    MainScreen game;
    //title
    private String title;
    //
    private boolean isGameOver;
    private  int score;


    private int xHotspot, yHotspot;
    private Cursor cursor;
    private Pixmap pixmap;

    public MainMenuScreen(MainScreen game, String title, int score){
        pixmap = Art.cursorTexture;
        xHotspot = pixmap.getWidth() / 2;
        yHotspot = pixmap.getHeight() / 2;
        cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
        Gdx.graphics.setCursor(cursor);


        this.score = score;
        this.isGameOver = score != -1 ;
        parallaxBackground = ScrollingBackground.getInstance();
        parallaxBackground.setSpeed(0.5f);
        Sounds.menuMusic.play();
        Sounds.menuMusic.setVolume(Config.volume);
        Sounds.menuMusic.setLooping(true);
        this.game = game;
        this.title = title;
        batch = new SpriteBatch();

        //
        EXIT_BUTTON_WIDTH = Art.exitButtonTexture.getWidth();
        PLAY_BUTTON_WIDTH = Art.playButtonTexture.getWidth();
        EXIT_BUTTON_HEIGHT = Art.exitButtonTexture.getHeight();
        PLAY_BUTTON_HEIGHT = Art.playButtonTexture.getHeight();

        //
        PLAY_BUTTON_POSITION = new Vector2((WORLD_WIDTH/2f) - PLAY_BUTTON_WIDTH/2f , WORLD_HEIGHT- 300f) ;
        EXIT_BUTTON_POSITION = new Vector2((WORLD_WIDTH/2f) -EXIT_BUTTON_WIDTH/2f,   WORLD_HEIGHT- 450f)  ;
        //

        //
        prepareHeaderFont();
        prepareFooterFont();
        prepareCommandfont();

    }





    private void drawHeader(){
        Art.fontHeader.draw(batch, title, 500, 650, 300, Align.center, false);
    }


    private void drawCommands()
    {
        Art.commandFontKeys.draw(batch, "Q  :", 250, 400, 100, Align.left, false);
        Art.commandFont.draw(batch, "Move Left", 285, 400, 100, Align.left, false);
        Art.commandFontKeys.draw(batch, "S  :", 250, 360, 100, Align.left, false);
        Art.commandFont.draw(batch, "Move Down", 285, 360, 100, Align.left, false);
        Art.commandFontKeys.draw(batch, "D  :", 250, 320, 100, Align.left, false);
        Art.commandFont.draw(batch, "Move Right", 285, 320, 100, Align.left, false);
        Art.commandFontKeys.draw(batch, "Z  :", 250, 280, 100, Align.left, false);
        Art.commandFont.draw(batch, "Move Up", 285, 280, 100, Align.left, false);


        Art.commandFontKeys.draw(batch, "P  :", 850, 400, 100, Align.left, false);
        Art.commandFont.draw(batch, "Pause / Resume Game", 885, 400, 100, Align.left, false);
        Art.commandFontKeys.draw(batch, "Left Mouse Button  :", 850,360 , 100, Align.left, false);
        Art.commandFont.draw(batch, "Shoot Lasers", 1100,360 , 100, Align.left, false);

        Art.commandFontKeys.draw(batch, "Key Up  :", 850,320 , 100, Align.left, false);
        Art.commandFont.draw(batch, "Volume Up", 950,320 , 100, Align.left, false);

        Art.commandFontKeys.draw(batch, "Key Down  :", 850,280 , 100, Align.left, false);
        Art.commandFont.draw(batch, "Volume Down", 980,280 , 100, Align.left, false);

    }


    private void drawScore(Integer score){
        Art.fontFooter.draw(batch, "Score :", 50, 700, 300, Align.left, false);
        Art.fontFooter.draw(batch, score.toString(), 150, 700, 300, Align.left, false);
    }


    private void prepareCommandfont(){
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/galaxy.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 25;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.5f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);
        Art.commandFont = fontGenerator.generateFont(fontParameter);

        fontParameter.size = 25;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.5f);
        fontParameter.borderColor = new Color(1, 1, 1, 0.3f);
        Art.commandFontKeys = fontGenerator.generateFont(fontParameter);
    }


    private void prepareHeaderFont(){
        //Create a BitmapFont from our font file
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 0, 0, 0.6f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);
        Art.fontHeader = Art.fontGenerator.generateFont(fontParameter);
    }
    private void prepareFooterFont(){
        //Create a BitmapFont from our font file
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 26;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.5f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);
        Art.fontFooter = Art.fontGenerator.generateFont(fontParameter);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float PX1 = PLAY_BUTTON_POSITION.x;
        float PX2 =  PLAY_BUTTON_POSITION.x + PLAY_BUTTON_WIDTH;
        float PY1 = WORLD_HEIGHT - PLAY_BUTTON_POSITION.y;
        float PY2 = WORLD_HEIGHT-PLAY_BUTTON_POSITION.y - PLAY_BUTTON_HEIGHT ;
        float EX1 = EXIT_BUTTON_POSITION.x;
        float EX2 =  EXIT_BUTTON_POSITION.x +EXIT_BUTTON_WIDTH;
        float EY1 = PLAY_BUTTON_POSITION.y;
        float EY2 = WORLD_HEIGHT-EXIT_BUTTON_POSITION.y - EXIT_BUTTON_HEIGHT ;
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        parallaxBackground.draw(batch);
        parallaxBackground.update();
        handleVolume(delta);
        drawHeader();
        drawCommands();
        if(isGameOver){
            drawScore(this.score);
        }

            if ((Gdx.input.getX() >= PX1 && Gdx.input.getX() <= PX2) && ((Gdx.input.getY() <= PY1 && Gdx.input.getY() >= PY2))) {

                batch.draw(Art.playButtonActiveTexture, PLAY_BUTTON_POSITION.x, PLAY_BUTTON_POSITION.y);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    Sounds.menuMusic.stop();
                    game.setScreen(new GameScreen(game));

                }
            } else {
                batch.draw(Art.playButtonTexture, PLAY_BUTTON_POSITION.x, PLAY_BUTTON_POSITION.y);
            }

            if ((Gdx.input.getX() >= EX1 && Gdx.input.getX() <= EX2) && ((Gdx.input.getY() <= EY1 && Gdx.input.getY() >= EY2)) ) {
                batch.draw(Art.exitButtonActiveTexture, EXIT_BUTTON_POSITION.x, EXIT_BUTTON_POSITION.y);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    Gdx.app.exit();
                }
            } else {
                batch.draw(Art.exitButtonTexture, EXIT_BUTTON_POSITION.x, EXIT_BUTTON_POSITION.y);
            }
        batch.end();
    }

    private void handleVolume(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                Config.volume-= delta;
                if(Config.volume < 0)
                    Config.volume = 0f;
            Sounds.menuMusic.setVolume(Config.volume);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            Config.volume+= delta;
                if(Config.volume > 1)
                    Config.volume = 1f;
            Sounds.menuMusic.setVolume(Config.volume);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
