package concreate;

import TCP.TcpClient;
import TCP.custom_interface.Protocol;
import TCP.custom_interface.State;


/**
 * 环境监测器, 实现客户端功能
 */
public class Detector extends TcpClient implements State, Protocol
{

    /**
     * 客户端发送消息方法, 用以获取客户端要发送给服务端的消息
     *
     * @return 客户端要发送给服务端的消息
     */
    @Override
    public String sendMessage()
    {
        // 用于测试的一个例子
        try
        {
            Thread.sleep(10000);
        } catch (InterruptedException ignored)
        {
        }

        String message = "114514" + "[" + Thread.currentThread().getName() + "]";
        System.out.println("客户端要发送: " + message);
        return message;
    }

    /**
     * 客户端处理服务端返回消息的方法, 用以处理服务端返回的消息
     *
     * @param serverMessage 服务端返回的消息
     */
    @Override
    public void processMessage(String serverMessage)
    {
        System.out.println("客户端收到了服务端返回的: " + serverMessage);
    }

    /**
     * 设置状态
     *
     * @param newState 新的状态
     */
    @Override
    public void setState(Object newState)
    {

    }

    /**
     * 获得状态
     *
     * @return 状态
     */
    @Override
    public Object getState()
    {
        return null;
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