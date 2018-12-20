package com.haoming.centipede;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Font;

public class Board extends JPanel implements ActionListener {
    private final int plane_x = 500;
    private final int plane_y = 700;
    private final int DELAY = 10;
    public static boolean ingame = true;
    public static boolean restart = false;
    private Timer timer;
    private Plane plane;
    private List<Centipede> centipedes;
    private List<Mushroom> mushrooms;
    private int mid = 99;   // Max number, make sure not move inversely at the beginning
    private static int CENTI_NUM = 5;
    private static int MUSHROOM_NUM = 50;
    private List<Spider> spiders;
    private static int SPIDER_NUM = 1;
    public static int score = 0;

    public Board() {
        plane = new Plane(plane_x, plane_y);
        initBoard();
    }

    private void initBoard() {
        setBackground(Color.BLACK);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                plane.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                plane.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //plane.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                plane.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                plane.mouseMoved(e);
            }
        });
        initCentipedes();
        initSpider();
        initMushroom();
        timer = new Timer(DELAY, this);
        timer.start();
    }


    public void initCentipedes() {
        centipedes = new ArrayList<>();
        mid = 99;
        for (int i = 0; i <= CENTI_NUM - 1; i++){
            centipedes.add(new Centipede(580+i*20, 20));
        }
    }

    public void initSpider(){
        spiders = new ArrayList<>();
        for (int i = 0; i <= SPIDER_NUM - 1; i++){
            spiders.add(new Spider(560, randRange(400, 500)));
        }
    }

    public void initMushroom(){
        mushrooms = new ArrayList<>();
        for (int i = 0; i <= MUSHROOM_NUM - 1; i++){
            mushrooms.add(new Mushroom(randRange(0, 28)*20, randRange(3, 30)*20));   // x:0-560    y:60-600 -> safe area: 600-800
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(ingame) {
            g2d.drawImage(plane.getImage(), plane.getX(), plane.getY(), this);

            List<Bullet> bullets = plane.getBullets();

            for (Bullet bullet : bullets) {
                g2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
            }
            for (Centipede centipede : centipedes) {
                g2d.drawImage(centipede.getImage(), centipede.getX(), centipede.getY(), this);
            }
            for (Spider spider : spiders) {
                g2d.drawImage(spider.getImage(), spider.getX(), spider.getY(), this);
            }
            for (Mushroom mushroom : mushrooms) {
                g2d.drawImage(mushroom.getImage(), mushroom.getX(), mushroom.getY(), this);
            }
            g.setColor(Color.white);
            if (Plane.PLANE_LIFE <= 0) {
                Plane.PLANE_LIFE = 0;
            }
            g.setFont(new Font("Arial",Font.BOLD,15));
            g.drawString("Score: " + score + " Life: " + Plane.PLANE_LIFE + " Centipedes left: " + centipedes.size(), 5, 15);
        }
        else{
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.setColor(Color.RED);
            g.drawString("Game Over!!", 135, 300);
            g.setColor(Color.GREEN);
            g.drawString("Your score: "+ score, 125, 400);
        }
    }

    public static int getCentiNum(){
        return CENTI_NUM;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        updatePlane();
        updateBullets();
        updateCentipede();
        updateSpider();
        updateMushroom();
        checkCollisions();
        repaint();
    }

    private void inGame() {
        if (!ingame) {
            timer.stop();
        }
        if (restart){
            try{
                Thread.sleep(1000);
            }catch(Exception e){ }
            initCentipedes();
            //initMushroom();   // Won't change position of mushroom on restart
            initSpider();
            for(Mushroom mushroom : mushrooms){
                if (mushroom.getMushroomLife() != 3){
                    score += 10;
                    mushroom.setMushroomLife();
                }
            }
            restart = false;
        }
    }

    private void updatePlane() {
        if (plane.isVisible()) {
            plane.move();
        }
    }

    private void updateBullets() {
        List<Bullet> bt = plane.getBullets();

        for (int i = 0; i < bt.size(); i++) {
            Bullet m = bt.get(i);

            if (m.isVisible()) {
                m.move();
            }
            else {
                bt.remove(i);
            }
        }
    }

    private void updateCentipede(){
        int i = 0;

        if (centipedes.isEmpty()) {
            initCentipedes();
            score += 600;
            repaint();
        }

        for (i = 0; i < centipedes.size(); i++) {
            Centipede a = centipedes.get(i);

            if (a.isVisible()) {  // TODO: Reversed after breaking
//                if(i > mid){
//                    a.setleftright(!a.getleftright());
//                }
                a.move();
            } else {
                centipedes.remove(i);
                if (mid >= i) {
                    mid = i;
                }
            }
        }

    }

    private void updateSpider(){
        for (int i = 0; i < spiders.size(); i++) {
            Spider spider = spiders.get(i);
            if (spider.isVisible()) {
                spider.move();
            } else {
                spiders.remove(i);
            }
        }
    }

    private void updateMushroom(){
        for (int i = 0; i < mushrooms.size(); i++) {
            Mushroom mushroom = mushrooms.get(i);
            if (mushroom.isVisible()) {
                //mushroom.move();
            } else {
                mushrooms.remove(i);
            }
        }
    }

    public void checkCollisions() {
        // Centipede collides with mushroom can use rectangle box check.
        // Spider/Centipede hits plane, use the center point check collision.
        Rectangle plane_rec = plane.getBounds();
        List<Bullet> bt = plane.getBullets();

        // Spider can only actively reacts with plane
        for (Spider spider : spiders) {
            Rectangle spider_rec = spider.getBounds();
            if (spider_rec.intersects(plane_rec)) {
                //spider.setVisible(false);
                Plane.PLANE_LIFE -= 1;
                plane.planeHitted();
            }
        }

        //  Centipede can actively reacts with mushroom, plane and walls
        for (Centipede centipede : centipedes) {
            Rectangle centi_rec = centipede.getBounds();
            if (plane_rec.intersects(centi_rec)) {
                //entipede.setVisible(false);
                Plane.PLANE_LIFE -= 1;
                plane.planeHitted();
            }
            if(centipede.getX() <= 0){
                centipede.setleftright(false);
            }
            if(centipede.getY() >= 700){
                centipede.setleftright(true);
            }
           for (Mushroom mushroom : mushrooms){
                Rectangle mushroom_rec = mushroom.getBounds();
                if (centi_rec.intersects(mushroom_rec)) {
                    mushroom.addCollide_count();
                    centipede.setReverseleftright(mushroom.getCollide_count());
                }

            }
        }

        // Bullets can actively react with mushroom, spider and cetipeds
        for(int i = 0; i < bt.size(); i++){
            Rectangle bullet_rec = bt.get(i).getBounds();
            for (Centipede centipede : centipedes) {
                Rectangle centi_rec = centipede.getBounds();
                if (bullet_rec.intersects(centi_rec)) {
                    try {
                        centipede.centiHitted();
                        bt.remove(i);
                    }
                    catch(Exception e) { // One bullet hits 2 centipedes at a time
                        System.out.println("Hit 2 centipedes at a time. Not a big deal :D");
                    }
                }
            }
            for (Spider spider : spiders) {
                Rectangle spider_rec = spider.getBounds();
                if (bullet_rec.intersects(spider_rec)) {
                    try {
                        spider.spiderHitted();
                        bt.remove(i);
                    } catch (Exception e) {
                    }
                }
            }
            for (Mushroom mushroom : mushrooms){
                Rectangle mushroom_rec = mushroom.getBounds();
                try {
                    if (bullet_rec.intersects(mushroom_rec)) {
                        mushroom.mushroomHitted();
                        bt.remove(i);
                    }
                }
                catch(Exception e) { }
            }
        }
    }


    public static int randRange(int min, int max) {
        Random random = new Random();
        int out = random.nextInt(max)%(max-min+1) + min;
        return out;
    }
}
