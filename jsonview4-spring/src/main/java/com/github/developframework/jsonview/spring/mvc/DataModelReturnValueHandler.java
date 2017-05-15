package com.github.developframework.jsonview.spring.mvc;

import com.github.developframework.jsonview.core.JsonviewFactory;
import com.github.developframework.jsonview.core.data.DataModel;
import org.springframework.core.MethodParameter;

/**
 * 处理DataModel的ReturnValueHandler
 *
 * @author qiuzhenhao
 * @date 2017/5/11
 */
public final class DataModelReturnValueHandler extends AnnotationJsonviewReturnValueHandler<DataModel> {

    public DataModelReturnValueHandler(JsonviewFactory jsonviewFactory) {
        super(jsonviewFactory);
    }

    @Override
    protected Class<DataModel> returnType() {
        return DataModel.class;
    }

    @Override
    protected DataModel dataModel(DataModel returnValue, MethodParameter methodParameter) {
        return returnValue;
    }
}
