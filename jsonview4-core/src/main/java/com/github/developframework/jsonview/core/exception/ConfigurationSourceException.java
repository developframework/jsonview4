package com.github.developframework.jsonview.core.exception;

/**
 * 配置源异常
 * @author qiuzhenhao
 */
public class ConfigurationSourceException extends JsonviewException{

    public ConfigurationSourceException(String source) {
        super("The jsonview configuration source \"\" is not found.", source);
    }
}
