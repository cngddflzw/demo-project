package com.zim.demo.rpcproxy.heterogeneous;

import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.tools.InvocationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<InvocationResult> handleException(Exception ex) {
        LOGGER.error("handle request error", ex);
        InvocationResult result = InvocationUtils.createFailedResult(ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
