package com.github.developframework.jsonview.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;
import com.github.developframework.jsonview.core.element.JsonviewTemplatePackage;
import com.github.developframework.jsonview.core.exception.ResourceNotUniqueException;
import com.github.developframework.jsonview.core.exception.TemplatePackageUndefinedException;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Jsonview配置
 * @author qiuzhenhao
 * @date 2017/5/6
 */
public class JsonviewConfiguration {

    @Getter
    @Setter
    private ObjectMapper objectMapper;

    /* 模板包Map */
    private Map<String, JsonviewTemplatePackage> templatePackages;

    public JsonviewConfiguration() {
        this.templatePackages = new HashMap<>();
    }

    /**
     * 增加模板包
     * @param jsonviewTemplatePackage 模板包
     */
    public void putTemplatePackage(JsonviewTemplatePackage jsonviewTemplatePackage) {
        String namespace = jsonviewTemplatePackage.getNamespace();
        if (this.templatePackages.containsKey(namespace)) {
            throw new ResourceNotUniqueException("Jsonview package", namespace);
        }
        this.templatePackages.put(namespace, jsonviewTemplatePackage);
    }

    /**
     * 获取模板包
     * @param namespace 命名空间
     * @return 模板包
     */
    public JsonviewTemplatePackage getTemplatePackageByNamespace(String namespace) {
        JsonviewTemplatePackage jsonviewTemplatePackage = templatePackages.get(namespace);
        if (jsonviewTemplatePackage == null) {
            throw new TemplatePackageUndefinedException(namespace);
        }
        return jsonviewTemplatePackage;
    }

    /**
     * 提取模板
     * @param namespace 命名空间
     * @param templateId 模板ID
     * @return 模板
     */
    public JsonviewTemplate extractTemplate(String namespace, String templateId) {
        return getTemplatePackageByNamespace(namespace).getJsonviewTemplateById(templateId);
    }
}
