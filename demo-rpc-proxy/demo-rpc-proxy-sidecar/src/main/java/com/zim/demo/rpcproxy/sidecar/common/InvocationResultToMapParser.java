package com.zim.demo.rpcproxy.sidecar.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zim.demo.rpcproxy.api.InvocationResult;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class InvocationResultToMapParser implements
        ResponseParser<InvocationResult, Map<String, Object>> {

    private static final Type TYPE = new TypeReference<Map<String, Object>>() {
    }.getType();

    @Override
    public Map<String, Object> parse(InvocationResult input) {
        return JSON.parseObject(input.data().toString(), TYPE);
    }

    // TODO 测试一下多层级 json 转 map
}
