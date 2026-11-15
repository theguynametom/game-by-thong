package game.object;

//day la lop cha nhe la goc cua tat ca cac lop
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject{
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GameObject(int x, int y, int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    } //vị trí của vật
    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height); //hit box
    }
    //phan nay de viet lai lop cha
    public abstract void update();
    public abstract void draw(Graphics2D g);
}

