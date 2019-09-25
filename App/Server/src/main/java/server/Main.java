package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.Arrays;

public class Main {

	public static void main(String args[]) {

		// testCRUD();

		int port = 50051;
		boolean isReporter = false;

		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New client connected");

				if (!isReporter) {
					isReporter = true;
					new ReporterThread(socket).start();
				} else {
					new FanThread(socket).start();
				}
			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// public void testCRUD() {
	// MatchDao dao = new MatchDao();
	// Match match = new Match("COR", "SAO");
	// match.setInProgress(true);
	// match.setHostTeamScore(2);
	// match.setGuestTeamScore(1);
	// match.setReporter("Bruno");
	// match.setComments(Arrays.asList("gol", "gol de novo"));

	// // Create
	// dao.persist(match);

	// // Read
	// Match createdMatch = dao.findById(match.getId());
	// System.out.println(createdMatch);

	// // Update
	// createdMatch.setHostTeamScore(3);
	// dao.edit(createdMatch);
	// System.out.println(dao.findById(createdMatch.getId()));

	// // Delete
	// dao.delete(createdMatch);
	// System.out.println(dao.readAll());
	// }
}
