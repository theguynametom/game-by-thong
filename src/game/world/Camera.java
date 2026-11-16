package game.world;

import game.object.Player;

/** Theo doi Player va gioi han khung nhin trong kich thuoc level. */
public class Camera {
    // Goc tren trai cua khung nhin trong he toa do the gioi.
    private int x;
    private int y;

    /** Dat camera sao cho theo Player va khong vuot qua bien level. */
    public void follow(Player player, int levelWidth, int levelHeight, int viewportWidth, int viewportHeight) {
        // Tinh vi tri can dat de tam Player nam gan tam viewport.
        int targetX = player.getX() + player.getWidth() / 2 - viewportWidth / 2;
        int targetY = player.getY() + player.getHeight() / 2 - viewportHeight / 2;
        // Gioi han vi tri toi da; neu map nho hon man hinh thi muc toi da bang 0.
        x = clamp(targetX, Math.max(0, levelWidth - viewportWidth));
        y = clamp(targetY, Math.max(0, levelHeight - viewportHeight));
    }

    /** Ep gia tri nam trong khoang tu 0 den gioi han da tinh. */
    private int clamp(int value, int maximum) {
        return Math.max(0, Math.min(value, maximum));
    }

    /** Tra ve do dich ngang ma GamePanel ap dung khi ve world. */
    public int getX() { return x; }

    /** Tra ve do dich doc ma GamePanel ap dung khi ve world. */
    public int getY() { return y; }
}
