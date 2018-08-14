package com.zim.demo.rpcproxy.sidecar.core.thrift;

import com.google.common.base.Suppliers;
import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.thrift.ProxyService.Client;
import com.zim.demo.rpcproxy.api.thrift.Request;
import com.zim.demo.rpcproxy.api.thrift.ThriftServiceClientFactory;
import com.zim.demo.rpcproxy.api.tools.InvocationUtils;
import com.zim.demo.rpcproxy.common.GsonHolder;
import com.zim.demo.rpcproxy.sidecar.common.RequestParser;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.core.AbstractRequestSender;
import com.zim.demo.rpcproxy.sidecar.exception.Java2HeterogeneousException;
import java.util.Optional;
import java.util.function.Supplier;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhenwei.liu
 * @since 2018-08-13
 */
@Service
public class ThriftRequestSender extends AbstractRequestSender<Request, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftRequestSender.class);

    @Value("${heterogeneous.request.rpc.address}")
    private String rpcAddress;

    @Value("${heterogeneous.request.rpc.port}")
    private int rpcPort;

    // thrift client 并不是 thread-safe 的
    private ThreadLocal<Optional<Client>> clientTL = new ThreadLocal<Optional<Client>>() {

        @Override
        protected Optional<Client> initialValue() {
            try {
                return Optional.of(ThriftServiceClientFactory.create(rpcAddress, rpcPort));
            } catch (TTransportException e) {
                LOGGER.error("create thrift client error", e);
                return Optional.empty();
            }
        }
    };

    private Supplier<RequestParser<Invocation, Request>> requestParserSupplier = Suppliers.memoize(
            Suppliers.ofInstance(new RequestParser<Invocation, Request>() {
                @Override
                public Request parse(Invocation inv) {
                    Request request = new Request();
                    request.setClass_name(inv.interfaceName());
                    request.setFunc_name(inv.methodName());
                    request.setParam(GsonHolder.gson().toJson(inv.paramVals()));
                    return request;
                }
            })
    );

    private Supplier<ResponseParser<String, InvocationResult>> responseParserSupplier = Suppliers
            .memoize(Suppliers.ofInstance(new ResponseParser<String, InvocationResult>() {
                        @Override
                        public InvocationResult parse(String result) {
                            return InvocationUtils.createSuccessfulResult(result);
                        }
                    })
            );

    @Override
    protected String doSend(Request request) throws TException {
        Optional<Client> op = clientTL.get();
        if (op.isPresent()) {
            return op.get().Invoke(request);
        } else {
            throw new Java2HeterogeneousException("get thrift client error");
        }
    }

    @Override
    protected RequestParser<Invocation, Request> getRequestParser() {
        return requestParserSupplier.get();
    }

    @Override
    protected ResponseParser<String, InvocationResult> getResponseParser() {
        return responseParserSupplier.get();
    }
}
