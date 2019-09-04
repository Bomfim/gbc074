using System;
using System.Net.Sockets;

namespace Client
{
    class Program
    {
        static void Main(string[] args)
        {
            Connect("192.168.0.28", "Hello I'm Device 1...");
        }
        static void Connect(string server, string message)
        {
            try
            {
                int port = 50051;
                TcpClient client = new TcpClient(server, port);
                NetworkStream stream = client.GetStream();

                // Translate the Message into ASCII.
                byte[] data = System.Text.Encoding.ASCII.GetBytes(message);

                // Send the message to the connected TcpServer. 
                stream.Write(data, 0, data.Length);
                Console.WriteLine("Sent: {0}", message);

                // Bytes Array to receive Server Response.
                data = new byte[256];
                string response = string.Empty;

                // Read the Tcp Server Response Bytes.
                int bytes = stream.Read(data, 0, data.Length);
                response = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
                Console.WriteLine("Received: {0}", response);

                stream.Close();
                client.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: {0}", e);
            }
            Console.Read();
        }
    }
}
