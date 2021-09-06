package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Config;
import com.mygdx.game.datastructure.CircularSinglyLinkedList;
import com.mygdx.game.datastructure.Node;
import com.mygdx.game.effects.EffectManager;
import com.mygdx.game.effects.Explosion;
import com.mygdx.game.entities.enemies.Asteroid;
import com.mygdx.game.entities.enemies.Criminal;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.enemies.Tricky;
import com.mygdx.game.entities.powerups.Growth;
import com.mygdx.game.entities.powerups.PowerUp;
import com.mygdx.game.entities.powerups.Speed;
import com.mygdx.game.screens.game.HUD;
import com.mygdx.game.utils.Art;
import com.mygdx.game.utils.Sounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public  class EntityManager {
    public  static ArrayList<Enemy> enemies = new ArrayList<>();
    public  static ArrayList<Bullet> bullets = new ArrayList<>();;
    public  static ArrayList<PowerUp> powerUps = new ArrayList<>();
    public  static Player player = Player.getInstance();
    //for all spawns
    private static final Random spawnRandom = new Random();
    //for powerUps spawn
    private  static float inverseSpawnChanceCounter;
    //for enemies spawn
    public static float lastTimeSpawn;
    private static float  spawnInterval = 2f;


    private static void createCriminal(){ enemies.add(new Criminal());}
    private static void createTricky(){ enemies.add(new Tricky()); }
    private static void createAsteroid(){ enemies.add(new Asteroid()); }
    private static void createBullet(){ bullets.add(new Bullet()); }
    private static void createGrowthPowerUp(){ powerUps.add(new Growth()); }
    private static void createSpeedPowerUp(){ powerUps.add(new Speed()); }
    private  static void spawnPowerUps(float delta){
        float inverseSpawnChance = 20f;
        inverseSpawnChanceCounter+= delta;
        if(inverseSpawnChanceCounter - inverseSpawnChance > 0){
            if(powerUps.size() < 1){
                if(spawnRandom.nextBoolean()) createGrowthPowerUp();
                else createSpeedPowerUp();
                inverseSpawnChanceCounter = 0f;
            }
        }
    }
    private static void spawnEnemies(float deltaTime){
        int number = spawnRandom.nextInt(3);
        lastTimeSpawn += deltaTime;
        if(lastTimeSpawn - spawnInterval >= 0 ){
                switch (number){
                    case 0: createCriminal();
                        break;
                    case 1: createTricky();
                        break;
                    case 2: createAsteroid();
                        break;
                }
                lastTimeSpawn = 0;

        }
    }
    public static void spawnEntities(float deltaTime){
        spawnPowerUps(deltaTime);
        spawnEnemies(deltaTime);
    }

    public static void updateLevel(){
        if(player.score == 0){
            player.level = 0;
            updateCriminalsVelocity(150f);
            player.velocity = 220f;
        }

        if(player.score == 10000){
            player.level = 1;
            updateCriminalsVelocity(150f);
            player.velocity = 220f;

        }
        if(player.score == 20000) {
            player.level = 2;
            updateCriminalsVelocity(175f);
            player.velocity = 250f;
            spawnInterval= 1.3f;
        }
        if(player.score == 30000){
            player.level = 3;
            updateCriminalsVelocity(200f);
            player.velocity = 270f;
            spawnInterval= 1.2f;
        }
        if(player.score == 40000){
            player.level = 4;
            updateCriminalsVelocity(225f);
            player.velocity = 290f;
            spawnInterval= 1.1f;
        }
        if(player.score == 50000){
            player.level = 5;
            updateCriminalsVelocity(250f);
            player.velocity = 250f;
            spawnInterval= 1f;
        }
        if(player.score == 60000){
            player.level = 6;
            updateCriminalsVelocity(275f);
            player.velocity = 320f;
            spawnInterval=0.9f;
        }
        if(player.score == 70000){
            player.level = 7;
            updateCriminalsVelocity(300f);
            player.velocity = 340f;
            spawnInterval= 0.8f;
        }
        if(player.score == 80000){
            player.level =  8;
            updateCriminalsVelocity(325f);
            player.velocity = 360f;
            spawnInterval=0.7f;

        }
        if(player.score == 90000){
            player.level = 9;
            updateCriminalsVelocity(350f);
            player.velocity = 380f;
            spawnInterval=0.6f;
        }
        if(player.score == 100000 && ! player.win){
            player.win = true;
            player.level = 10;
            updateCriminalsVelocity(380f);
            player.velocity = 400f;
            spawnInterval= 0.5f;
        }

    }
    private static void updateCriminalsVelocity(float velocity){
        for(Enemy enemy : enemies){
            if(enemy instanceof Criminal) enemy.velocity = velocity;
        }
    }
    public static  void updateEntities(float delta){
        player.update(delta);
        for(Enemy enemy : enemies) enemy.update(delta);
        for(Bullet bullet : bullets) bullet.update(delta);
        for(PowerUp powerUp : powerUps) powerUp.update(delta);
    }
    public static void drawEntities(Batch batch){
        player.draw(batch);
        for(Enemy enemy : enemies) enemy.draw(batch);
        for(Bullet bullet : bullets) bullet.draw(batch);
        for(PowerUp powerUp : powerUps) powerUp.draw(batch);
    }
    public static void handleBulletShoot(){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if(player.canShoot){
                createBullet();
                Sounds.shoot();
            }
        }
    }
    public static void handleExpiredBullets(){
        ListIterator<Bullet> bulletIterator = bullets.listIterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (bullet.position.y > Gdx.graphics.getHeight() || bullet.position.y < 0 || bullet.position.x > Gdx.graphics.getWidth() ||bullet.position.x < 0) {
                bulletIterator.remove();
            }
        }
    }
    private static void playerEnemyCollision(){
        ListIterator<Enemy> enemyListIterator = enemies.listIterator();
        while (enemyListIterator.hasNext()) {
            Enemy enemy = enemyListIterator.next();
            if(enemy.isColliding(player.bounds)){
                enemyListIterator.remove();
                if(player.life > 0){
                    EffectManager.createExplosion(enemy.bounds, 1.6f);
                    player.life--;
                    //sound
                    player.isHit = true;
                }
                if(player.life == 0){
                    EffectManager.gameOverExplosion();
                    Config.gameOver = true;
                    EffectManager.gameOverExplosion();
                }
                break;
            }
        }
    }
    private static void bulletEnemyCollision(){
        ListIterator<Enemy> enemyListIterator ;
        ListIterator<Bullet> bulletListIterator = bullets.listIterator();
        while (bulletListIterator.hasNext()) {
            Bullet bullet = bulletListIterator.next();
            enemyListIterator = enemies.listIterator();
            while (enemyListIterator.hasNext()) {
                Enemy enemy = enemyListIterator.next();
                if (enemy.isColliding(bullet.bounds)) {
                    enemy.life =  (enemy.life - Bullet.power) > 0 ? (int)(enemy.life - Bullet.power) : 0 ;
                    enemy.isHit = true;
                    bulletListIterator.remove();
                    if(enemy.life == 0){
                        enemyListIterator.remove();
                        EffectManager.createExplosion(enemy.bounds, 1.5f);
                        //sounds
                        player.score+= 1000;

                    }
                    break;
                }
            }
        }
    }
    private static void playerPowerUpCollision(){
        ListIterator<PowerUp> powerUpListIterator = powerUps.listIterator();
        while(powerUpListIterator.hasNext()){
            PowerUp powerUp = powerUpListIterator.next();
            if(powerUp.isColliding(player.bounds)){
                Sounds.powerUp();
                powerUpListIterator.remove();
                powerUp.feature();
                break;
            }
        }
    }
    public static void enemyEnemyCollision(){
        ListIterator<Enemy>  enemiesListIterator= enemies.listIterator();

        while(enemiesListIterator.hasNext()){
            Enemy enemy  = enemiesListIterator.next();
            enemiesListIterator = enemies.listIterator();
            while (enemiesListIterator.hasNext()){
                Enemy oppositeEnemy = enemiesListIterator.next();
                if(!enemy.equals(oppositeEnemy)){
                    if(enemy.isColliding(oppositeEnemy.bounds)){
                            enemies.remove(enemy);
                        if(enemy.life > 5)
                            enemy.life-= 5;
                        else
                            enemy.life = 1;
                        Sounds.collisionExplosion();
                        EffectManager.createExplosion(enemy.bounds,1.6f);
                    }
                }
            }
    }
    }
    public static void handleCollision(){
        bulletEnemyCollision();
        playerEnemyCollision();
        playerPowerUpCollision();
        enemyEnemyCollision();
    }



}
