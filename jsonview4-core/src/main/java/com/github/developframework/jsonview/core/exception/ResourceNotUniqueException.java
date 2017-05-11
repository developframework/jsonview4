package com.github.developframework.jsonview.core.exception;

/**
 * 资源定义不唯一异常
 * @author qiuzhenhao
 * @date 2017/5/6
 */
public class ResourceNotUniqueException extends JsonviewException{

    public ResourceNotUniqueException(String resourceName, String name) {
        super("%s \"%s\" have been defined.", resourceName, name);
    }
}
