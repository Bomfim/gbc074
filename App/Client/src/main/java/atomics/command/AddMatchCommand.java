package atomics.command;

import io.atomix.copycat.Command;

public class AddMatchCommand implements Command<Boolean>
{
    public int id;
    public String comment;

    public AddMatchCommand(int id, String comment)
    {
        this.id = id;
        this.comment = comment;
    }
}
