package game.object;

import java.awt.Color;
import java.awt.Graphics2D;

import game.lv.Level;

public class Spike extends GameObject implements Interactable {
    public Spike(int x, int y){
        super(x, y, 32, 32);
    }
    
    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.green);
        g.fillRect(x, y, 32, 32);
    }
    @Override 
    public void update(){}
    @Override
    public void onTouch(Player player, Level level){
        player.respawn();
    }
}
