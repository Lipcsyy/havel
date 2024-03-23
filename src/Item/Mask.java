package Item;

import Player.Player;

public class Mask extends Item{

    private int abilityNumber;

    public Mask(){abilityNumber = 3;}

    @Override
    public boolean ReactToGas(){
        return true;
    }

    @Override
    public void DecreaseTurnsLeft(){
        abilityNumber --;
    }

    @Override
    public boolean NeedToThrow(){
        return abilityNumber == 0;
    }


}
