using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Server
{
    class Server
    {
        TcpListener server = null;

        public Server(string ip, int port)
        {
            IPAddress localAddr = IPAddress.Parse(ip);
            server = new TcpListener(localAddr, port);
            server.Start();
            StartListener();
        }

        protected void StartListener()
        {
            try
            {
                while (true)
                {
                    Console.WriteLine("Waiting for a connection...");
                    TcpClient client = server.AcceptTcpClient();
                    Console.WriteLine("Client connected!");

                    Thread thr = new Thread(new ParameterizedThreadStart(Handler));
                    thr.Start(client);

                }
            }
            catch (SocketException ex)
            {
                Console.WriteLine("SocketException {0}", ex);
                server.Stop();
            }
        }

        private void Handler(object obj)
        {
            TcpClient client = (TcpClient)obj;
            var stream = client.GetStream();
            byte[] buffer = new byte[256];
            int i;
            try
            {
                while ((i = stream.Read(buffer, 0, buffer.Length)) != 0)
                {
                    string hex = BitConverter.ToString(buffer);
                    string data = Encoding.ASCII.GetString(buffer, 0, i);
                    Console.WriteLine("{1}: Received: {0}", data, Thread.CurrentThread.ManagedThreadId);

                    string str = "Hey Device!";
                    byte[] reply = Encoding.ASCII.GetBytes(str);
                    stream.Write(reply, 0, reply.Length);
                    Console.WriteLine("{1}: Sent: {0}", str, Thread.CurrentThread.ManagedThreadId);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.ToString());
                client.Close();
            }

        }
    }
}
