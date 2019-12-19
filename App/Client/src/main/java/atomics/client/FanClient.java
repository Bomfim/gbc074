package atomics.client;

import atomics.command.AddMatchCommand;
import atomics.command.AddMatchCommentCommand;
import atomics.command.GetMatchQuery;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FanClient extends StateMachine
{
    public static void main( String[] args , String[] ars2){
    	List<Address> addresses = new LinkedList<>();

        CopycatClient.Builder builder = CopycatClient.builder()
                                                     .withTransport( NettyTransport.builder()
                                                     .withThreads(4)
                                                     .build());
        CopycatClient client = builder.build();

        for(int i = 0; i <args.length;i+=2)
    	{
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
    		addresses.add(address);
    	}
        
        CompletableFuture<CopycatClient> future = client.connect(addresses);
        future.join();

        try {
        	System.out.println("Resposta ao fan " + client.submit(new GetMatchQuery(1)).get());
        } catch (Exception e)
        {
        	System.out.println("Commands may have failed.");
        	e.printStackTrace();
        }
    }
}
