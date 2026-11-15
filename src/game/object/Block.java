package game.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends GameObject {
    public Block (int x, int y){
        super(x, y,32, 32);
    }
    
    @Override
    public void update(){}
    @Override 
    public void draw(Graphics2D g){
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 32, 32);
    }
}

