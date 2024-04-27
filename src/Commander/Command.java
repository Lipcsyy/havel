package Commander;

import GameManager.GameManager;

public abstract interface Command {

    public abstract void execute( String[] params);

}
