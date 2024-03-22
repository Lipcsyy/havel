package Item;

import Player.Player;
import Room.*;

public class Camembert extends Item{

    @Override
    public void Use(Player player) {
        this.MakeGas(player.GetRoom());
    }

    public void MakeGas(Room room){
        room.SetTurnsLeftForEffect(5);
    }
}
