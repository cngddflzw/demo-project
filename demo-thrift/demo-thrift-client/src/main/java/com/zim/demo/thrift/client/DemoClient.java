package com.zim.demo.thrift.client;

import com.alibaba.fastjson.JSON;
import com.zim.demo.thrift.api.GenericService;
import com.zim.demo.thrift.api.Request;
import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author zhenwei.liu
 * @since 2018-08-08
 */
public class DemoClient {

    private static class Param {

        private String name;
        private int code;

        public String getName() {
            return name;
        }

        public Param setName(String name) {
            this.name = name;
            return this;
        }

        public int getCode() {
            return code;
        }

        public Param setCode(int code) {
            this.code = code;
            return this;
        }
    }

    public static void main(String[] args) {

        try {
            TTransport transport = new TSocket("localhost", 9090);
            transport.open();

            List<Param> paramList = new ArrayList<>();
            paramList.add(new Param().setName("Zim").setCode(18));
            paramList.add(new Param().setName("Neo").setCode(28));

            TProtocol protocol = new TBinaryProtocol(transport);
            GenericService.Client client = new GenericService.Client(protocol);
            Request request = new Request();
            request.setClass_name("DemoServiceWrapper");
            request.setFunc_name("Test2");
            request.setParam(JSON.toJSONString(paramList));

            String result = client.Invoke(request);
            System.out.println(result);

            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }
}
