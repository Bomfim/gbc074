package atomics.command;

import atomics.type.Match;
import io.atomix.copycat.Query;

public class GetMatchQuery implements Query<Match>
{
    public int id;

    public GetMatchQuery(int id)
    {
        this.id = id;
    }
}
