package Item;

import Player.Player;

public class BeerGlass extends Item{

    private int turnsLeft;

    public BeerGlass(){
        turnsLeft = 5;
    }

    public void Use(Player player){
        player.Freeze(3);
    }

    @Override
    public boolean ReactToHit(Player player){
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