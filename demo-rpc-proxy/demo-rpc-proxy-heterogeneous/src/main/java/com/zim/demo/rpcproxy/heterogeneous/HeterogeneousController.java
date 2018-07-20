package com.zim.demo.rpcproxy.heterogeneous;

import com.google.common.base.Preconditions;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocation;
import com.zim.demo.rpcproxy.api.tools.InvocationUtils;
import java.lang.reflect.Method;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异构语言提供给 sidecar 的 http 接口
 *
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@RestController
@RequestMapping("/heterogeneous")
public class HeterogeneousController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @PostMapping("/invoke")
    public Object invoke(@Valid @RequestBody DefaultInvocation invocation)
            throws Exception {
        String clazzName = invocation.getInterfaceName();
        Object bean = applicationContext.getBean(Class.forName(clazzName));

        String methodName = invocation.getMethodName();
        Class[] classes = invocation.getParamTypes().stream().map(e -> {
            try {
                return Class.forName(e);
            } catch (ClassNotFoundException e1) {
                return null;
            }
        }).filter(Objects::nonNull).toArray(Class[]::new);
        Preconditions.checkState(classes.length == invocation.getParamTypes().size());

        Method method = bean.getClass().getMethod(methodName, classes);

        Object[] args = invocation.getParamVals().toArray();
        Object data = method.invoke(bean, args);
        return InvocationUtils.createSuccessfulResult(data);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
