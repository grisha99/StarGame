package ru.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.math.Rect;
import ru.stargame.pool.BulletPool;
import ru.stargame.pool.ExplosionPool;
import ru.stargame.sprite.Bullet;
import ru.stargame.sprite.Explosion;

public class Ship extends Sprite{
    
    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    
    
    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Sound bulletSound;
    protected float bulletHeight;
    protected int damage;
    
    protected final Vector2 v;
    protected final Vector2 v0;
    protected final Vector2 cruiseSpeed;    // крейсерская скорость
    protected final Vector2 bulletV;
    protected final Vector2 bulletPos;
    
    protected float reloadTimer;
    protected float reloadInterval;
    
    protected int hp;
    
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    
    protected boolean isInBattle;       // корабль в режиме боя (уже вышел из экрана)
    
    public Ship() {
        v = new Vector2();
        v0 = new Vector2();
        cruiseSpeed = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
    }
    
    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v = new Vector2();
        v0 = new Vector2();
        cruiseSpeed = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (isInBattle && (reloadTimer >= reloadInterval)) {    // если уже в режиме боя и перезаряжены, стреляем
            reloadTimer = 0;
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }
    
    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play();
    }
    
    @Override
    public void destroy() {
        super.destroy();
        boom();
    }
    
    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }
    
    public int getDamage() {
        return damage;
    }
    
    public void setReloadTimer (int value) {
        this.reloadTimer = value;
    }
    
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
