package com.zim.demo.rpcproxy.sidecar.common;

import com.alibaba.fastjson.JSON;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class JsonResponseParser implements ResponseParser<Object> {

    @Override
    public String parse(Object o) {
        return JSON.toJSONString(o);
    }
}
