package com.zim.demo.rpcproxy.api.impl;

import com.zim.demo.rpcproxy.api.MethodInfo;
import java.util.List;

/**
 * @author zhenwei.liu
 * @since 2018-08-14
 */
public class DefaultMethodInfo implements MethodInfo {

    private List<String> paramTypeList;

    @Override
    public List<String> paramTypeList() {
        return paramTypeList;
    }

    public List<String> getParamTypeList() {
        return paramTypeList;
    }

    public DefaultMethodInfo setParamTypeList(List<String> paramTypeList) {
        this.paramTypeList = paramTypeList;
        return this;
    }
}
