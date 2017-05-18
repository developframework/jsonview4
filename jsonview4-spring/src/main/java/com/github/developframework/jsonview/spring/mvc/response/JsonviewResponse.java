package com.github.developframework.jsonview.spring.mvc.response;

import com.github.developframework.jsonview.core.data.DataModel;
import lombok.Getter;

/**
 * Jsonview的响应对象
 *
 * @author qiuzhenhao
 */
@Getter
public class JsonviewResponse {

    /* 命名空间 */
    protected String namespace;
    /* 模板ID */
    protected String templateId;
    /* 数据模型 */
    protected DataModel dataModel;

    public JsonviewResponse(String namespace, String templateId, DataModel dataModel) {
        this.namespace = namespace;
        this.templateId = templateId;
        this.dataModel = dataModel;
    }

    /**
     * 放入数据
     *
     * @param dataName 数据名称
     * @param data     数据值
     * @return 对象自己
     */
    public JsonviewResponse data(String dataName, Object data) {
        dataModel.putData(dataName, data);
        return this;
    }
}
