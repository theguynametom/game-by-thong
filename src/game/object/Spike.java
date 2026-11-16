package game.object;

import java.awt.Color;
import java.awt.Graphics2D;

import game.level.Level;

/** Bay hoi sinh Player ve spawn khi hai hitbox cham nhau. */
public class Spike extends GameObject implements Interactable {
    /** Tao Spike voi hitbox co kich thuoc mot tile. */
    public Spike(int x, int y){
        super(x, y, 32, 32);
    }
    
    /** Ve Spike bang hinh chu nhat mau xanh trong ban demo. */
    @Override
    public void draw(Graphics2D g){
        // Mau xanh danh dau vi tri bay trong ban demo hien tai.
        g.setColor(Color.green);
        // Ve hinh bay theo kich thuoc hitbox mot tile.
        g.fillRect(x, y, 32, 32);
    }
    /** Spike khong tu thay doi trang thai theo thoi gian. */
    @Override 
    public void update(){}

    /** Dua Player ve spawn khi Collision phat hien va cham voi Spike. */
    @Override
    public void onTouch(Player player, Level level){
        // Dung lai Player tai toa do spawn va xoa van toc hien tai.
        player.respawn();
    }
}
