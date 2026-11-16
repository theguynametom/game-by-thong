package game;

import game.screen.GamePanel;
import game.screen.MenuPanel;

import java.awt.CardLayout;
import javax.swing.JFrame; //import cai nay de hinh cua so
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** Khoi dong ung dung va tao cua so chua cac man hinh cua game. */
public class Main{
    /** Dua viec khoi tao giao dien vao luong su kien cua Swing. */
    public static void main(String[] arg){
        // Swing yeu cau viec tao va cap nhat giao dien dien ra tren Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> createWindow());
    }

    /** Tao mot cua so va chuyen giua MenuPanel va GamePanel bang CardLayout. */
    private static void createWindow(){
        // JFrame la cua so duy nhat duoc su dung trong toan bo game.
        JFrame frame = new JFrame("My Platformer");
        // CardLayout chi hien mot JPanel tai mot thoi diem va cho phep doi card theo ten.
        CardLayout cardLayout = new CardLayout();
        JPanel screens = new JPanel(cardLayout);

        // Tao man gameplay truoc de menu co the chuyen sang no khi nguoi choi bam Start.
        GamePanel gamePanel = new GamePanel();
        // Menu chi gui yeu cau; Main quan ly viec chuyen man va dong cua so.
        MenuPanel menuPanel = new MenuPanel(
                () -> {
                    // Hien card gameplay va bat dau Timer cap nhat game.
                    cardLayout.show(screens, "game");
                    gamePanel.startGame();
                },
                // Dong cua so hien tai khi nguoi choi bam Exit.
                () -> frame.dispose());

        // Dang ky hai man hinh bang ten de CardLayout co the chuyen doi giua chung.
        screens.add(menuPanel, "menu");
        screens.add(gamePanel, "game");
        // Dat JPanel quan ly card lam noi dung chinh cua cua so.
        frame.setContentPane(screens);
        // Tinh kich thuoc cua so dua tren kich thuoc uu tien cua cac panel.
        frame.pack(); //Dong goi cua so theo kich thuoc GamePanel.
        // Dong ung dung khi nguoi dung bam nut dong cua so.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cho nut thoat
        // Dat cua so vao giua man hinh.
        frame.setLocationRelativeTo(null); //man hinh hien nam giua
        // Hien cua so sau khi da them va cau hinh cac man hinh.
        frame.setVisible(true);
    }
}
