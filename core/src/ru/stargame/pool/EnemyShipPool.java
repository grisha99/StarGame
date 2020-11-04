package ru.stargame.pool;

import ru.stargame.base.SpritesPool;
import ru.stargame.math.Rect;
import ru.stargame.ships.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    
    private BulletPool bulletPool;
    private Rect worldBounds;
    
    public EnemyShipPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }
    
    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
    }
}
