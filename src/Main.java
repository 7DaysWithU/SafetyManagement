import concreate.Detector;
import concreate.Processor;

import java.io.IOException;


/**
 * 启动服务端及客户端的入口类
 */
public class Main
{
    /**
     * 启动服务端和多个客户端
     *
     * @param args 命令行输入参数
     * @throws InterruptedException 打断异常, 用于处理Socket通信异常关闭的情况
     */
    public static void main(String[] args) throws InterruptedException
    {
        System.out.println("Hello and welcome!");

        Thread server = new StartProcessor();
        Thread client1 = new StartDetector();
        Thread client2 = new StartDetector();

        // 启动服务端
        server.start();
        // 适当等待, 避免客户端启动过快且服务端仍未启动导致的无法连接问题
        Thread.sleep(1000);

        // 启动客户端
        client1.start();
        client2.start();
    }
}

/**
 * 服务端启动类
 */
class StartProcessor extends Thread
{
    /**
     * 线程方法, 启动一个线程运行服务端
     */

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

/**
 * 客户端启动类
 */
class StartDetector extends Thread
{
    /**
     * 线程方法, 启动一个线程运行客户端
     */
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
