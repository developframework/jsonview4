package com.github.developframework.jsonview.core.element;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import lombok.Setter;

import java.util.Optional;

/**
 * 属性节点
 *
 * @author qiuzhenhao
 */
public abstract class PropertyElement extends ContentElement {

    @Setter
    protected String converterValue;

    public PropertyElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    public Optional<String> getConverterValue() {
        return Optional.ofNullable(converterValue);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + namespace.hashCode();
        hash = hash * 31 + templateId.hashCode();
        hash = hash * 31 + dataDefinition.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PropertyElement) {
            PropertyElement otherPropertyElement = (PropertyElement) obj;
            return dataDefinition.equals(otherPropertyElement.dataDefinition);
        }
        return false;
    }
}
