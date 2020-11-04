package ru.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.base.Sprite;

public class Explosion extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.01f;
    
    private float animateTimer;
    
    private Sound explosionSound;
    
    public Explosion(TextureRegion region, int rowCount, int colCount, int frameCount, Sound explosionSound) {
        super(region, rowCount, colCount, frameCount);
        this.explosionSound = explosionSound;
    }
    
    public void set(float height, Vector2 pos) {
        setHeightProportion(height);
        this.pos.set(pos);
        explosionSound.play();
    }
    
    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }
    
    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
