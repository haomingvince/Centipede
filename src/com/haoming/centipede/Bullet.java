package com.haoming.centipede;

public class Bullet extends Objects {

    private final int MISSILE_SPEED = 2;

    public Bullet(int x, int y) {
        super(x, y);

        initMissile();
    }

    private void initMissile() {

        loadImage("src/com/haoming/resources/bullet.png");
        getImageDimensions();
    }

    public void move() {

        y -= MISSILE_SPEED;

        if (y <= 0) {
            visible = false;
        }
    }
}
