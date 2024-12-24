package TCP;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * 客户端线程处理器
 */
public abstract class ClientHandler implements Runnable
{
    /**
     * 客户端连接
     */
    private final Socket clientSocket;

    /**
     * 构造方法, 设置客户端连接
     *
     * @param clientSocket 客户端连接
     */
    public ClientHandler(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    /**
     * 线程方法, 运行线程为该客户端连接提供服务端消息接收和服务端消息返回服务
     */
    @Override
    public void run()
    {
        try (
                DataInputStream input = new DataInputStream(this.clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(this.clientSocket.getOutputStream())
        )
        {
            while (true)
            {
                String clientMessage = input.readUTF();
                if (clientMessage.equals("EOF"))
                {
                    break;
                }

                String returnMessage = this.processMessage(clientMessage);
                output.writeUTF(returnMessage);
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 接收并处理客户端消息, 并向客户端返回服务端消息
     *
     * @param clientMessage 客户端消息
     * @return 服务端消息
     */
    public abstract String processMessage(String clientMessage);
}
