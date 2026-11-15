package game.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Goal extends GameObject implements Interactable {
    public Goal (int x, int y){
        super(x, y, 64, 64);
    }
    
    @Override 
    public void draw(Graphics2D g){
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 64, 64);
    }
    @Override 
    public 
}
