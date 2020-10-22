package ru.stargame.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.stargame.math.Rect;

public class BaseShip extends Sprite{

//    private Texture texture;            // изображение объекта
    private Vector2 position;           // текущая позиция
    private float speed;                // скорость передвижения
    private Vector2 direction;          // направление движения (нормализована)
    private Vector2 endPosition;        // конечноая точка
    private Vector2 tmp;

    public BaseShip(/*String texturePath*/Texture texture, float startX, float startY, float speed) {
        super(new TextureRegion(texture));
        pos.set(new Vector2(startX - (regions[frame].getRegionWidth() / 2f), startY));
        setSize(regions[frame].getRegionWidth(), regions[frame].getRegionHeight());
        this.speed = speed;
        endPosition = pos.cpy();
        direction = new Vector2();
        tmp = new Vector2();
    }

    public void move() {
        tmp.set(endPosition);

        if (tmp.sub(pos).len() >= direction.len()) {       // конечная точка не достигнута
            pos.add(direction);                            // сл.позиция
        } else {                                                // объект достиг конечной точки
            pos.set(endPosition);                          // ровняем текущую позицию с конечной (для точности)
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setEndPosition(float x, float y) {
        endPosition.set(x - (regions[frame].getRegionWidth() / 2f), y - (regions[frame].getRegionHeight() / 2f)); // конечная позиция с учетом размеров объекта
        direction = endPosition.cpy().sub(pos).setLength(speed);                   // расчет направления движения до конечной позиции с учетом скорости
        System.out.println(pos.x + "-" + pos.y + "-" + halfHeight + "-" + halfWidth);
    }
    
    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.11f);
        pos.set(worldBounds.pos);
        setBottom(worldBounds.getBottom());
    }
}
