package ru.stargame.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.base.BaseShip;
import ru.stargame.base.Sprite;
import ru.stargame.math.Rect;
import ru.stargame.pool.BulletPool;
import ru.stargame.sprite.Bullet;

public class PlayerShip extends Sprite {

    
//    public PlayerShip(TextureAtlas atlas, float startX, float startY, float speed){
//        super(atlas.findRegion("main_ship"),startX, startY, speed);
//
//    }
    
    private static final float SHIP_HEIGHT = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final long AUTO_SHOOT_TIME = 150L; // задержка между выстрелами в миллиснкундах
    
    private static final int INVALID_POINTER = -1;
    
    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Sound shootSount;
    
    private final Vector2 v = new Vector2();
    private final Vector2 v0 = new Vector2(0.5f, 0);
    private final Vector2 bulletV = new Vector2(0, 0.5f);
    private final Vector2 bulletPos = new Vector2();
    
    private boolean pressedLeft;
    private boolean pressedRight;
    
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    
    private long lastShootTime;
    
    public PlayerShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        shootSount = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }
    
    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
    }
    
    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        } else if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        //        2-й пример ограничений
        //        if (getLeft() > worldBounds.getRight()) {
        //            setRight(worldBounds.getLeft());
        //        } else if (getRight() < worldBounds.getLeft()) {
        //            setLeft(worldBounds.getRight());
        //        }
        if (System.currentTimeMillis() - lastShootTime >= AUTO_SHOOT_TIME) { // автострельба
            shoot();
        }
    }
    
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x <  worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }
    
    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }
    
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
//            case Input.Keys.UP:
//                shoot();
//                break;
        }
        return false;
    }
    
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }
    
    private void moveRight() {
        v.set(v0);
    }
    
    private void moveLeft() {
        v.set(v0).rotate(180);
    }
    
    private void stop() {
        v.setZero();
    }
    
    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, getTop());
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, 1, 0.01f);
        shootSount.play(0.1f);
        lastShootTime = System.currentTimeMillis();
    }
    
}
