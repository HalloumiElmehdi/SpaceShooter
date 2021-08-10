package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

class GameScreen implements Screen {
    //graphics
    private final SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont fontTime;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;
    // Cursor
    private  boolean isHit = false;
    private float bulletHit ;
    private boolean isBig = false;

    //world parameters
    private final int WORLD_WIDTH = 1280;
    private final int WORLD_HEIGHT = 720;

    private final Player player;
    private final ScrollingBackground parallaxBackground;

    // inverse span count
    private static int level;
    private boolean win = false;

    // enemy spawn interval
    private float spawnInterval = 2f;
    private float lastTimeSpawn;

   // private ShaderProgram shader;
    private boolean isPause = false;
    private boolean gameOver = false;
    private boolean blink = false ;

    private float blinkTimeCounter;
    // Enemy list
    LinkedList<Enemy> enemyList;
    LinkedList<Bullet> bulletList;
    LinkedList<Explosion> explosionList;
    LinkedList<Asteroid> asteroidList;
    LinkedList<PowerUp> powerUpList;

    private float resetTime;
    private final float resetFrameTime = 4f;
    private final float hitAnimationEnd = 2f;
    private float animationTimeCounter = 0f;
    private final float blinkTimePause = 0.5f;

    MainScreen game;

    //constructor
    GameScreen(MainScreen game){
        this.game = game;
        player = Player.getInstance();
        font = new BitmapFont();
        fontTime  = new BitmapFont();
        level = 1;
        parallaxBackground = ScrollingBackground.getInstance();
        parallaxBackground.setSpeed(0.5f);

        Sounds.gameMusic.setVolume(Config.volume);
        Sounds.gameMusic.setLooping(true);
        bulletHit = 1f;
        // enemy list instantiation
        explosionList = new LinkedList<>();
        enemyList = new LinkedList<>();
        bulletList = new LinkedList<>();
        asteroidList = new LinkedList<>();
        powerUpList = new LinkedList<>();

        prepareRadar();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();
        parallaxBackground.draw(batch);
        parallaxBackground.update();
        updateAndRenderExplosions(deltaTime);
        handleVolume(deltaTime);
        if(!gameOver){
           pauseResumeGame();
           player.draw(batch);
           drawBullets();
           drawEnemies();
           drawBullets();
           drawPowerUps();
           if(isHit){
               batch.draw(Art.hitAnimationTexture,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
               animationTimeCounter+= deltaTime;
                if(animationTimeCounter - hitAnimationEnd > 0){
                    isHit= false;
                    animationTimeCounter = 0;
                }
           }
           updateAndRenderHUD();

           if(!isPause){
               Sounds.gameMusic.play();
               updateLevel();
               lastTimeSpawn += deltaTime;
               player.update(deltaTime);
               spawnEnemies();
               spawnPowerUps(deltaTime);
               fireBullets();
               IsBigTimeline(deltaTime);
               updateEnemies(deltaTime);
               updateBullets(deltaTime);
               updatePowerUps(deltaTime);
               handleExpiredBullets();
               handleCollision();
           }else
           {
               blinkTimeCounter+= deltaTime;
               if(blinkTimeCounter - blinkTimePause > 0){
                   blinkTimeCounter = 0;
                   blink = !blink;
               }
               if(blink){
                   batch.draw(Art.pauseBtnBlueTexture, Gdx.graphics.getWidth()/2 - (Art.pauseBtnBlueTexture.getWidth()/2), Gdx.graphics.getHeight()/2, Art.pauseBtnBlueTexture.getWidth(), Art.pauseBtnBlueTexture.getHeight());
               }
               else{
                   batch.draw(Art.pauseBtnRedTexture, Gdx.graphics.getWidth()/2 - (Art.pauseBtnRedTexture.getWidth()/2), Gdx.graphics.getHeight()/2, Art.pauseBtnRedTexture.getWidth(), Art.pauseBtnRedTexture.getHeight());
               }

               Sounds.gameMusic.pause();

           }
       }
        if(gameOver){
        batch.draw(Art.hitAnimationTexture,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
           resetTime+= deltaTime;
           if( resetTime - resetFrameTime > 0){
               game.setScreen(new MainMenuScreen(game, "GAME OVER",player.score));
           }
       }
        batch.end();
    }


    private void updateLevel(){
        if(player.score == 0){
            level = 0;
            Enemy.speed = 150f;
            player.velocity = 220f;
        }

        if(player.score == 10000){
            level = 1;
            Enemy.speed = 150f;
            player.velocity = 220f;

        }
        if(player.score == 20000) {
            level = 2;
            Enemy.speed = 175f;
            player.velocity = 250f;
            spawnInterval= 1.3f;
        }
        if(player.score == 30000){
            level = 3;
            Enemy.speed = 200f;
            player.velocity = 270f;
            spawnInterval= 1.2f;
        }
        if(player.score == 40000){
            level = 4;
            Enemy.speed = 225f;
            player.velocity = 290f;
            spawnInterval= 1.1f;
        }
        if(player.score == 50000){
            level = 5;
            Enemy.speed = 250f;
            player.velocity = 250f;
            spawnInterval= 1f;
        }
        if(player.score == 60000){
            level = 6;
            Enemy.speed = 275f;
            player.velocity = 320f;
            spawnInterval=0.9f;
        }
        if(player.score == 70000){
            level = 7;
            Enemy.speed = 300f;
            player.velocity = 340f;
            spawnInterval= 0.8f;
        }
        if(player.score == 80000){
            level =  8;
            Enemy.speed = 325f;
            player.velocity = 360f;
            spawnInterval=0.7f;

        }
        if(player.score == 90000){
            level = 9;
            Enemy.speed = 350f;
            player.velocity = 380f;
            spawnInterval=0.6f;
        }
        if(player.score == 100000 && !win){
            win = true;
            level = 10;
            Enemy.speed = 380;
            player.velocity = 400f;
            spawnInterval= 0.5f;
        }

    }
    private void prepareRadar(){
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
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;

    }
    private void updateAndRenderHUD() {
        //render top row labels
        font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Level", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "Shield", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%06d", player.score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", level), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", player.life), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
        if(isBig) {
            float temp = 0f ;
            if(Math.round(isBigCounter) == 0)
                    temp = 10;
            if(Math.round(isBigCounter) == 1)
                temp = 9;
            if(Math.round(isBigCounter) == 2)
                temp = 8;
            if(Math.round(isBigCounter) == 3)
                temp = 7;
            if(Math.round(isBigCounter) == 4)
                temp = 6;
            if(Math.round(isBigCounter) == 5)
                temp = 5;
            if(Math.round(isBigCounter) == 6)
                temp = 4;
            if(Math.round(isBigCounter) == 7)
                temp = 3;
            if(Math.round(isBigCounter) == 8)
                temp = 2;
            if(Math.round(isBigCounter) == 9)
                temp = 1;
            if(Math.round(isBigCounter) == 10)
                temp = 0;
            fontTime.draw(batch, String.format(Locale.getDefault(), "%02d", (int)temp), Gdx.graphics.getWidth() - 600, hudRow1Y , hudSectionWidth, Align.right, false);
        }
    }


    private void fireBullets(){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if(player.canShoot){
                Bullet bullet = new Bullet();
                if(isBig)
                  bullet.setTexture(Art.bigBulletTexture);
                bulletList.add(bullet);
                long id = Sounds.laserSound.play(Config.volume);
                Sounds.laserSound.setPitch(id, 1);
                Sounds.laserSound.setLooping(id, false);
            }
        }
    }


    private float inverseSpawnChanceCounter ;

    //spawn, draw, update power ups
    private void spawnPowerUps(float delta){
        float inverseSpawnChance = 20f;
        inverseSpawnChanceCounter+= delta;
      if(inverseSpawnChanceCounter - inverseSpawnChance > 0){
          if(powerUpList.size() < 1)
          { PowerUp powerUp = new PowerUp();
          powerUpList.add(powerUp);
          inverseSpawnChanceCounter = 0f;}
      }
    }
    private void drawPowerUps(){
        for(PowerUp powerUp : powerUpList){
            powerUp.draw(batch);
        }
    }
    private void updatePowerUps(float delta){
        for(PowerUp powerUp : powerUpList){
            powerUp.update(delta);
        }
    }

    //spawn, draw, update enemies
    private void spawnEnemies(){
        if(lastTimeSpawn -  spawnInterval >= 0 ){
            if(enemyList.size() < 2){
                Enemy enemy = new Enemy();
                if(asteroidList.size() <= 30){
                    Asteroid asteroid  = new Asteroid();
                    asteroidList.add(asteroid);
                }
                enemyList.add(enemy);
                lastTimeSpawn = 0;
            }
        }
    }

    private void drawEnemies(){
        for(Enemy enemy : enemyList){
            enemy.draw(batch);
        }

        for(Asteroid asteroid : asteroidList){
            asteroid.draw(batch);
        }
    }

    private void updateEnemies(float delta){
        for(Enemy enemy : enemyList){
            enemy.update(delta);
        }
        for(Asteroid asteroid : asteroidList){
            asteroid.update(delta);
        }
    }



    //draw, update bullets
    private void drawBullets(){
        for(Bullet bullet : bulletList){
            bullet.draw(batch);

        }
    }
    private void updateBullets(float delta){
        for(Bullet bullet : bulletList){
            bullet.update(delta);

        }
    }

    //handle game collision
    private void handleCollision(){
        ListIterator<Bullet> bulletListIterator = bulletList.listIterator();
        while (bulletListIterator.hasNext()) {
            Bullet bullet = bulletListIterator.next();
            ListIterator<Enemy> enemyListIterator = enemyList.listIterator();
            while (enemyListIterator.hasNext()) {
                Enemy enemy = enemyListIterator.next();

                if (enemy.isColliding(bullet.getBox2D())) {
                    int tempLife = enemy.life;
                    tempLife -= bulletHit;
                    if(tempLife < 0)
                        tempLife = 0;
                    enemy.life = tempLife;
                    enemy.isHit = true;
                    bulletListIterator.remove();
                    if(enemy.life == 0){
                        enemyListIterator.remove();
                        explosionList.add(
                                new Explosion(Art.explosionTexture,
                                        new Rectangle(enemy.box2D),
                                        1.5f));
                        long id = Sounds.explosionSound.play(Config.volume);
                        Sounds.explosionSound.setPitch(id, 1f);
                        Sounds.explosionSound.setLooping(id, false);
                        player.score+= 1000;
                    }


                    break;
                }
            }
        }

        bulletListIterator = bulletList.listIterator();
        while (bulletListIterator.hasNext()) {
            Bullet bullet = bulletListIterator.next();
            ListIterator<Asteroid> asteroidListIterator = asteroidList.listIterator();
            while (asteroidListIterator.hasNext()) {
                Asteroid asteroid = asteroidListIterator.next();
                if (asteroid.isColliding(bullet.getBox2D())) {
                    asteroidListIterator.remove();
                    bulletListIterator.remove();
                    explosionList.add(
                            new Explosion(Art.explosionTexture,
                                    new Rectangle(asteroid.box2D),
                                    1.5f));
                    long id = Sounds.explosionSound.play(Config.volume);
                    Sounds.explosionSound.setPitch(id, 1f);
                    Sounds.explosionSound.setLooping(id, false);
                    player.score+= 1000;
                    break;
                }
            }
        }


        ListIterator<Asteroid> asteroidListIterator = asteroidList.listIterator();
        while (asteroidListIterator.hasNext()) {
            Asteroid asteroid = asteroidListIterator.next();
            if(asteroid.isColliding(player.getBox2D())){
                asteroidListIterator.remove();
                if(player.life > 0){
                    explosionList.add(new Explosion(Art.explosionTexture,
                            new Rectangle(asteroid.box2D),
                            1.5f));
                    player.life--;
                    long id = Sounds.explosionSound.play(Config.volume);
                    Sounds.explosionSound.setPitch(id, 1f);
                    Sounds.explosionSound.setLooping(id, false);
                    isHit = true;
                }
                if(player.life == 0){
                    gameOverExplosion();
                    long id = Sounds.explosionSound.play(Config.volume);
                    Sounds.explosionSound.setPitch(id, 0.8f);
                    Sounds.explosionSound.setLooping(id, false);
                    gameOver = true;
                    long idGameOver = Sounds.gameOverSound.play(Config.volume);
                    Sounds.gameOverSound.setPitch(idGameOver, 0.8f);
                    Sounds.gameOverSound.setLooping(idGameOver, false);
                    Sounds.gameMusic.stop();
//
                }
                break;
            }
        }




        ListIterator<Enemy> enemyListIterator = enemyList.listIterator();
        while (enemyListIterator.hasNext()) {
            Enemy enemy = enemyListIterator.next();
            if(enemy.isColliding(player.getBox2D())){
                enemyListIterator.remove();
                if(player.life > 0){
                    explosionList.add(new Explosion(Art.explosionTexture,
                            new Rectangle(enemy.box2D),
                            1.6f));
                    player.life--;
                    long id = Sounds.explosionSound.play(Config.volume);
                    Sounds.explosionSound.setPitch(id, 1f);
                    Sounds.explosionSound.setLooping(id, false);
                    isHit = true;
                }
                if(player.life == 0){
                    gameOverExplosion();
                    long id = Sounds.explosionSound.play(Config.volume);
                    Sounds.explosionSound.setPitch(id, 0.8f);
                    Sounds.explosionSound.setLooping(id, false);
                    gameOver = true;
                    long idGameOver = Sounds.gameOverSound.play(Config.volume);
                    Sounds.gameOverSound.setPitch(idGameOver, 0.8f);
                    Sounds.gameOverSound.setLooping(idGameOver, false);
                    Sounds.gameMusic.stop();
//
                }
                break;
            }
        }
        enemyListIterator = enemyList.listIterator();
        while(enemyListIterator.hasNext()){
            Enemy enemy = enemyListIterator.next();
            asteroidListIterator = asteroidList.listIterator();
            while (asteroidListIterator.hasNext()) {
                Asteroid asteroid = asteroidListIterator.next();
                if(enemy.isColliding(asteroid.box2D)){
                    enemyListIterator.remove();
                    asteroidListIterator.remove();
                    long id = Sounds.explosionSound.play(Config.volume);
                    Sounds.explosionSound.setPitch(id, 1f);
                    Sounds.explosionSound.setLooping(id, false);
                    explosionList.add(new Explosion(Art.explosionTexture,
                            new Rectangle(enemy.box2D),
                            1.6f));
                    explosionList.add(new Explosion(Art.explosionTexture,
                            new Rectangle(asteroid.box2D),
                            1.6f));
                               break;
                }
            }

        }


        ListIterator<PowerUp> powerUpListIterator = powerUpList.listIterator();
        while(powerUpListIterator.hasNext()){
            PowerUp powerUp = powerUpListIterator.next();
            if(powerUp.isColliding(player.getBox2D())){
                long idPowerUp = Sounds.powerUpSound.play(1f);
                Sounds.powerUpSound.setPitch(idPowerUp, 1f);
                Sounds.powerUpSound.setLooping(idPowerUp, false);
                powerUpListIterator.remove();
                player.life++;
                isBig = true;
                bulletHit = 2f;
                break;
            }
        }


        enemyListIterator = enemyList.listIterator();
        while(enemyListIterator.hasNext()){
            Enemy enemy  = enemyListIterator.next();
            enemyListIterator = enemyList.listIterator();
            while (enemyListIterator.hasNext()){
                Enemy enemyCollision = enemyListIterator.next();
                if(!enemy.equals(enemyCollision)){
                    if(enemy.isColliding(enemyCollision.box2D)){
                        enemyListIterator.remove();
                        if(enemy.life > 5)
                            enemy.life-= 5;
                        else
                            enemy.life = 1;
                        long id = Sounds.explosionSound.play(Config.volume);
                        Sounds.explosionSound.setPitch(id, 1f);
                        Sounds.explosionSound.setLooping(id, false);
                        explosionList.add(new Explosion(Art.explosionTexture,
                                new Rectangle(enemyCollision.box2D),
                                1.6f));
                    }
                }
            }


        }
    }

    private float isBigCounter;

    private void IsBigTimeline(float delta){
        float isBigTimeLapse = 10f;
        if(isBig){
            player.setTexture(Art.bigPlayerTexture);
            isBigCounter+= delta;
            if(isBigCounter - isBigTimeLapse > 0){
                isBig = false;
                isBigCounter = 0f;
                bulletHit = 1f;
                player.setTexture(Art.playerTexture);
            }
        }
    }
    private void updateAndRenderExplosions(float deltaTime) {
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
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


    private void handleExpiredBullets(){
        ListIterator<Bullet> bulletIterator = bulletList.listIterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (bullet.getPosition().y > WORLD_HEIGHT || bullet.getPosition().y < 0 || bullet.getPosition().x > WORLD_WIDTH ||bullet.getPosition().x < 0) {
                bulletIterator.remove();
            }
        }
    }

    private void gameOverExplosion(){
        bulletList.clear();
        ListIterator<Enemy> enemyListIterator = enemyList.listIterator();
        while (enemyListIterator.hasNext()) {
            Enemy enemy = enemyListIterator.next();
            explosionList.add(
                    new Explosion(Art.explosionTexture,
                            new Rectangle(enemy.box2D),
                            1.6f));
        }
        explosionList.add(
                new Explosion(Art.explosionTexture,
                        new Rectangle(player.box2D.x - player.Width() , player.box2D.y - player.Height() , 256 , 256),
                        2f));

    }
    private void pauseResumeGame(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            isPause = !isPause;
        }
    }

    private void handleVolume(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            Config.volume-= delta;
            if(Config.volume < 0)
                Config.volume = 0f;

            Sounds.gameMusic.setVolume(Config.volume);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            Config.volume+= delta;
            if(Config.volume > 1)
                Config.volume = 1f;
            Sounds.gameMusic.setVolume(Config.volume);
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

    }
    @Override
    public void show() {

    }
    @Override
    public void dispose() {

    }

}
