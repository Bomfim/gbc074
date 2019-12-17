package client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Reporter implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Reporter.class.getName());
    private Socket socket;
    private String lastMessage;

    public Reporter(Socket socket) {
        this.socket = socket;
    }

    static boolean heartbeat(Socket socket) {
        try {
            if (socket.isClosed()) {
                return true;
            }

            int res = socket.getInputStream().read(new byte[1024], 0, 5);

            if (res < 0) {
                return true;
            }
        } catch (IOException ignore) {
        }

        return false;
    }

    private boolean login() {
        try {
            socket = new Socket(socket.getInetAddress().getHostAddress(), socket.getPort());
            Main.ehlo(socket, "NEW_REPORTER_CONNECTED");
        } catch (IOException e) {
            return true;
        }

        return false;
    }

    private void reconnect() throws IOException, InterruptedException {
        boolean reconnect = true;
        long triedTimes = 0;

        while (reconnect) {
            Thread.sleep(triedTimes * 1000L);

            if (socket.isConnected()) {
                socket.close();
            }

            reconnect = login();
            triedTimes += 1;
        }
    }

    private void reporter(Scanner scanner) {

        LOGGER.info("You are connected, type your messages:");
        boolean isDisabled = false;

        try (OutputStream output = socket.getOutputStream(); PrintWriter writer = new PrintWriter(output, true)) {

            while (!isDisabled) {
                String line = lastMessage;
                if (line == null) {
                    line = scanner.nextLine();
                }

                if (line.equals("\n")) {
                    break;
                }

                writer.println(line);
                if (heartbeat(socket)) {
                    lastMessage = line;
                    isDisabled = true;
                } else {
                    lastMessage = null;
                }
            }

        } catch (IOException e) {
            LOGGER.info("Error on reporter: " + e.getMessage());
        }

        try {
            LOGGER.info("You are offline, from this moment the messages will not be saved");
            reconnect();
        } catch (IOException | InterruptedException ignore) {
        }
    }

    @Override
    public void run() {
        while (true) {
            LOGGER.info("Connecting new reporter!");
            reporter(new Scanner(System.in));
        }
    }
}
