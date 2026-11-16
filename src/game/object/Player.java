package game.object;

import java.awt.Graphics2D;
import java.awt.Color;

/** Dai dien nhan vat, luu van toc, spawn va trang thai tiep dat. */
public class Player extends GameObject {
    // Cac hang so dieu khien toc do di chuyen, nhay va roi.
    private static final int MOVE_SPEED = 4;
    private static final int JUMP_SPEED = 15;
    private static final int GRAVITY = 1;
    private static final int MAX_FALL_SPEED = 12;

    // Luu spawn ban dau de Spike hoac roi khoi map co the dua Player tro lai.
    private final int spawnX;
    private final int spawnY;

    // Van toc ngang/ doc duoc cap nhat boi input, vat ly va Collision.
    private int velocityX;
    private int velocityY;

    // Collision bat co nay khi Player dang dung tren mot vat can.
    private boolean onGround;

    /** Tao Player voi hitbox va ghi nho vi tri spawn ban dau. */
    public Player( int x, int y){
        super(x, y, 32, 48);
        this.spawnX=x;
        this.spawnY=y;
    }
    
    @Override
    public void update(){
        // Tang van toc roi moi tick va chan toc do de giam nguy co xuyen qua Block.
        velocityY = Math.min(velocityY + GRAVITY, MAX_FALL_SPEED);
    }

    /** Ve Player bang hinh chu nhat do cho den khi sprite duoc tich hop. */
    @Override 
    public void draw(Graphics2D g){
        // Chon mau do de nhan vat tam thoi de nhan biet khi chua dung sprite.
        g.setColor(Color.RED);
        // Ve hitbox Player theo toa do world hien tai.
        g.fillRect(x, y, 32, 48);
    }

    /** Dat van toc sang trai theo muc di chuyen co dinh cua game. */
    public void moveLeft(){
        velocityX = -MOVE_SPEED;
    }

    /** Dat van toc sang phai theo muc di chuyen co dinh cua game. */
    public void moveRight(){
        velocityX = MOVE_SPEED;
    }

    /** Chuyen hai co phim thanh van toc ngang; neu hai phim giong nhau thi dung lai. */
    public void setHorizontalInput(boolean leftPressed, boolean rightPressed) {
        // Neu ca hai co bang nhau thi ca hai deu tha hoac cung bam, nen khong chon huong.
        if (leftPressed == rightPressed) {
            velocityX = 0;
        } else if (leftPressed) {
            // Chi phim trai duoc bam thi chuyen sang trai.
            moveLeft();
        } else {
            // Nguoc lai chi phim phai duoc bam thi chuyen sang phai.
            moveRight();
        }
    }

    /** Chi dat van toc nhay khi Collision xac nhan Player dang tren mat dat. */
    public void jump(){
        if (onGround) {
            // Van toc am day Player len tren; gravity se giam dan van toc nay trong update.
            velocityY = -JUMP_SPEED;
            // Tat co tiep dat ngay de khong the nhay lien tuc tren khong.
            onGround = false;
        }
    }

    /** Cung cap van toc ngang cho Collision tinh buoc di chuyen theo truc X. */
    public int getVelocityX() { return velocityX; }

    /** Cung cap van toc doc cho Collision tinh buoc di chuyen theo truc Y. */
    public int getVelocityY() { return velocityY; }

    /** Cap nhat van toc ngang khi va cham voi tuong hoac nhan input. */
    public void setVelocityX(int velocityX) { this.velocityX = velocityX; }

    /** Cap nhat van toc doc khi cham nen, cham tran hoac nhay. */
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }

    /** Cho biet Player co the nhay trong tick hien tai hay khong. */
    public boolean isOnGround() { return onGround; }

    /** De Collision danh dau Player vua tiep dat hoac roi khoi mat dat. */
    public void setOnGround(boolean onGround) { this.onGround = onGround; }

    /** Dua Player ve spawn va xoa van toc cu sau khi cham Spike hoac roi khoi map. */
    public void respawn(){
        // Khoi phuc vi tri ban dau va xoa cac van toc co the con tu lan roi truoc.
        setPosition(spawnX, spawnY);
        velocityX=0;
        velocityY=0;
        onGround=false;
    }
}

