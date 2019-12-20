package atomics.command;

import atomics.type.Match;
import io.atomix.copycat.Query;

import java.util.List;

public class GetAllMatchesQuery implements Query<List<Match>>
{

    public GetAllMatchesQuery()
    {
    }
}
