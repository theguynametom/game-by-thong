package game.object;

import game.level.Level;

/** Hop dong cho object phan ung khi Player cham hoac tuong tac. */
public interface Interactable {
    /** Xu ly va cham truc tiep giua Player va object. */
    public void onTouch(Player player, Level level);

    /** Xu ly lenh tuong tac chu dong; mac dinh khong lam gi neu object khong ho tro. */
    default void onInteract(Player player, Level level) { }
}
