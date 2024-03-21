package Item;
import Player.*;

public class Tvsz extends Item {
    private int abilityNumber;

    public int getAbilityNumber() {
        return abilityNumber;
    }

    public void setAbilityNumber(int abilityNumber) {
        this.abilityNumber = abilityNumber;
    }


    @Override
    public void Use(Player player) {

    }

    @Override
    public boolean ReactToGas(){
        return true;
    }

    @Override
    public boolean ReactToHit(Player player){
        return true;
    }

    public void DecreaseAbility(){

    }
}
