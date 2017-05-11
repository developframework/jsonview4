package com.github.developframework.jsonview.core.exception;

/**
 * 配置源异常
 * @author qiuzhenhao
 * @date 2017/5/7
 */
public class ConfigurationSourceException extends JsonviewException{

    public ConfigurationSourceException(String source) {
        super("The jsonview configuration source \"\" is not found.", source);
    }
}
