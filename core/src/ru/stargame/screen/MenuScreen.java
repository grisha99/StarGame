package ru.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.base.BaseScreen;
import ru.stargame.math.Rect;
import ru.stargame.ships.ExitButton;
import ru.stargame.ships.PlayButton;
import ru.stargame.sprite.Background;
import ru.stargame.sprite.Star;

public class MenuScreen extends BaseScreen {
    
    private static final int STAR_COUNT = 32;
    
    private Game game;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private ExitButton exitButton;
    private PlayButton playButton;
    
    private Star[] stars;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bgH.jpg");
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        background = new Background(new TextureRegion(bg));
    
        exitButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, game);
    
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }
    
    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
    }
    
    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        exitButton.draw(batch);
        playButton.draw(batch);
        batch.end();
    }
    
    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
 //       playerShip.move();
    }
    
    
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return false;
    }
    
    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }
    
    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }
}
