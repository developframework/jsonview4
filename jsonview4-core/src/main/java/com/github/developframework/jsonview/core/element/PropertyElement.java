package com.github.developframework.jsonview.core.element;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import lombok.Setter;

import java.util.Optional;

/**
 * 属性节点
 * @author qiuzhenhao
 * @date 2017/5/7
 */
public abstract class PropertyElement extends ContentElement{

    @Setter
    protected String converterValue;

    public PropertyElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    public Optional<String> getConverterValue() {
        return Optional.ofNullable(converterValue);
    }
}
