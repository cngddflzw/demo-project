package com.zim.demo.rpcproxy.api.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author zhenwei.liu
 * @since 2018-08-14
 */
public class ThriftServiceClientFactory {

    public static ProxyService.Client create(String address, int port)
            throws TTransportException {
        TTransport transport;
        transport = new TSocket(address, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        return new ProxyService.Client(protocol);
    }
}
