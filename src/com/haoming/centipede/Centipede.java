package com.haoming.centipede;

public class Centipede extends Objects {
    private boolean leftright = true;  // true -> left; false -> right
    private int CENTI_LIFE = 2;

    public Centipede(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {
        loadImage("src/com/haoming/resources/centi.png");
        getImageDimensions();
    }

    public void setleftright(boolean leftright) {
        this.leftright = leftright;
    }

    public boolean getleftright(){
        return leftright;
    }

    public void setReverseleftright(int collide_count) {    // hit mushroom, move down one unit and reverse the movement
        if(collide_count <= Board.getCentiNum()) {  // TODO: SIZE
            if (this.leftright) {
                this.leftright = false;
                if(this.y <= 600) {
                    this.y += 20;
                }
            } else {
                this.leftright = true;
                if(this.y <= 600) {
                    this.y += 20;
                }
            }
        }
    }

    public void move() {      // leftright: true -> left; false -> right
        //System.out.println("x = "+this.x+"   y = "+this.y);
        if (leftright) {
            if(this.x <= 1){   // hit left wall, move down one unit and reverse the movement
                if(this.y <= 600) {
                    this.y += 20;
                }
                this.leftright = false;
            }
            this.x -= 1;
        }
        else{
            if(this.x >= 560){   // hit right wall, move down one unit and reverse the movement
                if(this.y <= 600) {
                    this.y += 20;
                }
                this.leftright = true;
            }
            this.x += 1;
        }
    }

    public void centiHitted(){
        CENTI_LIFE -= 1;
        System.out.println("centi life = "+CENTI_LIFE);
        Board.score += 2;
        if (CENTI_LIFE == 0){
            this.setVisible(false);
            Board.score += 5;  // TODO: +7 or +5?
        }

    }
}
