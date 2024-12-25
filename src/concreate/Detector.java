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
    /**
     * 如果一氧化碳检测值
     */
    //二氧化碳传感器
    private Sensor s1;
    //水位传感器
    private Sensor s2;
    //风扇
    private Equipment e1;
    //排水设备
    private Equipment e2;
    //一个detector管理2个传感器，2个设备
    public Detector(){
        s1 = new Sensor();
        s2 = new Sensor();
        e1 = new Equipment();
        e2 = new Equipment();
    }
    //向客户端发送信息的间隔时间
    int SleepTime = 5000;

    public void setSleepTime(int SleepTime){
        this.SleepTime = SleepTime;
    }
    //合成所有的信息
    public String Conformity_Message(){
        //8位
        String s1infor = convertTo8BitBinary(s1.getDetectnum());
        //8位
        String s2infor = convertTo8BitBinary(s2.getDetectnum());
        //1位
        String e1infor = e1.getSwitch();
        //1位
        String e2infor = e2.getSwitch();
        String totalMessage = s1infor + s2infor + e1infor + e2infor;
        // 计算CRC-8校验码
        int crc = calculateCRC8(totalMessage);
        // 将CRC校验码转换为8位二进制字符串
        String crcBinary = convertTo8BitBinary(crc);
        return totalMessage + crcBinary;
    }

    //将信息转换成8位2进制
    public static String convertTo8BitBinary(int number) {
        if (number < 0 || number > 255) {
            throw new IllegalArgumentException("Number must be between 0 and 100");
        }
        String binaryString = Integer.toBinaryString(number);
        // 使用String.format()方法来确保二进制字符串是8位的，不足的部分在前面补零
        return String.format("%8s", binaryString).replace(' ', '0');
    }

    public int calculateCRC8(String binaryString) {
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


    @Override
    public String sendMessage()
    {
        // 用于测试的一个例子
        try
        {
            Thread.sleep(SleepTime);
        } catch (InterruptedException ignored)
        {
        }

        String message = Conformity_Message();
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
        if(this.check(serverMessage)){
            String se1 = serverMessage.substring(16,17);
            String se2 = serverMessage.substring(17,18);
            if(se1.equals("1")&!e1.getSwitch().equals(se1)){
                e1.setSwitch(se1);
                this.setSleepTime(1000);
                System.out.println("二氧化碳浓度高，风扇已打开");

            } else if (se1.equals("0")&!e1.getSwitch().equals(se1)) {
                e1.setSwitch(se1);
                System.out.println("二氧化碳浓度正常，风扇关闭");
                this.setSleepTime(5000);
            }

            if(se2.equals("1")&!e2.getSwitch().equals(se2)){
                e2.setSwitch(se2);
                System.out.println("水位升高，打开排水设备");
            } else if (se2.equals("0")&!e2.getSwitch().equals(se2)) {
                e2.setSwitch(se2);
                System.out.println("水位正常，关闭排水设备");
            }
        }else{

        }
    }

    public  Boolean check(String receivedBinary) {
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

    // 验证接收到的二进制码是否正确的方法
    public boolean verifyMessage(String receivedBinary) {
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
     * 将数据处理过后封装成协议数据报
     *
     * @param state 状态数据
     * @return 协议数据报
     */
    @Override
    public String protocolPacking(Object state)
    {
        return Conformity_Message();
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