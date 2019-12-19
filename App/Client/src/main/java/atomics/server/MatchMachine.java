package atomics.server;



import atomics.command.AddMatchCommand;
import atomics.command.AddMatchCommentCommand;
import atomics.command.GetLastMatchCommentQuery;
import atomics.command.GetMatchQuery;
import atomics.type.Match;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.atomix.copycat.server.CopycatServer.Builder;
import static io.atomix.copycat.server.CopycatServer.builder;

public class MatchMachine extends StateMachine
{
    private Map<Integer, Match> matchHashMap = new HashMap<>();

    public Boolean AddMatch(Commit<AddMatchCommand> commit){
        try{
            AddMatchCommand avc = commit.operation();
            Match m = new Match(avc.id, avc.comment);
            
            //System.out.println("Adding " + m );

            return matchHashMap.putIfAbsent(avc.id, m ) == null;
        }finally{
            commit.close();
        }
    }

    public Match GetMatch(Commit<GetMatchQuery> commit){
    	try{
                GetMatchQuery gvq = commit.operation();
    		//System.out.println("Matches:" + matchHashMap);

            Match result = matchHashMap.get(gvq.id);
    		//System.out.println("GetMatch " + gvq.id + " = " + result);
    		return result;
        }finally{
            commit.close();
        }
    }

    public Match AddMatchComment(Commit<AddMatchCommentCommand> commit){
        try{
            AddMatchCommentCommand gvq = commit.operation();
            Match m = matchHashMap.get(gvq.id);

            m.setComment(m.getComment() + "\n- " + gvq.comment);
            System.out.println("Reporter:" + gvq.comment);
            return m;
        }finally{
            commit.close();
        }
    }

    public String GetLastMatchComment(Commit<GetLastMatchCommentQuery> commit){
        try{
            GetLastMatchCommentQuery gvq = commit.operation();

            Match updatedMatch = matchHashMap.get(gvq.id);

            String[] arrSplit = updatedMatch.getComment().split("-");

            return arrSplit[arrSplit.length -1];
            }
        finally{
            commit.close();
        }
    }



    public static void main( String[] args ){
    	int myId = Integer.parseInt(args[0]);
    	List<Address> addresses = new LinkedList<>();
    	
    	for(int i = 1; i <args.length; i+=2)
    	{
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
    		addresses.add(address);
    	}

        Builder builder = builder(addresses.get(myId));
        builder.withStateMachine(MatchMachine::new);
        builder.withTransport(NettyTransport.builder()
                .withThreads(4)
                .build());
        builder.withStorage(Storage.builder()
                .withDirectory(new File("logs_" + myId)) //Must be unique
                .withStorageLevel(StorageLevel.DISK)
                .build());
        CopycatServer server = builder.build();

        if(myId == 0)
        {
            server.bootstrap().join();
        }
        else
        {
            server.join(addresses).join();
        }
    }
}
