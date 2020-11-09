package ru.stargame.dto;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.base.EnemySettingsDto;
import ru.stargame.base.Sprite;

public class EnemyBigSettingsDto extends EnemySettingsDto {
    
    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 10;
    private static final Vector2 CRUISE_SPEED = new Vector2(0f, -0.005f);   // крейсерская скорость
    
    public EnemyBigSettingsDto(TextureAtlas atlas, Sound bulletSound) {
        TextureRegion enemy0 = atlas.findRegion("enemy2");
        setRegions(Sprite.getRegionArr(enemy0, 1, 2, 2));
//        setV0(new Vector2(0f, -0.005f));
        setV0(getStartSpeed());                                 // бысрое появление на экране
        setBulletRegion(atlas.findRegion("bulletEnemy"));
        setBulletHeight(ENEMY_BIG_BULLET_HEIGHT);
        setBulletV(new Vector2(0f, -0.25f));
        setBulletSound(bulletSound);
        setDamage(ENEMY_BIG_DAMAGE);
        setReloadInterval(ENEMY_BIG_RELOAD_INTERVAL);
        setHeight(ENEMY_BIG_HEIGHT);
        setHp(ENEMY_BIG_HP);
    }
    
    @Override
    public void setDamageForLevel(int level) {
        setDamage(ENEMY_BIG_DAMAGE * level);
    }
    
    @Override
    public Vector2 getCruiseSpeed() {
        return CRUISE_SPEED;
    }
}
