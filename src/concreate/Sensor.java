package concreate;

import java.util.Random;
public class Sensor extends Thread {
    //创建传感器对象默认分配一个id
    public Sensor(){
        this.start();
    }
    //检测值
    private int detectnum;

    //0-100的随机整数
    public int getRandom() {
        Random random = new Random();
        return random.nextInt(256);
    }

    //为detectnum设置随机数
    public void setDetectnum(int detectnum) {
        this.detectnum = detectnum;
    }

    public int getDetectnum(){
        return detectnum;
    }

    public void run(){
        while(true){
            this.setDetectnum(this.getRandom());
            //传感器每0.5秒更新数据
            try {
                Thread.sleep(500); // 暂停4000毫秒（4秒）
            } catch (InterruptedException e) {
                // 当前线程在睡眠期间被中断，可以在这里处理中断情况
                e.printStackTrace();

            }
        }
    }
}
