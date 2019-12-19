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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FanClient extends StateMachine
{
    public static void main( String[] args){
        long TEMPO = (1000 * 5); // chama o método a cada 3 segundos

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
            int delay = 5000;   // delay de 5 seg.
            int interval = 1000;  // intervalo de 1 seg.
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    try {
                        System.out.println(client.submit(new GetMatchQuery(1)).get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }, delay, interval);

        } catch (Exception e)
        {
        	System.out.println("Commands may have failed.");
        	e.printStackTrace();
        }
    }
}
