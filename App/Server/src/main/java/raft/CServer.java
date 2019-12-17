package raft;

import server.FanThread;
import server.ReporterThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * just a TCP server, example taken from http://www.tutorialspoint.com/java/java_networking.htm
 */
public class CServer extends Thread {
    protected Debug logger;
    protected int number;
    private ServerSocket serverSocket;
    protected Socket t_server;
    protected DataInputStream s_in;
    protected DataOutputStream s_out;
    public static final String ERROR = "321165sfg4022";
    protected int port;

    public CServer(int number, int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.number = number;
        this.port = port;

        // append the server number to the logger indicator
        this.logger = new Debug("RAFT-SERVER" + number, Debug.DEBUG, System.out);
        this.logger.debug("Server " + number + " created; port:" + port);
    }

    public void run() {

        boolean isReporter = false;

        try {

            System.out.println("Server is listening on port " + port);

            while (true) {
                    this.t_server = this.serverSocket.accept();
                    logger.debug("Waiting for client on port " +serverSocket.getLocalPort() + "...");
                    logger.debug("Just connected to "+ this.t_server.getRemoteSocketAddress());

                        InputStream input = this.t_server.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                        String text = reader.readLine();
                        if (text.startsWith("isUser")) {
                            if (text.endsWith("isReporter")) {
                                new ReporterThread(this.t_server).start();
                            } else {
                                new FanThread(this.t_server).start();
                            }
                        }
                        else{
                            this.s_in = new DataInputStream(this.t_server.getInputStream());
                            this.s_out = new DataOutputStream(this.t_server.getOutputStream());
                            this.process();
                        }
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // read form client
    protected String read() {
        try {
            return this.s_in.readUTF();
        } catch (IOException e) {
            logger.error("read" + e.toString());
            return ERROR;
        }
    }

    // write onto client
    // read form client
    protected boolean write(String response) {
        try {
            this.s_out.writeUTF(response);
            return true;
        } catch (IOException e) {
            logger.error("write" + e.toString());
            return false;
        }
    }


    // to rewrite
    protected void process() {
        // an echo
        try {
            String from_client = this.read();
            this.logger.debug("Read form client: " + from_client);
            String response = "You said " + from_client;
            this.write(response);
        } catch (Exception e) {
            logger.error("process" + e.toString());
        }
    }
}