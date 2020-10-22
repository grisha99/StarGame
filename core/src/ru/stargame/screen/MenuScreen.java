package ru.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.stargame.base.BaseScreen;
import ru.stargame.base.BaseShip;
import ru.stargame.math.Rect;
import ru.stargame.ships.PlayerShip;
import ru.stargame.sprite.Background;

public class MenuScreen extends BaseScreen {

    Texture bgImg;
    Texture pShip;
    
    private Texture bg;
    private Background background;

    BaseShip playerShip;

    @Override
    public void show() {
        super.show();
        bgImg = new Texture("textures/bgH.jpg");
        pShip = new Texture("spaceship64.png");
        background = new Background(new TextureRegion(bgImg));
        playerShip = new PlayerShip(pShip, Gdx.graphics.getWidth() / 2f, 0, 3f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        playerShip.draw(batch);
        batch.end();

        playerShip.move();

    }
    
    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        playerShip.setEndPosition(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public void dispose() {
        bgImg.dispose();
        super.dispose();
    }
}
