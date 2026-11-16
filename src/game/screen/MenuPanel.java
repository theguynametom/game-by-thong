package game.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/** Hien thi menu khoi dong va chuyen yeu cau cua nguoi choi cho Main. */
@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
    // Mau chu va nut duoc dung chung de giao dien co bang mau thong nhat.
    private static final Color TEXT_COLOR = new Color(230, 222, 205);
    private static final Color BUTTON_COLOR = new Color(50, 42, 48);
    private static final Color BUTTON_HOVER_COLOR = new Color(78, 55, 62);

    /** Tao tieu de, mo ta va hai nut; Main cung cap callback cho cac hanh dong. */
    @SuppressWarnings("this-escape")
    public MenuPanel(Runnable onStart, Runnable onExit) {
        super(new GridBagLayout());

        // Panel nay gom noi dung theo chieu doc va de nen trong suot de thay nen menu.
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Tao tieu de va mo ta bang font co san, khong can them asset UI.
        JLabel title = new JLabel("DARK ASCENT", SwingConstants.CENTER);
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setForeground(TEXT_COLOR);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 42));

        JLabel subtitle = new JLabel("A shadow waits beyond the gate", SwingConstants.CENTER);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setForeground(new Color(164, 155, 153));
        subtitle.setFont(new Font(Font.SERIF, Font.ITALIC, 16));

        // Nut chi phat callback; Main quyet dinh viec chuyen man hay dong cua so.
        JButton startButton = createButton("START GAME", onStart);
        JButton exitButton = createButton("EXIT", onExit);

        // Them thanh phan theo thu tu doc va chen khoang cach de menu de nhin.
        content.add(title);
        content.add(Box.createRigidArea(new Dimension(0, 8)));
        content.add(subtitle);
        content.add(Box.createRigidArea(new Dimension(0, 38)));
        content.add(startButton);
        content.add(Box.createRigidArea(new Dimension(0, 12)));
        content.add(exitButton);
        add(content);
    }

    /** Tao nut co chung mau sac, kich thuoc va phan hoi hover. */
    private JButton createButton(String label, Runnable action) {
        JButton button = new JButton(label);
        // Can nut va dat kich thuoc toi da de cac nut luon cung kich co.
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(220, 48));
        button.setMaximumSize(new Dimension(220, 48));
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setOpaque(true);
        button.setFocusPainted(false);
        // Tao vien va le trong de nut tach ro khoi nen toi.
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(138, 108, 91)),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        button.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        // Doi mau khi con tro vao/roi nut de nguoi choi nhan biet nut duoc tro toi.
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        // Goi callback duoc truyen vao khi nut duoc bam hoac kich hoat bang phim.
        button.addActionListener(event -> action.run());
        return button;
    }

    /** Ve nen toi va cac lop trang tri phia sau cac thanh phan menu. */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // Tao ban sao Graphics de trang tri khong lam thay doi trang thai ve cua Swing.
        Graphics2D g2 = (Graphics2D) graphics.create();
        // Chon chat luong ve va tao nen gradient toi theo chieu doc.
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setPaint(new GradientPaint(
                0, 0, new Color(28, 24, 34),
                0, getHeight(), new Color(5, 7, 12)));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Ve vung sang mau do tim de tao diem nhan sau tieu de.
        g2.setColor(new Color(117, 72, 67, 70));
        g2.fillOval(getWidth() / 2 - 240, getHeight() / 2 - 210, 480, 360);
        // Ve da giac toi phia duoi nhu duong chan troi trang tri don gian.
        g2.setColor(new Color(12, 13, 20, 210));
        int[] xPoints = {0, getWidth() / 4, getWidth() / 2, getWidth() * 3 / 4, getWidth()};
        int[] yPoints = {getHeight(), getHeight() - 75, getHeight() - 35, getHeight() - 95, getHeight()};
        g2.fillPolygon(xPoints, yPoints, xPoints.length);
        // Giai phong Graphics tam sau khi ve xong nen.
        g2.dispose();
    }
}
