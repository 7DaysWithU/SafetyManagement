package concreate;

import TCP.TcpServer;

import java.net.Socket;


/**
 * 监测处理器, 实现服务端功能
 */
public class Processor extends TcpServer
{
    /**
     * 为每个连接的客户端生成线程处理器
     * 每个客户端连接后会调用本方法生成一个独立的线程为该客户端服务
     *
     * @param clientSocket 客户端连接
     * @return 客户端线程处理器
     */
    @Override
    public Runnable getClientHandler(Socket clientSocket)
    {
        return new ProcessorClientHandler(clientSocket);
    }
}