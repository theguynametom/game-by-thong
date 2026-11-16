package game.object;

import java.awt.Color;
import java.awt.Graphics2D;

/** Dai dien cho canh cua co the dong hoac mo. */
public class Door extends GameObject {
    // ID dung de Switch trong file level tim va lien ket dung Door.
    private final String id;

    // true khi cua mo va khong duoc Collision xem la vat can.
    private boolean open;

    /** Tao cua dong tai toa do duoc doc tu level. */
    public Door(String id, int x, int y) {
        super(x, y, 32, 64);
        this.id = id;
    }

    /** Tra ve ID cua de Level lien ket voi Switch. */
    public String getId() { return id; }

    /** Cho biet cua dang mo hay dong. */
    public boolean isOpen() { return open; }

    /** Dao trang thai mo/dong sau khi Switch duoc kich hoat. */
    public void toggle() { open = !open; }

    /** Door khong tu thay doi trang thai theo thoi gian. */
    @Override
    public void update() { }

    /** Ve cua; cua mo dung mau trong de the hien Player co the di qua. */
    @Override
    public void draw(Graphics2D graphics) {
        // Cua dong co mau dac; cua mo co mau trong de the hien trang thai co the di qua.
        graphics.setColor(open ? new Color(67, 118, 91, 105) : new Color(104, 73, 55));
        graphics.fillRect(x, y, width, height);
        // Ve vien cua de hitbox va hinh dang cua van de nhan biet khi mo.
        graphics.setColor(new Color(190, 158, 111));
        graphics.drawRect(x, y, width, height);
    }
}
