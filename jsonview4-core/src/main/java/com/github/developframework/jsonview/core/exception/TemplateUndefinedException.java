package com.github.developframework.jsonview.core.exception;

/**
 * 模板未定义异常
 * @author qiuzhenhao
 * @date 2017/5/6
 */
public class TemplateUndefinedException extends JsonviewException{

    public TemplateUndefinedException(String namespace, String templateId) {
        super("The template \"%s\" is undefined in template-package \"%s\".", templateId, namespace);
    }
}
