package com.haoming.centipede;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class Objects {
    protected int x;
    protected int y;
    protected int img_width;
    protected int img_height;
    protected boolean visible;
    protected Image image;

    public Objects(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    protected void getImageDimensions() {
        img_width = image.getWidth(null);
        img_height = image.getHeight(null);
    }

    protected int getImg_width(){
        return img_width;
    }

    protected int getImg_height(){
        return img_height;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, img_width, img_height);
    }

}
