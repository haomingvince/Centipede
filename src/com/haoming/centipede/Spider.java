package com.haoming.centipede;

public class Spider extends Objects {
    private int SPIDER_LIFE = 2;
    private int xspeed = 1;
    private int yspeed = 1;

    public Spider(int x, int y) {
        super(x, y);
        initSpider();
    }

    private void initSpider() {
        loadImage("src/com/haoming/resources/spider.png");
        getImageDimensions();
    }

    public void move(){
        if(x <= 1 || x >= Entry.width - 40) {
            this.xspeed = -this.xspeed;
        }
        else if(y <= 1 || y >= Entry.height - 40){
            this.yspeed = -this.yspeed;
        }
        x += xspeed;
        y += yspeed;
    }
    public void spiderHitted() {
        SPIDER_LIFE -= 1;
        System.out.println("spider life = "+SPIDER_LIFE);
        Board.score += 100;
        if (SPIDER_LIFE == 0) {
            this.setVisible(false);
            Board.score += 600;
        }
    }
}
