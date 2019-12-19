package atomics.type;

import io.atomix.copycat.Operation;

import java.io.Serializable;

public class Match implements Serializable {
    int id;
    String comment;

    public Match(int id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString()
    {
        return "(" + comment + ")";
    }
}
