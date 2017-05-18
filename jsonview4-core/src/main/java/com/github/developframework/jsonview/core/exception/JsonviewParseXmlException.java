package com.github.developframework.jsonview.core.exception;

/**
 * 解析XML异常
 * @author qiuzhenhao
 */
public class JsonviewParseXmlException extends JsonviewException{

    public JsonviewParseXmlException(String format, Object... objs) {
        super(format, objs);
    }
}
