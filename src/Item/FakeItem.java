package Item;
import Player.Player;
import java.util.Random;

public class FakeItem extends Item {

    static int idNumber = 1;
    double version;

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public FakeItem() {
        super( "FakeItem_" + idNumber++ );

        Random random = new Random();
        version = random.nextDouble( 0, 1 );
    }
}
