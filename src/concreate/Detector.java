package concreate;

import TCP.TcpClient;
import TCP.custom_interface.Protocol;
import TCP.custom_interface.State;

public class Detector extends TcpClient implements State, Protocol
{

    @Override
    public String sendMessage()
    {
        try
        {
            Thread.sleep(10000);
        } catch (InterruptedException ignored)
        {
        }

        String message = "114514";
        System.out.println("客户端要发送: " + message);
        return message;
    }

    @Override
    public void processMessage(String serverMessage)
    {
        System.out.println("客户端收到了服务端返回的: " + serverMessage + "\n");
    }

    @Override
    public void setState(Object newState)
    {

    }

    @Override
    public Object getState()
    {
        return null;
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