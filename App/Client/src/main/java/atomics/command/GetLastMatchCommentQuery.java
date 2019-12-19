package atomics.command;

import atomics.type.Match;
import io.atomix.copycat.Query;

public class GetLastMatchCommentQuery implements Query<String>
{
    public int id;

    public GetLastMatchCommentQuery(int id)
    {
        this.id = id;
    }
}
