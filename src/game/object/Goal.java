package game.object;

import java.awt.Color;
import java.awt.Graphics2D;

import game.level.Level;

/** Diem dich danh dau level hoan thanh khi Player cham vao. */
public class Goal extends GameObject implements Interactable {
    /** Tao Goal voi kich thuoc hai tile moi chieu. */
    public Goal (int x, int y){
        super(x, y, 64, 64);
    }
    
    /** Ve Goal bang mau vang de phan biet voi cac vat the khac. */
    @Override 
    public void draw(Graphics2D g){
        // Chon mau vang de Goal khac biet voi Block va Player.
        g.setColor(Color.YELLOW);
        // Ve hinh chu nhat theo toa do world da duoc truyen vao constructor.
        g.fillRect(x, y, 64, 64);
    }

    /** Goal khong can cap nhat vat ly nen phuong thuc nay de trong. */
    @Override
    public void update() {}

    /** Bao cho Level dung cap nhat gameplay sau khi Player cham Goal. */
    @Override
    public void onTouch(Player player, Level level) {
        // Danh dau man da hoan thanh de GamePanel dung cap nhat gameplay.
        level.completeLevel();
    }
}
