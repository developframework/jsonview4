package com.github.developframework.jsonview.core.exception;

/**
 * 无效的参数异常
 * @author qiuzhenhao
 */
public class InvalidArgumentsException extends JsonviewException{

    public InvalidArgumentsException(String attributeName, String argumentName, String hint) {
        super("Invalid arguments \"%s\" in attribute \"%s\": %s", argumentName, attributeName, hint);
    }
}
