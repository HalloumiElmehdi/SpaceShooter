package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.game.Config;
import com.mygdx.game.effects.EffectManager;
import com.mygdx.game.entities.enemies.Asteroid;
import com.mygdx.game.entities.enemies.Criminal;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.enemies.Tricky;
import com.mygdx.game.entities.powerups.Growth;
import com.mygdx.game.entities.powerups.PowerUp;
import com.mygdx.game.entities.powerups.Speed;
import com.mygdx.game.ressources.Sounds;
import com.mygdx.game.screens.game.GameScreen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public  class EntityManager {
    public  static ConcurrentLinkedQueue<Enemy> enemies = new ConcurrentLinkedQueue<>();
    public  static ConcurrentLinkedQueue<Bullet> bullets = new ConcurrentLinkedQueue<>();;
    public  static ConcurrentLinkedQueue<PowerUp> powerUps = new ConcurrentLinkedQueue<>();
    public  static Player player = Player.getInstance();

    //for all spawns
    private static final Random spawnRandom = new Random();

    //for enemies spawn
    public static short enemiesSpawnInterval = 100;
    public static short powerUpsSpawnInterval = 100;

    private static float  spawnInterval = 2f;

    private static void spawnEnemies(){
        if(spawnRandom.nextInt(enemiesSpawnInterval) == 0)
                createEnemy(Enemy.createCriminal());

        if(spawnRandom.nextInt(enemiesSpawnInterval) == 0)
                createEnemy(Enemy.createAsteroid());

        if(spawnRandom.nextInt(enemiesSpawnInterval) == 0)
                createEnemy(Enemy.createTricky());
    }

    public static void spawnPowerUps() {
        if(powerUps.size() < 1 ) {
            if(spawnRandom.nextInt(powerUpsSpawnInterval) == 0) {
                createPowerUp(Growth.create()); return;
            }

            if(spawnRandom.nextInt(powerUpsSpawnInterval) == 0) {
                createPowerUp(Speed.create());
                return;
            }
        }

    }

    public static void spawnEntities(float deltaTime){
        spawnEnemies();
        spawnPowerUps();
    }

    public static void createPowerUp(Entity entity) {
        if(entity instanceof Growth)
            powerUps.add((Growth)entity);
        if(entity instanceof Speed)
            powerUps.add((Speed)entity);
    }

    public static void createEnemy(Entity entity) {
        if(entity instanceof Bullet)
            bullets.add((Bullet)entity);

        if(entity instanceof Criminal)
            enemies.add((Criminal)entity);

        if(entity instanceof Tricky)
            enemies.add((Tricky)entity);

        if(entity instanceof Asteroid)
            enemies.add(((Asteroid)entity));

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
                bullets.add(Bullet.create());
                Sounds.shoot();
            }
        }
    }

    public static void handleExpiredBullets(){
        for(Bullet bullet :bullets) {
            if (bullet.position.y > Gdx.graphics.getHeight() || bullet.position.y < 0 || bullet.position.x > Gdx.graphics.getWidth() ||bullet.position.x < 0) {
                bullets.remove(bullet);
            }
        }

    }


    public static void handleGameOver() {
        EffectManager.gameOverExplosion();
        Sounds.playGameOverExplosion();
        bullets.clear();
        powerUps.clear();
        enemies.clear();
    }

    private static void playerEnemyCollision(){
        for(Enemy enemy : enemies) {
            if(player.isColliding(enemy.bounds)) {
                enemies.remove(enemy);
                player.life--;
                if(player.life > 0){
                    player.isHit = true;
                    Sounds.playCollisionExplosion();
                    EffectManager.createExplosion(enemy.bounds, 1.6f);
                    break;
                }
                if(player.life == 0){
                    handleGameOver();
                    break;
                }
            }
        }
    }

    private static void bulletEnemyCollision(){
        for(Bullet bullet : bullets)
            for(Enemy enemy : enemies)
                if(bullet.isColliding(enemy.bounds)) {
                    enemy.life = (enemy.life - Bullet.power) > 0 ? (int) (enemy.life - Bullet.power) : 0;
                    enemy.isHit = true;
                    bullets.remove(bullet);
                    if (enemy.life == 0) {
                        enemies.remove(enemy);
                        EffectManager.createExplosion(enemy.bounds, 1.5f);
                        Sounds.playCollisionExplosion();
                        break;
                    }
                }
    }

    private static void playerPowerUpCollision(){
        for(PowerUp powerUp : powerUps) {
            if(player.isColliding(powerUp.bounds)) {
                Sounds.PlayPowerUpSound();
                powerUps.remove(powerUp);
                powerUp.feature();
                break;
            }
        }

    }

    public static void enemyEnemyCollision() {
        for(Enemy enemy : enemies)
            for(Enemy other : enemies)
                if(enemy != other)
                    if(enemy.isColliding(other.bounds)) {
                        enemies.remove(enemy);
                        enemies.remove(other);
                        EffectManager.createExplosion(enemy.bounds,1.6f);
                        EffectManager.createExplosion(other.bounds,1.6f);
                        Sounds.playCollisionExplosion();
                        break;
                    }
    }

    public static void handleCollision(){
        bulletEnemyCollision();
        playerEnemyCollision();
        playerPowerUpCollision();
        enemyEnemyCollision();
    }
}
