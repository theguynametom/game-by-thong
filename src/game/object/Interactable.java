package game.object;

import game.lv.Level;

public interface Interactable {
    public void onTouch(Player player, Level level);
}
