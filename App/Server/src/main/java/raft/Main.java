package raft;

import java.util.Properties;
import java.util.Timer;

public class Main {

    // initialize the output logger to DEBUG and output by System.out
    // this is not de log of raft!!
    private static Debug logger = new Debug("MAIN", Debug.DEBUG, System.out);
    public static final int DEFAULT_NUMBER_OF_SERVERS = 5;
    public static final int FIRST_PORT = 5001;

    // initialize general properties manager
    private static Properties props = new Properties();

    public static void main(String[] args) {
	    logger.debug("Raft Initialize");
        int numberOfServers = 5;

        // take arguments

       numberOfServers = DEFAULT_NUMBER_OF_SERVERS;
       logger.debug("Using default number of servers " + DEFAULT_NUMBER_OF_SERVERS);


        if (numberOfServers<=0) {
            numberOfServers=DEFAULT_NUMBER_OF_SERVERS;
            logger.debug("Using default number of servers");
        }

        // timer of the serverpool
        Timer timer=new Timer();

        // create the serverpool
        RaftServerPool sp = new RaftServerPool(
                numberOfServers,
                FIRST_PORT
        );

        // do maintenance processes every TIME_REPORT_POOL_STATUS ms
        timer.schedule(sp, RaftServerPool.TIME_REPORT_POOL_STATUS, RaftServerPool.TIME_REPORT_POOL_STATUS);

        // start the pool and print status
        sp.start();


        logger.debug("Main thread waiting...");

    }

    private static void quit(String msg) {
        System.err.println(msg);
        help();
        System.exit(1);
    }

    private static void help() {
        System.err.println("Usage:");
        System.err.println(" number_of_servers");
    }



}
