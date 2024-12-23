package TCP;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public abstract class TcpClient
{
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

    public abstract String sendMessage();

    public abstract void processMessage(String serverMessage);
}