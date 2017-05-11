package com.github.developframework.jsonview.core.exception;

/**
 * 模板包未定义异常
 * @author qiuzhenhao
 * @date 2017/5/6
 */
public class TemplatePackageUndefinedException extends JsonviewException{

    public TemplatePackageUndefinedException(String namespace) {
        super("The template package \"%s\" is undefined.", namespace);
    }
}
