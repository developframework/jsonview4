package com.github.developframework.jsonview.core.exception;

/**
 * 一对一链接数组大小不一致异常
 * @author qiuzhenhao
 * @date 2017/5/9
 */
public class OneToOneSizeNotEqualException extends JsonviewException{

    public OneToOneSizeNotEqualException(String elementName, String namespace, String templateId) {
        super("The element %s size is not equals parent array size in package \"%s\" template \"%s\"", elementName, namespace, templateId);
    }
}
