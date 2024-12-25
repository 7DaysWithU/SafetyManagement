package concreate;

import TCP.ClientHandler;
import TCP.custom_interface.Protocol;

import java.net.Socket;


/**
 * 监测服务器的客户端线程处理器
 */
class ProcessorClientHandler extends ClientHandler implements Protocol
{

    /**
     * 构造方法, 设置客户端连接
     *
     * @param clientSocket 客户端连接
     */
    public ProcessorClientHandler(Socket clientSocket)
    {
        super(clientSocket);
    }

    /**
     * 接收并处理客户端消息, 并向客户端返回服务端消息
     *
     * @param clientMessage 客户端消息
     * @return 服务端消息
     */
    @Override
    public String processMessage(String clientMessage)
    {
        // 用于测试的一个例子
        System.out.println("服务端收到了：" + clientMessage);
        //校验接受信息是否正确
        if(this.check(clientMessage)){
            //数据信息
            String messagePart = clientMessage.substring(0, clientMessage.length() - 8);
            String s1 = messagePart.substring(0, 8);
            String s2 = messagePart.substring(8,16);
            String e1 = messagePart.substring(16,17);
            String e2 = messagePart.substring(17,18);
            String processMessage = this.generater_Return_message(s1,s2,e1,e2);


            //计算校验码
            int crc = calculateCRC8(processMessage);
            String crcBinary = convertTo8BitBinary(crc);

            String returnMessage = processMessage + crcBinary;

            System.out.println("服务端返回信息: " + returnMessage);
            return returnMessage;
        }
        else{
            return null;
        }


    }

    public String generater_Return_message(String s1,String s2,String e1,String e2){
        String newe1 = null;
        String newe2 = null;
        int s1num = Integer.parseInt(s1, 2);
        int s2num = Integer.parseInt(s2, 2);
        int e1num = Integer.parseInt(e1, 2);
        int e2num = Integer.parseInt(e2, 2);

        //检测二氧化碳传感器
        if(s1num >= 122){
            newe1 = "1";
        }else{
            newe1 = "0";
        }

        //检测水位传感器
        if(s2num >= 250){
            newe2 = "1";
        }else {
            newe2 = "0";
        }
        return s1+s2+newe1+newe2;
    }

    // 将信息转换成8位2进制，和原来代码中的方法功能一致
    public static String convertTo8BitBinary(int number) {
        if (number < 0 || number > 255) {
            throw new IllegalArgumentException("Number must be between 0 and 255");
        }
        String binaryString = Integer.toBinaryString(number);
        return String.format("%8s", binaryString).replace(' ', '0');
    }

    // 计算CRC8校验码，和原来代码中的方法功能一致
    public static int calculateCRC8(String binaryString) {
        // CRC-8多项式，这里使用x^8 + x^2 + x + 1，即0x31
        int POLYNOMIAL = 0x31;
        int INITIAL_VALUE = 0x00;
        int crc = INITIAL_VALUE;
        for (int i = 0; i < binaryString.length(); i++) {
            boolean bit = binaryString.charAt(i) == '1';
            crc ^= (bit ? 1 : 0); // 如果当前位是1，则异或当前CRC值
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x80) != 0) { // 检查最高位是否为1
                    crc = (crc << 1) ^ POLYNOMIAL; // 左移并异或多项式
                } else {
                    crc <<= 1; // 仅左移
                }
                crc &= 0xFF; // 确保CRC值在0-255范围内
            }
        }
        return crc;
    }

    // 验证接收到的二进制码是否正确的方法
    public static boolean verifyMessage(String receivedBinary) {
        // 分离出信息部分和接收到的CRC校验码部分
        String messagePart = receivedBinary.substring(0, receivedBinary.length() - 8);
        String receivedCRC = receivedBinary.substring(receivedBinary.length() - 8);

        // 重新计算信息部分的CRC校验码
        int calculatedCRC = calculateCRC8(messagePart);

        // 将重新计算的CRC校验码转换为8位二进制字符串
        String calculatedCRC8Binary = convertTo8BitBinary(calculatedCRC);
        // 比较重新计算的CRC校验码和接收到的CRC校验码是否一致
        return calculatedCRC8Binary.equals(receivedCRC);
    }

    public  Boolean check(String receivedBinary ) {
        // 这里模拟接收到的二进制码，实际应用中应该是从网络接收等其他途径获取
        boolean isValid = verifyMessage(receivedBinary);
        if (isValid) {
            System.out.println("接收到的二进制码验证通过，没有问题。");
            return true;
        } else {
            System.out.println("接收到的二进制码验证不通过，可能出现了错误。");
            return false;
        }
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