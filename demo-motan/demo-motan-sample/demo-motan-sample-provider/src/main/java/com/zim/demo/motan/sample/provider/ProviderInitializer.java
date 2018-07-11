package com.zim.demo.motan.sample.provider;

import static com.weibo.api.motan.util.MotanSwitcherUtil.*;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

/**
 * @author zhenwei.liu
 * @since 2018-07-11
 */
public class ProviderInitializer {

    public static void init() {
        setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }
}
