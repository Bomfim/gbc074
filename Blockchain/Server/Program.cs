using System;
using System.Threading;

namespace Server
{
    class Program
    {
        static void Main(string[] args)
        {
            Thread thread = new Thread(delegate ()
            {
                Server server = new Server("192.168.0.28", 50051);
            });

            thread.Start();

            Console.WriteLine("Server Started!");
        }
    }
}
