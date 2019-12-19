package atomics.command;

import io.atomix.copycat.Command;

public class AddMatchCommentCommand implements Command<Boolean> {

    public int id;
    public String comment;

    public AddMatchCommentCommand(int id, String comment)
    {
        this.id = id;
        this.comment = comment;
    }
}
