package atomics.client;

import atomics.command.AddMatchCommand;
import atomics.command.AddMatchCommentCommand;
import atomics.command.GetLastMatchCommentQuery;
import atomics.command.GetMatchQuery;
import atomics.type.Match;
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

public class FanClient extends StateMachine {
    public static void main(String[] args, String[] args2) {

        List<Address> addresses = new LinkedList<>();

        CopycatClient.Builder builder = CopycatClient.builder()
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build());
        CopycatClient client = builder.build();

        for (int i = 0; i < args.length; i += 2) {
            Address address = new Address(args[i], Integer.parseInt(args[i + 1]));
            addresses.add(address);
        }

        CompletableFuture<CopycatClient> future = client.connect(addresses);
        future.join();

        try {
            if(args2[0].equals("isUnitTest")){
                Match match = client.submit(new GetMatchQuery(1)).get();
                System.out.println(match);
            }else {
                Match match = client.submit(new GetMatchQuery(1)).get();
                System.out.println(match);

                int lentgh = 0;
                while (true) {
                    Match updatedMatch = client.submit(new GetMatchQuery(1)).get();
                    if (updatedMatch != null && updatedMatch.getComment().length() > lentgh) {
                        System.out.println(client.submit(new GetLastMatchCommentQuery(1)).get());
                        lentgh = updatedMatch.getComment().length();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Commands may have failed.");
            e.printStackTrace();
        }
    }
}
