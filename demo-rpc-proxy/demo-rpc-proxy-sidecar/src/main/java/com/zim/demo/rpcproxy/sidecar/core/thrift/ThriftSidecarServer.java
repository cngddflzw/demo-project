package com.zim.demo.rpcproxy.sidecar.core.thrift;

import com.zim.demo.rpcproxy.api.thrift.ProxyService.Processor;
import javax.annotation.Resource;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 接收异构语言 thrift 调用请求
 *
 * @author zhenwei.liu
 * @since 2018-08-14
 */
@Service
public class ThriftSidecarServer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftSidecarServer.class);
    private static final int MIN_WORKER_THREAD = Runtime.getRuntime().availableProcessors() * 2;

    @Value("${sidecar.thrift.server.port}")
    private int port;

    @Resource
    private ThriftRequestHandler handler;

    public void startup() {
        try {
            Processor processor = new Processor(handler);
            TServerTransport serverTransport = new TServerSocket(port);
            Args args = new Args(serverTransport);
            args.minWorkerThreads(MIN_WORKER_THREAD);
            TServer server = new TThreadPoolServer(args.processor(processor));
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("Thrift sidecar server start up, bind port {}", port);
                    server.serve();
                }
            });
            t.setName("thrift-sidecar-server");
            t.start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() {
        startup();
    }
}
