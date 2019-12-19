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
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class ReporterClient extends StateMachine
{
    public static void main( String[] args, String[] args2 ){
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

        if(args2[0].equals("isUnitTest")){
            CompletableFuture[] futures = new CompletableFuture[]{
                    client.submit(new AddMatchCommand(1, "COR X PAL")),
                    client.submit(new AddMatchCommentCommand(1, "corinthians com a bola")),
                    client.submit(new AddMatchCommentCommand(1, "palmeiras esta jogando mal")),
                    client.submit(new AddMatchCommentCommand(1, "gooool do corinthians")),
                    client.submit(new AddMatchCommentCommand(1, "torcida vai a loucura")),
                    client.submit(new AddMatchCommentCommand(1, "palmeiras perdeu"))
            };

            CompletableFuture.allOf(futures).thenRun(() -> System.out.println("Commands completed!"));

        }else {

            client.submit(new AddMatchCommand(1, "COR X PAL"));

            while (true) {
                System.out.println("\n=========================");
                System.out.println("|   faca um comentario   ");
                System.out.println("=========================\n");
                Scanner s = new Scanner(System.in);
                String commentario = s.next();
                client.submit(new AddMatchCommentCommand(1, commentario));
            }
        }
    }}
