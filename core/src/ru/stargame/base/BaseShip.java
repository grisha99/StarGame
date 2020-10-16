package ru.stargame.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class BaseShip {

    private Texture texture;            // изображение объекта
    private Vector2 position;           // текущая позиция
    private float speed;                // скорость передвижения
    private Vector2 vSpeed;             // вектор скорости (не используется....пока)
    private Vector2 direction;          // направление движения (нормализована)
    private Vector2 endPosition;        // конечноая точка
    private Vector2 nextPosition;       // следующая координата для перемещения

    public BaseShip(String texturePath, float startX, float startY, float speed) {
        texture = new Texture(texturePath);
        position = new Vector2(startX - (texture.getWidth() / 2f), startY); // начальное позиционироване (внизу по середине экрана)
        this.speed = speed;
        endPosition = position.cpy();
        direction = new Vector2();
        nextPosition = new Vector2(0, 0);
    }

    public void move() {
        if (position.cpy().sub(endPosition).len() > speed) {        // конечная точка не достигнута
            nextPosition.set(direction.cpy().scl(speed));           // сл.позиция с учетом скорости
        } else {                                                    // объект достиг конечной точки
            nextPosition.setZero();                                 // сл.позиция нулевая (нет перемещения)
            position.set(endPosition);                              // ровняем текущую позицию с конечной (для точности)
        }
        position.add(nextPosition);                                 // перемещаем объект на след. позицию
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public Vector2 getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(float x, float y) {
        endPosition.set(x - (texture.getWidth() / 2f), y - (texture.getHeight() / 2f)); // конечная позиция с учетом размеров объекта
        direction = endPosition.cpy().sub(position).nor();                              // расчет направления движения до конечной позиции
    }

    public Vector2 getNextPosition() {
        return nextPosition;
    }
}
