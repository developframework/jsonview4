package com.github.developframework.jsonview.core.exception;

/**
 * 数据未定义异常
 * @author qiuzhenhao
 */
public class DataUndefinedException extends JsonviewException{

    public DataUndefinedException(String dataName) {
        super("Data \"%s\" is undefined in DataModel.", dataName);
    }
}
