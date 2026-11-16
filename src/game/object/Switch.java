package game.object;

import game.level.Level;
import java.awt.Color;
import java.awt.Graphics2D;

/** Cong tac mo hoac dong mot Door khi Player bam phim E o gan. */
public class Switch extends GameObject implements Interactable {
    // Khoang cach toi da theo tung truc ma Player co the bam E de dung cong tac.
    private static final int INTERACTION_RANGE = 64;

    // ID Switch va tham chieu den Door ma no dieu khien.
    private final String id;
    private final Door door;

    /** Tao cong tac da lien ket voi mot Door cu the. */
    public Switch(String id, int x, int y, Door door) {
        super(x, y, 32, 32);
        this.id = id;
        this.door = door;
    }

    /** Tra ve ID de phan biet cong tac trong du lieu Level. */
    public String getId() { return id; }

    /** Kiem tra khoang cach giua tam Player va tam Switch theo ca hai truc. */
    public boolean isInRange(Player player) {
        // Dung tam hitbox thay vi goc toa do de tinh khoang cach tuong tac.
        int playerCenterX = player.getX() + player.getWidth() / 2;
        int playerCenterY = player.getY() + player.getHeight() / 2;
        int switchCenterX = x + width / 2;
        int switchCenterY = y + height / 2;
        return Math.abs(playerCenterX - switchCenterX) <= INTERACTION_RANGE
                && Math.abs(playerCenterY - switchCenterY) <= INTERACTION_RANGE;
    }

    /** Cham vao Switch khong kich hoat no; nguoi choi can bam E. */
    @Override
    public void onTouch(Player player, Level level) { }

    /** Dao trang thai cua Door da duoc lien ket khi Level xac nhan lenh E hop le. */
    @Override
    public void onInteract(Player player, Level level) {
        door.toggle();
    }

    /** Switch khong tu thay doi trang thai theo thoi gian. */
    @Override
    public void update() { }

    /** Ve cong tac bang mau khac nhau dua tren trang thai cua Door. */
    @Override
    public void draw(Graphics2D graphics) {
        // Dung mau xanh khi cua mo va mau vang nau khi cua dang dong.
        graphics.setColor(door.isOpen() ? new Color(97, 158, 115) : new Color(173, 116, 55));
        graphics.fillRect(x, y, width, height);
        // Hinh tron nho la chi tiet trang tri o giua cong tac.
        graphics.setColor(new Color(35, 28, 25));
        graphics.drawOval(x + 11, y + 11, 10, 10);
    }
}
