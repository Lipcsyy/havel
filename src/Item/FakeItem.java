package Item;
import Player.Player;
import java.util.Random;

public class FakeItem extends Item {

    double version;

    FakeItem() {
        Random random = new Random();
        version = random.nextDouble( 0, 1 );
    }
}
