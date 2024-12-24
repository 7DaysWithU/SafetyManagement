package concreate;

import TCP.ClientHandler;
import TCP.custom_interface.Protocol;

import java.net.Socket;


/**
 * 监测服务器的客户端线程处理器
 */
class ProcessorClientHandler extends ClientHandler implements Protocol
{

    /**
     * 构造方法, 设置客户端连接
     *
     * @param clientSocket 客户端连接
     */
    public ProcessorClientHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    /**
     * 接收并处理客户端消息, 并向客户端返回服务端消息
     *
     * @param clientMessage 客户端消息
     * @return 服务端消息
     */
    @Override
    public String processMessage(String clientMessage)
    {
        // 用于测试的一个例子
        System.out.println("服务端收到了客户端发送的:" + clientMessage);
        String returnMessage = "[" + clientMessage + "]";
        System.out.println("服务端要返回给客户端: " + returnMessage);
        return returnMessage;
    }

    /**
     * 将数据封装成协议数据报
     *
     * @param state 状态数据
     * @return 协议数据报
     */
    @Override
    public String protocolPacking(Object state)
    {
        return null;
    }

    /**
     * 解包协议数据报获得信息
     *
     * @param protocolMessage 协议数据报
     * @return 状态数据
     */
    @Override
    public Object protocolUnpacking(String protocolMessage)
    {
        return null;
    }
}