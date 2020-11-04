package ru.stargame.dto;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.base.EnemySettingsDto;
import ru.stargame.base.Sprite;

public class EnemyMediumSettingsDto extends EnemySettingsDto {
    
    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HP = 5;
    private static final Vector2 CRUISE_SPEED = new Vector2(0f, -0.03f);    // крейсерская скорость
    
    public EnemyMediumSettingsDto(TextureAtlas atlas, Sound bulletSound) {
        TextureRegion enemy0 = atlas.findRegion("enemy1");
        setRegions(Sprite.getRegionArr(enemy0, 1, 2, 2));
//        setV0(new Vector2(0f, -0.03f));
        setV0(getStartSpeed());                                                 // быстрое появление на экране
        setBulletRegion(atlas.findRegion("bulletEnemy"));
        setBulletHeight(ENEMY_MEDIUM_BULLET_HEIGHT);
        setBulletV(new Vector2(0f, -0.25f));
        setBulletSound(bulletSound);
        setDamage(ENEMY_MEDIUM_DAMAGE);
        setReloadInterval(ENEMY_MEDIUM_RELOAD_INTERVAL);
        setHeight(ENEMY_MEDIUM_HEIGHT);
        setHp(ENEMY_MEDIUM_HP);
    }
    
    @Override
    public Vector2 getCruiseSpeed() {
        return CRUISE_SPEED;
    }
}
