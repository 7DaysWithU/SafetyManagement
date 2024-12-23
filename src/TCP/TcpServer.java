package TCP;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class TcpServer
{
    public void server(String host, int port) throws IOException
    {
        try (ServerSocket serverSocket = new ServerSocket(port, 10, InetAddress.getByName(host)))
        {
            ExecutorService executor = Executors.newFixedThreadPool(10);

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                executor.submit(this.getClientHandler(clientSocket));
            }
        }
    }

    public abstract Runnable getClientHandler(Socket clientSocket);
}