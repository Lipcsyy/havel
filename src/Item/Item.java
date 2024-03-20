package Item;
import Player.*;

public abstract class Item {

    public void Use() {

    };

    public boolean ReactToGas() {

        return true;
    };

    public boolean ReactToHit(Teacher teacher) {

        return true;
    };



}
