package ru.stargame.ships;

import ru.stargame.base.EnemySettingsDto;
import ru.stargame.base.Ship;
import ru.stargame.math.Rect;
import ru.stargame.pool.BulletPool;
import ru.stargame.pool.ExplosionPool;

public class EnemyShip extends Ship {
    
    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;

    }
    
    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getBottom());
        if (!isInBattle) {                      // не в бою еще, за экраном
            if (getTop() <= worldBounds.getTop()) {     // вышел на экран, в режим боя, крейсерская скорость и первый выстрел
                v.set(cruiseSpeed);
                isInBattle = true;
                setReloadTimer(0);              // избегаем двойного выстрела при вводе корабля в бой
                shoot();
            }
        }
        super.update(delta);
        if (getBottom() < worldBounds.getBottom()) {    // уничтожение корабля внизу экрана
            destroy();
        }
    }
    
    public void set(EnemySettingsDto settings) {
        this.regions = settings.getRegions();
        this.v.set(settings.getV0());
        this.cruiseSpeed.set(settings.getCruiseSpeed());
        this.bulletRegion = settings.getBulletRegion();
        this.bulletHeight = settings.getBulletHeight();
        this.bulletV.set(settings.getBulletV());
        this.bulletSound = settings.getBulletSound();
        this.damage = settings.getDamage();
        this.reloadInterval = settings.getReloadInterval();
        setHeightProportion(settings.getHeight());
        this.hp = settings.getHp();
        isInBattle = false;                     // изначально за экраном, не в бою
    }
    
    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }
}
