package game;

import game.screen.GamePanel;

import javax.swing.JFrame; //import cai nay de hinh cua so

public class Main{
    public static void main(String[] arg){
        JFrame frame = new JFrame("My Platformer");
        frame.add(new GamePanel());
        frame.setSize(800, 450); //set size cho man hinh
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cho nut thoat
        frame.setLocationRelativeTo(null); //man hinh hien nam giua
        frame.setVisible(true);
    }
}
