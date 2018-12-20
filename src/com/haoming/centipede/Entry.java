package com.haoming.centipede;


import java.awt.EventQueue;
import javax.swing.JFrame;

public class Entry extends JFrame {

    public static int width = 600;
    public static int height = 800;

    public Entry() {
        initUI();
    }

    private void initUI() {
        Board board = new Board();
        add(board);

        setSize(width, height);
        setResizable(false);
        setTitle("Centipede");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Entry ex = new Entry();
            ex.setVisible(true);
        });
    }
}
