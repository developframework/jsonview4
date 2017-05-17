package com.github.developframework.jsonview.core.exception;

/**
 * 一对一链接数组大小不一致异常
 * @author qiuzhenhao
 */
public class LinkSizeNotEqualException extends JsonviewException{

    public LinkSizeNotEqualException(String namespace, String templateId) {
        super("The element <link> size is not equals parent array size in package \"%s\" template \"%s\"", namespace, templateId);
    }
}
