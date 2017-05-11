package com.github.developframework.jsonview.core.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Jsonview 异常
 * @author qiuzhenhao
 * @date 2017/5/6
 */
@Slf4j
public class JsonviewException extends RuntimeException{

    public JsonviewException(String message) {
        super(message);
        log.error(super.getMessage());
    }

    public JsonviewException(String format, Object... objs) {
        super(String.format(format, objs));
        log.error(super.getMessage());
    }
}
