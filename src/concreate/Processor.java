package concreate;

import TCP.TcpServer;

import java.net.Socket;

public class Processor extends TcpServer
{
    @Override
    public Runnable getClientHandler(Socket clientSocket)
    {
        return new ProcessorClientHandler(clientSocket);
    }
}