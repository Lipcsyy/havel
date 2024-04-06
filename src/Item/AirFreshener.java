package Item;
import Logger.Logger;

public class AirFreshener extends Item{

    private int abilityNumber;

    public AirFreshener(){abilityNumber = 1;}

    @Override
    public boolean ReactToGas(){
        Logger.logEntry(this.getClass().getName(), "ReactToGas", "");
        abilityNumber--;
        Logger.logExit(this.getClass().getName(), "ReactToGas", "true");
        return true;
    }

    @Override
    public boolean NeedToThrow(){
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow", "abilityNumber == 0");
        return abilityNumber == 0;
    }
}
