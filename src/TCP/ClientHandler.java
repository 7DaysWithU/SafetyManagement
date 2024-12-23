package TCP;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public abstract class ClientHandler implements Runnable
{
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

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

    public abstract String processMessage(String clientMessage);
}
