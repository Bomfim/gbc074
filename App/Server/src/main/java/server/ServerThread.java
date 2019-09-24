// package server;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.io.OutputStream;
// import java.io.PrintWriter;
// import java.net.Socket;
// import java.util.List;
// import pubsub.Message;
// import pubsub.publisher.Publisher;
// import pubsub.publisher.PublisherImpl;
// import pubsub.service.PubSubService;
// import pubsub.subscriber.Subscriber;
// import pubsub.subscriber.SubscriberImpl;

// public class ServerThread extends Thread {
//     private Socket socket;
//     private boolean isReporter;

//     public ServerThread(Socket socket, boolean isReporter) {
//         this.socket = socket;
//         this.isReporter = isReporter;
//     }

//     @Override
//     public void run() {
//         try {
//             InputStream input = this.socket.getInputStream();
//             BufferedReader reader = new BufferedReader(new InputStreamReader(input));

//             OutputStream output = this.socket.getOutputStream();
//             PrintWriter writer = new PrintWriter(output, true);

//             if (this.isReporter) {
//                 System.out.println("New reporter!\n");
//                 Publisher reporter = new PublisherImpl();
//                 while (true) {
//                     String text = reader.readLine();
//                     Message m = new Message("SAO vs FLA", text);
//                     reporter.publish(m);
//                     PubSubService.getInstance().broadcast();
//                 }
//             } else {
//                 System.out.println("New fan!\n");

//                 Subscriber fan = new SubscriberImpl();

//                 fan.addSubscriber("SAO vs FLA");

//                 while (true) {
//                     List<Message> subscriberMessages;
//                     // fan.getMessagesForSubscriberOfMatch("SAO");
//                     // fan.printMessages();
//                     Thread.sleep(2000);

//                     subscriberMessages = fan.getSubscriberMessages();

//                     if (!subscriberMessages.isEmpty()) {
//                         for (Message message : subscriberMessages) {
//                             writer.println("Reporter: " + message.getMatch() + " : " + message.getPayload());
//                         }
//                     }
//                     // fan.setSubscriberMessages(Collections.<Message>emptyList());
//                 }
//             }
//         } catch (IOException ex) {
//             System.out.println("Server exception: " + ex.getMessage());
//             ex.printStackTrace();
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
// }
