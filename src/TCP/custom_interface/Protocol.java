package TCP.custom_interface;

/**
 * 协议数据报接口, 用以实现协议数据报的有关操作
 */
public interface Protocol
{
    /**
     * 将数据封装成协议数据报
     *
     * @param state 状态数据
     * @return 协议数据报
     */
    String protocolPacking(Object state);

    /**
     * 解包协议数据报获得信息
     *
     * @param protocolMessage 协议数据报
     * @return 状态数据
     */
    Object protocolUnpacking(String protocolMessage);
}
