package ru.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.stargame.base.BaseScreen;
import ru.stargame.math.Rect;
import ru.stargame.pool.BulletPool;
import ru.stargame.pool.EnemyShipPool;
import ru.stargame.pool.ExplosionPool;
import ru.stargame.ships.EnemyShip;
import ru.stargame.ships.PlayerShip;
import ru.stargame.sprite.Background;
import ru.stargame.sprite.Bullet;
import ru.stargame.sprite.Explosion;
import ru.stargame.sprite.Star;
import ru.stargame.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    
    private static final int STAR_COUNT = 64;
    
    private Game game;
    
    private TextureAtlas atlas;
    private Texture bg;

    private Sound enemyBulletSound;
    private Sound explosionSound;
    
    private Background background;
    private Star[] stars;
    
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyShipPool enemyShipPool;
    private EnemyEmitter enemyEmitter;
    
    private PlayerShip playerShip;
    
    private Music gameMusic;
    
    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound((Gdx.files.internal("sounds/explosion.wav")));
        
        background = new Background(new TextureRegion(bg));
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, enemyBulletSound, atlas);
    
        this.playerShip = new PlayerShip(atlas, bulletPool, explosionPool);
        
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        gameMusic.play();
        gameMusic.setLooping(true);
        gameMusic.setVolume(1.5f);
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }
    
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }
    
    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
        enemyBulletSound.dispose();
        explosionSound.dispose();
        gameMusic.dispose();
        playerShip.dispose();
        super.dispose();
    }
    
    @Override
    public boolean keyDown(int keycode) {
        playerShip.keyDown(keycode);
        return false;
    }
    
    @Override
    public boolean keyUp(int keycode) {
        playerShip.keyUp(keycode);
        return false;
    }
    
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
//        playerShip.setEndPosition(touch);
        playerShip.touchDown(touch, pointer, button);
        return false;
    }
    
    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        playerShip.touchUp(touch, pointer, button);
        return false;
    }
    
    private void update(float delta) {
//        playerShip.move();
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
        playerShip.update(delta);
    }
    
    private void checkCollision() {
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = playerShip.getHalfWidth() + enemyShip.getHalfWidth();
            if (enemyShip.pos.dst(playerShip.pos) < minDist) {
                enemyShip.destroy();
                playerShip.damage(enemyShip.getDamage());
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != playerShip) {
                if (playerShip.isBulletCollision(bullet)) {
                    playerShip.damage(bullet.getDamage());
                    bullet.destroy();
                    return;
                }
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    return;
                }
            }
        }
    
    }
    
    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        playerShip.draw(batch);
        enemyShipPool.drawActiveSprites(batch);
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }
    
    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }
}
