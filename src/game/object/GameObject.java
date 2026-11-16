package game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/** Lop co so luu toa do va quy dinh cac hanh vi chung cua object trong game. */
public abstract class GameObject{
    // Toa do goc tren ben trai cua object trong he toa do the gioi.
    protected int x;
    protected int y;

    // Kich thuoc hitbox duoc dung khi ve va tinh va cham.
    protected int width;
    protected int height;

    /** Khoi tao object voi vi tri va kich thuoc do lop con cung cap. */
    public GameObject(int x, int y, int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    /** Tao hinh chu nhat bao quanh object de dung cho kiem tra va cham. */
    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    /** Tra ve toa do ngang hien tai trong the gioi game. */
    public int getX() { return x; }

    /** Tra ve toa do doc hien tai trong the gioi game. */
    public int getY() { return y; }

    /** Tra ve chieu rong hitbox cua object. */
    public int getWidth() { return width; }

    /** Tra ve chieu cao hitbox cua object. */
    public int getHeight() { return height; }

    /** Cap nhat ca hai toa do khi Collision can dat lai object sau va cham. */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /** Lop con dinh nghia cach thay doi trang thai trong moi tick game. */
    public abstract void update();

    /** Lop con dinh nghia cach ve object tren Graphics cua Swing. */
    public abstract void draw(Graphics2D g);
}

