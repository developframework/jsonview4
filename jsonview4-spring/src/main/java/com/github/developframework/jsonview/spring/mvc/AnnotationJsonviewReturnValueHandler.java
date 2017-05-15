package com.github.developframework.jsonview.spring.mvc;

import com.github.developframework.jsonview.core.JsonviewFactory;
import com.github.developframework.jsonview.core.data.DataModel;
import com.github.developframework.jsonview.core.exception.JsonviewException;
import com.github.developframework.jsonview.spring.mvc.AbstractJsonviewReturnValueHandler;
import com.github.developframework.jsonview.spring.mvc.annotation.JsonviewNamespace;
import com.github.developframework.jsonview.spring.mvc.annotation.TemplateId;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 基于注解的ReturnValueHandler
 * @author qiuzhenhao
 * @date 2017/5/11
 */
public abstract class AnnotationJsonviewReturnValueHandler<T> extends AbstractJsonviewReturnValueHandler <T>{


    public AnnotationJsonviewReturnValueHandler(JsonviewFactory jsonviewFactory) {
        super(jsonviewFactory);
    }

    @Override
    protected String namespace(T returnValue, MethodParameter methodParameter) {
        if (methodParameter.hasMethodAnnotation(JsonviewNamespace.class)) {
            return methodParameter.getMethodAnnotation(JsonviewNamespace.class).value();
        } else {
            final JsonviewNamespace annotation = AnnotationUtils.findAnnotation(methodParameter.getContainingClass(), JsonviewNamespace.class);
            if (annotation != null) {
                return annotation.value();
            } else {
                throw new JsonviewException("@JsonviewNamespace is not found in Class \"%s\" with Method \"%s\".", methodParameter.getContainingClass(), methodParameter.getMethod().getName());
            }
        }
    }

    @Override
    protected String templateId(T returnValue, MethodParameter methodParameter) {

        if (methodParameter.hasMethodAnnotation(TemplateId.class)) {
            return methodParameter.getMethodAnnotation(TemplateId.class).value();
        } else {
            throw new JsonviewException("@TemplateId is not found in Class \"%s\" with Method \"%s\".", methodParameter.getContainingClass(), methodParameter.getMethod().getName());
        }
    }
}
