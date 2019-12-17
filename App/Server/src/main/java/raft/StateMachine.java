package raft;

/**
 * Created with IntelliJ IDEA.
 * User: sistemas
 * Date: 19/10/13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */

import java.util.HashMap;
import java.util.Map;

/*
* this class acts a "State Machine" it simply "executes" two commands
* append and remove with the object recipe
 */
public class StateMachine {
    private Map recipes;
    Debug logger;

    public StateMachine() {
        this.recipes=new HashMap();
        this.logger = new Debug("STATE-MACHINE", Debug.DEBUG, System.out);
    }

    // list all stored recipes
    public String list() {
        return this.recipes.toString();
    }
}
