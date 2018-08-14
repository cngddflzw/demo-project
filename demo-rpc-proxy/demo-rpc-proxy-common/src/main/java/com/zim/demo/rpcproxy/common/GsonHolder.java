package com.zim.demo.rpcproxy.common;

import com.google.gson.Gson;

/**
 * @author zhenwei.liu
 * @since 2018-08-14
 */
public class GsonHolder {

    private static final Gson INSTANCE = new Gson();

    public static Gson gson() {
        return INSTANCE;
    }
}
