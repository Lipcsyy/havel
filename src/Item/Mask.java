package Item;

import Player.Player;

public class Mask extends Item{

    private int turnsLeft;

    public Mask(){turnsLeft = 3;}

    public boolean ReactToGas(){
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
