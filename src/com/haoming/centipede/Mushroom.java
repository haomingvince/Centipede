package com.haoming.centipede;

public class Mushroom extends Objects {
    private int MUSHROOM_LIFE = 3;
    private int collide_count = 0;
    public Mushroom(int x, int y) {
        super(x, y);
        initMushroom();
    }

    private void initMushroom() {
        loadImage("src/com/haoming/resources/mushroom.png");
        getImageDimensions();
    }

    public void addCollide_count(){
        collide_count += 1;
    }

    public int getCollide_count(){
        return collide_count;
    }

    public int getMushroomLife(){
        return MUSHROOM_LIFE;
    }

    public void setMushroomLife(){
        this.MUSHROOM_LIFE = 3;
    }

    public void mushroomHitted() {
        MUSHROOM_LIFE -= 1;
        System.out.println("mushroom life = "+MUSHROOM_LIFE);
        Board.score += 1;
        if (MUSHROOM_LIFE <= 0) {
            this.setVisible(false);
            Board.score += 5;
        }
    }
}
