import java.awt.Graphics2D;
import java.awt.Color;

public class Player extends GameObject {
    private int Vx;
    private int Vy;
    private boolean onGround;

    public Player( int x, int y){
        super(x, y, 32, 48);
    }
    
    @Override
    public void update(){
        x +=this.Vx;
    }
    @Override 
    public void draw(Graphics2D g){
        g.setColor(Color.RED);
        g.fillRect(x, y, 32, 48);
    }
    public void moveLeft(){
        this.Vx=-5;
    }
    public void moveRight(){
        this.Vx=5;
    }
    public void jump(){
        if(onGround==true){
            this.Vy++;
        }

    }
}
