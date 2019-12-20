package atomics.client;

import atomics.command.*;
import atomics.type.Match;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FanClient extends StateMachine {
    public static void main(String[] args) {

        List<Address> addresses = new LinkedList<>();

        CopycatClient.Builder builder = CopycatClient.builder()
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build());
        CopycatClient client = builder.build();

        for (int i = 1; i < args.length; i += 2) {
            Address address = new Address(args[i], Integer.parseInt(args[i + 1]));
            addresses.add(address);
        }

        CompletableFuture<CopycatClient> future = client.connect(addresses);
        future.join();

        try {
            if(args[0].equals("isUnitTest")){
                Match match = client.submit(new GetMatchQuery(1)).get();
                System.out.println(match);
            }else {
                List<Match> matches = client.submit(new GetAllMatchesQuery()).get();

                System.out.println("\n=========================");
                System.out.println("|   Escolha uma partida   ");

                for(Match match : matches){
                    String[] arrSplit = match.getComment().split("-");
                    System.out.println(match.getId() + " - " + arrSplit[0] + " \n");
                }

                System.out.println("=========================\n");

                Scanner s = new Scanner(System.in);
                int id = s.nextInt();
                Match match = client.submit(new GetMatchQuery(id)).get();
                System.out.println(match);

                int lentgh = 0;
                while (true) {
                    Match updatedMatch = client.submit(new GetMatchQuery(id)).get();
                    if (updatedMatch != null && updatedMatch.getComment().length() > lentgh) {
                        System.out.println(client.submit(new GetLastMatchCommentQuery(id)).get());
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
