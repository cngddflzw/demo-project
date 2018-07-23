package com.zim.demo.rpcproxy.sidecar.common;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@Component
public class JsonSerializer implements Serializer<String> {

    @Override
    public String serialize(Object o) {
        return JSON.toJSONString(o);
    }
}
