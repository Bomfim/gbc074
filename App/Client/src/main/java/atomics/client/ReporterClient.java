package atomics.client;

import atomics.command.AddMatchCommand;
import atomics.command.AddMatchCommentCommand;
import atomics.command.GetAllMatchesQuery;
import atomics.command.GetMatchQuery;
import atomics.type.Match;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ReporterClient extends StateMachine
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        List<Address> addresses = new LinkedList<>();

        CopycatClient.Builder builder = CopycatClient.builder()
                .withTransport( NettyTransport.builder()
                        .withThreads(4)
                        .build());
        CopycatClient client = builder.build();

        for(int i = 1; i <args.length;i+=2)
        {
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
            addresses.add(address);
        }

        CompletableFuture<CopycatClient> future = client.connect(addresses);
        future.join();

        if(args[0].equals("isUnitTest")){
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
            CompletableFuture[] futures = new CompletableFuture[]{
                    client.submit(new AddMatchCommand(1, "COR X PAL")),
                    client.submit(new AddMatchCommand(2, "REL X BAR")),
                    client.submit(new AddMatchCommand(3, "FLA X FLU")),
                    client.submit(new AddMatchCommand(4, "SAN X SPO"))
            };
            CompletableFuture.allOf(futures).thenRun(() -> System.out.println("partidas iniciadas!"));

            List<Match> matches = client.submit(new GetAllMatchesQuery()).get();

            System.out.println("\n=========================");
            System.out.println("|   Escolha uma partida para comentar ");
            for(Match match : matches){
                String[] arrSplit = match.getComment().split("-");
                System.out.println(match.getId() + " - " + arrSplit[0] + " \n");
            }

            System.out.println("=========================\n");

            Scanner s = new Scanner(System.in);
            int id = s.nextInt();

            while (true) {
                System.out.println("\n=========================");
                System.out.println("|   faca um comentario   ");
                System.out.println("=========================\n");
                Scanner s2 = new Scanner(System.in);
                String commentario = s2.nextLine();
                client.submit(new AddMatchCommentCommand(id, commentario));
            }
        }
    }}
