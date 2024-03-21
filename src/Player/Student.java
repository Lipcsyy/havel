package Player;

import Item.Item;

public class Student extends Player {
    @Override
    public void RemoveFromInventory(Item item) {

    }

    @Override
    public void ReactToTeacher(Player teacher) {

        for(Item item: items){
            if(item.ReactToHit(teacher)){
                return;
            }
        }
        setIsAlive(false);
    }

    @Override
    public void Interact(Player player) {

    }
}
