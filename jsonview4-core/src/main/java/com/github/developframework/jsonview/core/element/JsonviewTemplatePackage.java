package com.github.developframework.jsonview.core.element;

import com.github.developframework.jsonview.core.exception.ResourceNotUniqueException;
import com.github.developframework.jsonview.core.exception.TemplateUndefinedException;
import lombok.Getter;

import java.util.HashMap;

/**
 * Jsonview 模板包
 * @author qiuzhenhao
 * @date 2017/5/6
 */
public class JsonviewTemplatePackage extends HashMap<String, JsonviewTemplate>{

    /* 命名空间 */
    @Getter
    private String namespace;

    public JsonviewTemplatePackage(String namespace) {
        super();
        this.namespace = namespace;
    }

    /**
     * 根据id获取模板
     * @param templateId 模板ID
     * @return 模板
     */
    public JsonviewTemplate getJsonviewTemplateById(String templateId) {
        JsonviewTemplate jsonviewTemplate = super.get(templateId);
        if (jsonviewTemplate == null) {
            throw new TemplateUndefinedException(namespace, templateId);
        }
        return jsonviewTemplate;
    }

    /**
     * 放入模板
     * @param jsonviewTemplate 模板
     */
    public void push(JsonviewTemplate jsonviewTemplate) {
        String templateId = jsonviewTemplate.getTemplateId();
        if (super.containsKey(templateId)) {
            throw new ResourceNotUniqueException("Jsonview template", templateId);
        }
        super.put(templateId, jsonviewTemplate);
    }
}
