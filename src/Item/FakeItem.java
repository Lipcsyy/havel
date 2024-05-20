package Item;
import Player.Player;
import java.util.Random;
import Enums.*;

public class FakeItem extends Item {

    static int idNumber = 1;

    /*
    VERSIONS:
    0.0 - 0.33 : MASK
    0.33 - 0.66 : TVSZ
    0.66 - 1.0 : SLIDERULE
     */
    EVersion version;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public FakeItem() {
        super( "FakeItem_" + idNumber++ );
    }

    public void setVersion( EVersion version){
        this.version = version;
    }
}
