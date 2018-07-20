package com.zim.demo.rpcproxy.api;

import java.util.List;

/**
 * 泛化调用参数
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public interface Invocation {

    String interfaceName();

    String group();

    String version();

    String methodName();

    List<String> paramTypes();

    List<Object> paramVals();
}
