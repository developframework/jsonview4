package com.github.developframework.jsonview.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.github.developframework.jsonview.core.data.DataModel;

/**
 * json生成器接口
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public interface JsonProducer {

    /**
     * 创建json
     * @param dataModel 数据模型
     * @param namespace 命名空间
     * @param templateId 模板ID
     * @return
     */
    String createJson(DataModel dataModel, String namespace, String templateId);

    /**
     * 创建json
     * @param dataModel 数据模型
     * @param namespace 命名空间
     * @param templateId 模板ID
     * @param isPretty 是否美化
     * @return
     */
    String createJson(DataModel dataModel, String namespace, String templateId, boolean isPretty);

    /**
     * 从JsonGenerator输出json
     * @param jsonGenerator JsonGenerator
     * @param dataModel 数据模型
     * @param namespace 命名空间
     * @param templateId 模板ID
     */
    void printJson(JsonGenerator jsonGenerator, DataModel dataModel, String namespace, String templateId);
}
