package Item;
import Player.*;

public class Tvsz extends Item {
    private int abilityNumber;

    public Tvsz(){
        abilityNumber = 3;
    }

    @Override
    public boolean NeedToThrow() {
        return abilityNumber == 0;
    }

    @Override
    public void Use(Player player) {
        abilityNumber --;
    }

    @Override
    public boolean ReactToHit(Player player){
        return true;
    }

}
