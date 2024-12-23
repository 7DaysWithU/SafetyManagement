package concreate;

import TCP.ClientHandler;
import TCP.custom_interface.Protocol;

import java.net.Socket;

class ProcessorClientHandler extends ClientHandler implements Protocol
{

    public ProcessorClientHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    @Override
    public String processMessage(String clientMessage)
    {
        System.out.println("服务端收到了客户端发送的:" + clientMessage);
        String returnMessage = "[" + clientMessage + "]";
        System.out.println("服务端要返回给客户端: " + returnMessage);
        return returnMessage;
    }

    @Override
    public String protocolPacking(Object state)
    {
        return null;
    }

    @Override
    public Object protocolUnpacking(String protocolMessage)
    {
        return null;
    }
}