package ru.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import ru.stargame.base.BaseScreen;
import ru.stargame.base.BaseShip;
import ru.stargame.ships.PlayerShip;

public class MenuScreen extends BaseScreen {

    Texture bgImg;

    BaseShip playerShip;

    @Override
    public void show() {
        super.show();
        bgImg = new Texture("bgH.jpg");
        playerShip = new PlayerShip("spaceship64.png", Gdx.graphics.getWidth() / 2f, 0, 3f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(bgImg, 0, 0);
        batch.draw(playerShip.getTexture(), playerShip.getPosition().x, playerShip.getPosition().y);
        batch.end();

        playerShip.move();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        playerShip.setEndPosition(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
