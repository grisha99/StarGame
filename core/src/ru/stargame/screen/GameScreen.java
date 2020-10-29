package ru.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.base.BaseScreen;
import ru.stargame.base.BaseShip;
import ru.stargame.math.Rect;
import ru.stargame.pool.BulletPool;
import ru.stargame.ships.PlayerShip;
import ru.stargame.sprite.Background;
import ru.stargame.sprite.Star;

public class GameScreen extends BaseScreen {
    
    private static final int STAR_COUNT = 64;
    
    private Game game;
    
    private TextureAtlas atlas;
    private Texture bg;
    
    private Background background;
    private Star[] stars;
    
    private BulletPool bulletPool;
    private PlayerShip playerShip;
    
    private Music gameMusic;
    
//    public GameScreen(Game game) {
//        this.game = game;
//    }
    
    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        
        background = new Background(new TextureRegion(bg));
        bulletPool = new BulletPool();
//        mainShip = new MainShip(atlas, bulletPool);
        playerShip = new PlayerShip(atlas, bulletPool);
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
        gameMusic.dispose();
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
        playerShip.update(delta);
    }
    
    private void checkCollision() {
    
    }
    
    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        playerShip.draw(batch);
        batch.end();
    }
    
    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
    }
}
