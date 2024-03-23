package Item;

import Player.Player;

public class Rag extends Item{
    private int turnsLeft;

    public Rag(){
        turnsLeft = 5;
    }

    @Override
    public void Use(Player player){
        player.Freeze(3);
    }

    @Override
    public boolean CanSave(Player player){
        return true;
    }

    @Override
    public void DecreaseTurnsLeft(){
        turnsLeft --;
    }

    @Override
    public boolean NeedToThrow(){
        return turnsLeft == 0;
    }
}
