package game.screen;

import game.input.InputHandler;
import game.level.Level;
import game.object.GameObject;
import game.object.Player;
import game.world.Camera;
import game.world.Collision;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.nio.file.Path;
import javax.swing.JPanel;
import javax.swing.Timer;

/** Quan ly Level, input, cap nhat gameplay va ve man hinh choi. */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    // Cac he thong nay duoc dung chung trong suot thoi gian Level dang chay.
    private final Level level;
    private final InputHandler inputHandler;
    private final Collision collision;
    private final Camera camera;

    // Timer tao nhip cap nhat gan 60 lan moi giay ma khong can vong lap rieng.
    private final Timer gameTimer;

    /** Tai Level 1 va tao cac he thong can thiet cho gameplay. */
    @SuppressWarnings("this-escape")
    public GamePanel(){
        // Bao cho JFrame kich thuoc client mong muon cua panel.
        setPreferredSize(new Dimension(800, 450));
        try {
            // Doc level tu duong dan tinh tu thu muc goc project.
            level = Level.load(Path.of("resources", "levels", "level1.txt"));
        } catch (IOException exception) {
            // Dung khoi tao va bao loi neu file level thieu hoac khong dung dinh dang.
            throw new IllegalStateException("Khong the tai resources/levels/level1.txt", exception);
        }
        // Khoi tao bo doc phim, xu ly va cham va camera cho Level da nap.
        inputHandler = new InputHandler();
        collision = new Collision();
        camera = new Camera();
        // Gan bo doc phim vao panel va tao Timer, nhung chua bat Timer luc menu hien.
        addKeyListener(inputHandler);
        gameTimer = new Timer(16, event -> updateGame());
    }

    /** Cai dat kha nang nhan phim khi Swing gan panel vao cua so. */
    // Bat focus khi Swing gan panel vao cua so de nhan phim dieu khien.
    @Override
    public void addNotify() {
        super.addNotify();
        setFocusable(true);
    }

    /** Bat Timer gameplay sau khi MenuPanel chuyen sang man nay. */
    public void startGame() {
        gameTimer.start();
        // Chuyen focus sang panel de cac su kien ban phim duoc gui toi InputHandler.
        requestFocusInWindow();
    }

    /** Dung Timer neu man choi bi dong hoac roi khoi giao dien. */
    public void stopGame() {
        gameTimer.stop();
    }

    /** Cap nhat input, Player, va cham, tuong tac va camera trong mot tick. */
    private void updateGame() {
        if (!level.isCompleted()) {
            // Lay Player tu Level de cac he thong cung cap nhat mot doi tuong.
            Player player = level.getPlayer();
            // Chuyen co phim trai/phai thanh van toc ngang cho Player.
            player.setHorizontalInput(inputHandler.isLeftPressed(), inputHandler.isRightPressed());
            // Tieu thu yeu cau nhay mot lan neu nguoi choi vua bam Space.
            if (inputHandler.consumeJumpRequest()) player.jump();
            // Cap nhat van toc doc truoc khi Collision dich chuyen Player.
            player.update();
            // Di chuyen theo hai truc va giai quyet va cham voi Block/Door.
            collision.movePlayer(player, level);
            // Hoi sinh neu Player roi qua bien duoi cua map.
            if (player.getY() > level.getHeight()) player.respawn();
            // Kiem tra va cham voi Spike hoac Goal sau khi da cap nhat vi tri.
            collision.checkInteractions(player, level);
            // Chi thu tuong tac Switch khi InputHandler ghi nhan phim E moi.
            if (inputHandler.consumeInteractionRequest()) level.interactWithNearbySwitch(player);
            // Theo Player tai vi tri moi va gioi han camera theo kich thuoc map.
            camera.follow(player, level.getWidth(), level.getHeight(), getWidth(), getHeight());
        }
        // Yeu cau Swing ve lai panel, ke ca khi Level da hoan thanh.
        repaint();
    }

    /** Ve the gioi theo camera va ve HUD tren he toa do man hinh. */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // To mau den de nen cua panel khong phu thuoc vao mau mac dinh cua Swing.
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        // Tao Graphics rieng de phep dich camera khong anh huong den HUD.
        Graphics2D worldGraphics = (Graphics2D) graphics.create();
        // Bat khang cua de cac vien object duoc ve min hon.
        worldGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Chuyen toa do world sang screen bang cach tru vi tri camera.
        worldGraphics.translate(-camera.getX(), -camera.getY());
        // Ve cac object truoc va Player sau cung de nhan vat nam tren lop tren.
        for (GameObject object : level.getObjects()) object.draw(worldGraphics);
        level.getPlayer().draw(worldGraphics);
        // Huy ban Graphics da dich chuyen sau khi ve xong the gioi.
        worldGraphics.dispose();

        // Tao Graphics doc lap de cac thong tin HUD khong bi camera dich chuyen.
        Graphics2D overlay = (Graphics2D) graphics.create();
        // Chon mau chu sang de doc duoc tren nen den.
        overlay.setColor(new Color(235, 230, 218));
        // Dat font nho cho dong huong dan dieu khien.
        overlay.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        // Hien phim di chuyen, nhay va tuong tac o goc man hinh.
        overlay.drawString("A / LEFT - MOVE LEFT    D / RIGHT - MOVE RIGHT    SPACE - JUMP    E - USE", 18, 24);
        if (level.isCompleted()) {
            // Hien chu thong bao khi Goal da dat co hoan thanh trong Level.
            overlay.setFont(new Font(Font.SERIF, Font.BOLD, 34));
            // Ve thong bao o gan giua viewport de nguoi choi nhan ra ket qua.
            overlay.drawString("LEVEL COMPLETE", 285, 230);
        }
        // Giai phong Graphics HUD sau khi ve xong.
        overlay.dispose();
    }
}

