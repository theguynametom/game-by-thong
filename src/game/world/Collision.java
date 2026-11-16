package game.world;

import game.level.Level;
import game.object.Block;
import game.object.Door;
import game.object.GameObject;
import game.object.Interactable;
import game.object.Player;
import java.util.List;
import java.util.ArrayList;

/** Di chuyen Player va phan giai va cham theo truc X roi truc Y. */
public class Collision {
    /** Di chuyen va giai quyet va cham voi Block, dung cho cac truong hop kiem tra don gian. */
    public void movePlayer(Player player, List<Block> blocks) {
        // Dung gioi han rong rat lon de overload nay chi xu ly va cham Block.
        movePlayer(player, new ArrayList<>(blocks), Integer.MAX_VALUE);
    }

    /** Tao danh sach vat can tu Block va cac Door dang dong cua Level. */
    public void movePlayer(Player player, Level level) {
        // Copy Block vao danh sach chung; Door mo se khong co trong danh sach vat can.
        List<GameObject> solids = new ArrayList<>(level.getBlocks());
        for (Door door : level.getDoors()) {
            if (!door.isOpen()) solids.add(door);
        }
        // Truyen kich thuoc map de Player bi chan tai bien trai va phai.
        movePlayer(player, solids, level.getWidth());
    }

    /** Dich chuyen rieng tung truc va dua hitbox ve sat mat vat can khi bi chong lan. */
    private void movePlayer(Player player, List<? extends GameObject> solids, int worldWidth) {
        // Xu ly ngang truoc de toa do X da on dinh truoc khi tinh va cham doc.
        int horizontalStep = player.getVelocityX();
        // Gioi han Player trong map truoc khi kiem tra cac vat can ngang.
        int nextX = Math.max(0, Math.min(player.getX() + horizontalStep, worldWidth - player.getWidth()));
        player.setPosition(nextX, player.getY());
        // Chi xu ly khi hitbox giao nhau va Player co buoc di chuyen theo truc X.
        for (GameObject solid : solids) {
            if (!player.getBounds().intersects(solid.getBounds()) || horizontalStep == 0) continue;
            // Dat Player ve ben truoc vat can dua tren huong di chuyen hien tai.
            int resolvedX = horizontalStep > 0
                    ? solid.getX() - player.getWidth()
                    : solid.getX() + solid.getWidth();
            // Dat sat phia doi dien cua vat can va xoa van toc de tranh tiep tuc xuyen vao.
            player.setPosition(resolvedX, player.getY());
            player.setVelocityX(0);
        }

        // Dat lai onGround truoc khi tinh tiep truc doc de phan biet roi va tiep dat.
        int verticalStep = player.getVelocityY();
        player.setOnGround(false);
        // Di chuyen theo van toc doc roi kiem tra tung vat can.
        player.setPosition(player.getX(), player.getY() + verticalStep);
        for (GameObject solid : solids) {
            if (!player.getBounds().intersects(solid.getBounds())) continue;
            if (verticalStep > 0) {
                // Khi roi xuong, dat chan Player tren mat vat can va bat co onGround.
                player.setPosition(player.getX(), solid.getY() - player.getHeight());
                player.setOnGround(true);
            } else if (verticalStep < 0) {
                // Khi dang bay len, dat dau Player duoi vat can de chan xuyen tran.
                player.setPosition(player.getX(), solid.getY() + solid.getHeight());
            }
            // Xoa van toc doc sau khi dau hoac chan Player cham vat can.
            player.setVelocityY(0);
        }
    }

    /** Goi hanh vi cham cua object khi hitbox Player giao voi hitbox object. */
    public void checkInteractions(Player player, Level level) {
        // Duyet danh sach ve vi no chua cac object co the phan ung khi cham.
        for (GameObject object : level.getObjects()) {
            // Chi goi callback cho object tuong tac va dang giao voi Player.
            if (object instanceof Interactable interactable
                    && player.getBounds().intersects(object.getBounds())) {
                interactable.onTouch(player, level);
            }
        }
    }
}
