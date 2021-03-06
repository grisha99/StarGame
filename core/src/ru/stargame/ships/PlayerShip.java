package ru.stargame.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.base.Ship;
import ru.stargame.math.Rect;
import ru.stargame.pool.BulletPool;
import ru.stargame.pool.ExplosionPool;

public class PlayerShip extends Ship {

    
//    public PlayerShip(TextureAtlas atlas, float startX, float startY, float speed){
//        super(atlas.findRegion("main_ship"),startX, startY, speed);
//
//    }
    
    private static final float SHIP_HEIGHT = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final float RELOAD_INTERVAL = 0.2f;
    private static final int BASE_HP = 100;
    
    private static final int INVALID_POINTER = -1;
    
    private boolean pressedLeft;
    private boolean pressedRight;
    
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public PlayerShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.v0.set(0.5f, 0);
        this.bulletV.set(0, 0.5f);
        this.reloadInterval = RELOAD_INTERVAL;
        this.hp = BASE_HP;
        this.isInBattle = true;             // наш корабль сразу в бою
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
        bulletPos.set(pos.x, getTop());
        super.update(delta);
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
    }
    
    public int getBaseHP() {
        return BASE_HP;
    }
    
    public void dispose() {
        bulletSound.dispose();
    }
    
    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
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
//            case Input.Keys.UP:
//                shoot();
//                break;
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
   
}
