package TCP;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 服务端 TCP通信基类
 */
public abstract class TcpServer
{
    /**
     * 服务端通信方法, 完成服务端接收客户端消息, 返回客户端消息的功能
     *
     * @param host 监听主机
     * @param port 监听端口
     * @throws IOException IO异常, 用于处理通信时的 IO刘异常问题
     */
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

    /**
     * 为每个连接的客户端生成线程处理器
     * 每个客户端连接后会调用本方法生成一个独立的线程为该客户端服务
     *
     * @param clientSocket 客户端连接
     * @return 客户端线程处理器
     */
    public abstract Runnable getClientHandler(Socket clientSocket);
}