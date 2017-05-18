package com.github.developframework.jsonview.spring.mvc;

import com.github.developframework.jsonview.core.JsonviewFactory;
import com.github.developframework.jsonview.core.data.DataModel;
import com.github.developframework.jsonview.spring.mvc.response.JsonviewResponse;
import org.springframework.core.MethodParameter;

/**
 * 处理JsonviewResponse的ReturnValueHandler
 *
 * @author qiuzhenhao
 */
public final class JsonviewResponseReturnValueHandler extends AbstractJsonviewReturnValueHandler<JsonviewResponse> {

    public JsonviewResponseReturnValueHandler(JsonviewFactory jsonviewFactory) {
        super(jsonviewFactory);
    }

    @Override
    protected Class<JsonviewResponse> returnType() {
        return JsonviewResponse.class;
    }

    @Override
    protected String namespace(JsonviewResponse returnValue, MethodParameter methodParameter) {
        return returnValue.getNamespace();
    }

    @Override
    protected String templateId(JsonviewResponse returnValue, MethodParameter methodParameter) {
        return returnValue.getTemplateId();
    }

    @Override
    protected DataModel dataModel(JsonviewResponse returnValue, MethodParameter methodParameter) {
        return returnValue.getDataModel();
    }
}
