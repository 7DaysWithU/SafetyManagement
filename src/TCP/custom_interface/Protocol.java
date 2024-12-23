package TCP.custom_interface;

public interface Protocol
{
    String protocolPacking(Object state);

    Object protocolUnpacking(String protocolMessage);
}
