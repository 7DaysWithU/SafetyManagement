package TCP.custom_interface;

/**
 * 状态接口, 实现有关状态的操作方法
 */
public interface State
{
    /**
     * 设置状态
     *
     * @param newState 新的状态
     */
    void setState(Object newState);

    /**
     * 获得状态
     *
     * @return 状态
     */
    Object getState();
}
