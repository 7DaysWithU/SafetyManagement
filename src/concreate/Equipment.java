package concreate;


//联动设备，Switch为0是关闭，为1是启动
public class Equipment {
    private String Switch;

    public Equipment() {
        Switch = "0";
    }

    public String getSwitch() {
        return Switch;
    }

    public void setSwitch(String aSwitch) {
        Switch = aSwitch;
    }
}
