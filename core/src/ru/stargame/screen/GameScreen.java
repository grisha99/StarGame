package ru.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.base.BaseScreen;
import ru.stargame.base.BaseShip;
import ru.stargame.math.Rect;
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
    
    
    BaseShip playerShip;
    
//    public GameScreen(Game game) {
//        this.game = game;
//    }
    
    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        
        background = new Background(new TextureRegion(bg));
        playerShip = new PlayerShip(atlas, Gdx.graphics.getWidth() / 2f, 0, 0.01f);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
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
        super.dispose();
    }
    
    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }
    
    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }
    
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        playerShip.setEndPosition(touch);
        return false;
    }
    
    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }
    
    private void update(float delta) {
        playerShip.move();
        for (Star star : stars) {
            star.update(delta);
        }
    }
    
    private void checkCollision() {
    
    }
    
    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        playerShip.draw(batch);
        batch.end();
    }
}
