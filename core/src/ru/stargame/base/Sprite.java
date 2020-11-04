package ru.stargame.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.math.Rect;

public class Sprite extends Rect {
    
    protected float angle;
    protected float scale = 1;
    protected TextureRegion[] regions;
    protected int frame;
    
    protected boolean destroyed;
    
    public Sprite() {
    }
    
    public Sprite(TextureRegion region) {
        this.regions = new TextureRegion[1];
        regions[0] = region;
    }
    
    /**
     *
     * @param region - исходный регион с множеством однотипных картинок
     * @param rowCount - кол-во строк в регионе
     * @param colCount - кол-во столбцов в регионе
     * @param frameCount - кол-во текстур (массив может быть не полным)
     */
    public Sprite(TextureRegion region, int rowCount, int colCount, int frameCount) {
        this.regions = Sprite.getRegionArr(region, rowCount, colCount, frameCount);
    }
    
    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }
    
    public void update(float delta) {
    
    }
    
    public void resize(Rect worldBounds) {
    
    }
    
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }
    
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }
    
    public void setAngle(float angle) {
        this.angle = angle;
    }
    
    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void destroy() {
        destroyed = true;
    }
    
    public void flushDestroy() {
        destroyed = false;
    }
    
    // получаем массив текстур из региона
    public static TextureRegion[] getRegionArr(TextureRegion region, int rowCount, int colCount, int frameCount) {
        TextureRegion [] result = new TextureRegion[frameCount];
        int frameWidth = region.getRegionWidth() / colCount;
        int frameHeight = region.getRegionHeight() / rowCount;
        int currentFrame = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                result[currentFrame] = new TextureRegion(region, j * frameWidth, i * frameHeight, frameWidth, frameHeight);
                if (currentFrame == frameCount - 1) {
                    return result;
                }
                currentFrame++;
            }
        }
        return null;
    }
}
