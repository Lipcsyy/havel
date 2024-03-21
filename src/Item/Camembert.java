package Item;

import Player.Player;
import Room.*;

public class Camembert extends Item{

    @Override
    public void Use(Player player) {
        Room room = player.GetRoom();
        room.SetTurnsLeftForEffect(3);
    }
}
