import concreate.Detector;
import concreate.Processor;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        System.out.println("Hello and welcome!");

        Thread t1 = new StartProcessor();
        Thread t2 = new StartDetector();
        Thread t3 = new StartDetector();

        // 启动服务端
        t1.start();
        Thread.sleep(1000);

        // 启动客户端
        t2.start();
        t3.start();
    }
}

class StartProcessor extends Thread
{
    @Override
    public void run()
    {
        Processor processor = new Processor();
        try
        {
            processor.server("localhost", 8080);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

class StartDetector extends Thread
{
    @Override
    public void run()
    {
        Detector detector = new Detector();
        try
        {
            detector.client("localhost", 8080);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
