package game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** Luu trang thai phim giu va cac yeu cau chi kich hoat mot lan. */
public class InputHandler extends KeyAdapter {
    // Cac co nay giu trang thai di chuyen lien tuc trong khi phim duoc nhan.
    private boolean leftPressed;
    private boolean rightPressed;

    // jumpPressed chan lap lai yeu cau khi he dieu hanh gui nhieu su kien keyPressed.
    private boolean jumpPressed;
    private boolean jumpRequested;

    // Hai co tuong tu cho phim E, tranh cong tac bi dao trang thai lien tuc khi giu phim.
    private boolean interactPressed;
    private boolean interactRequested;

    /** Cap nhat co phim khi Swing thong bao nguoi choi vua nhan mot phim. */
    @Override
    public void keyPressed(KeyEvent event) {
        // Doi ma phim thanh trang thai dieu khien ma GamePanel co the doc trong moi tick.
        switch (event.getKeyCode()) {
            // A va mui ten trai deu yeu cau Player di sang trai.
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed = true;
            // D va mui ten phai deu yeu cau Player di sang phai.
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_SPACE -> {
                // Chi tao yeu cau nhay o canh bam dau tien, khong tao lai khi phim dang giu.
                if (!jumpPressed) jumpRequested = true;
                jumpPressed = true;
            }
            case KeyEvent.VK_E -> {
                // Chi tao yeu cau tuong tac o canh bam dau tien cua phim E.
                if (!interactPressed) interactRequested = true;
                interactPressed = true;
            }
            // Cac phim khong nam trong bo dieu khien duoc bo qua.
            default -> { }
        }
    }

    /** Xoa trang thai phim giu de Player dung di chuyen hoac co the bam lai phim hanh dong. */
    @Override
    public void keyReleased(KeyEvent event) {
        // Nhan biet phim duoc tha de dung di chuyen hoac cho phep bam lai phim hanh dong.
        switch (event.getKeyCode()) {
            // Tha mot trong hai phim trai thi bo co trai.
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed = false;
            // Tha mot trong hai phim phai thi bo co phai.
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed = false;
            // Cho phep lan bam Space tiep theo tao yeu cau nhay moi.
            case KeyEvent.VK_SPACE -> jumpPressed = false;
            // Cho phep lan bam E tiep theo tao yeu cau tuong tac moi.
            case KeyEvent.VK_E -> interactPressed = false;
            default -> { }
        }
    }

    /** Tra ve true neu phim trai dang duoc giu. */
    public boolean isLeftPressed() { return leftPressed; }

    /** Tra ve true neu phim phai dang duoc giu. */
    public boolean isRightPressed() { return rightPressed; }

    /** Lay va xoa yeu cau nhay de mot lan bam chi duoc xu ly mot lan. */
    public boolean consumeJumpRequest() {
        // Sao chep yeu cau hien tai de co the tra ve sau khi xoa co luu tru.
        boolean requested = jumpRequested;
        // Xoa yeu cau; lan goi sau chi tra true neu nguoi choi bam phim moi.
        jumpRequested = false;
        return requested;
    }

    /** Lay va xoa yeu cau tuong tac voi Switch. */
    public boolean consumeInteractionRequest() {
        // Giu lai gia tri de tra ve cho GamePanel trong khi dat lai co.
        boolean requested = interactRequested;
        // Xoa co de phim E can duoc bam lai moi tao yeu cau khac.
        interactRequested = false;
        return requested;
    }
}
