package com.haoming.centipede;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Plane extends Objects {
    private int ship_width;
    private int ship_height;
    private List<Bullet> bullets;
    public static int PLANE_LIFE = 3;

    public Plane(int x, int y) {
        super(x, y);

        initSpaceShip();
    }

    private void initSpaceShip() {

        bullets = new ArrayList<>();
        loadImage("src/com/haoming/resources/plane.png");
        getImageDimensions();
        ship_height = getImg_height();
        ship_width = getImg_width();
    }

    public void move() {
        if (x < 1) {
            x = 1;
        }
        if (x > Entry.width - ship_width){  //TODO: change the ratio
            x = Entry.width - ship_width;
        }
        if (y < 1) {
            y = 1;
        }
        if (y > Entry.height - 2*ship_height){
            y = Entry.height - 2*ship_height;
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void mouseClicked(MouseEvent e){  // Only fire on click
        fire();
        x = e.getX()-20;
        y = e.getY()-20;
    }

    public void mousePressed(MouseEvent e){
        x = e.getX()-20;
        y = e.getY()-20;
    }

    public void mouseDragged(MouseEvent e){
        x = e.getX()-20;
        y = e.getY()-20;
    }
    public void mouseMoved(MouseEvent e){
        x = e.getX()-20;
        y = e.getY()-20;
    }

    public void fire() {
        try {
            if(Board.ingame) {
                AePlayWave apw = new AePlayWave("src/com/haoming/resources/bullet_shot.wav");
                apw.start();
                bullets.add(new Bullet(x + ship_width / 2, y + ship_height / 2));
            }
            //Thread.sleep(200);
        }
        catch(Exception e){ }
    }

    public void planeHitted(){

        if (PLANE_LIFE <= 0){
            Board.ingame = false;
        }
        else{
            Board.restart = true;
        }
    }

}
