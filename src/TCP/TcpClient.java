package TCP;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * 客户端 TCP通信基类
 */
public abstract class TcpClient
{
    /**
     * 客户端通信方法, 完成客户端消息发送, 接收服务端返回消息的功能
     *
     * @param host 监听主机
     * @param port 监听端口
     * @throws IOException IO异常, 用于处理通信时的 IO刘异常问题
     */
    public void client(String host, int port) throws IOException
    {
        try (Socket socket = new Socket(host, port))
        {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            while (true)
            {
                String sendingMessage = this.sendMessage();
                output.writeUTF(sendingMessage);
                if (sendingMessage.equals("EOF"))
                {
                    break;
                }

                String returnedMessage = input.readUTF();
                this.processMessage(returnedMessage);
            }

            input.close();
            output.close();
        }
    }

    /**
     * 客户端发送消息方法, 用以获取客户端要发送给服务端的消息
     *
     * @return 客户端要发送给服务端的消息
     */
    public abstract String sendMessage();


    /**
     * 客户端处理服务端返回消息的方法, 用以处理服务端返回的消息
     *
     * @param serverMessage 服务端返回的消息
     */
    public abstract void processMessage(String serverMessage);
}