package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.ArrayProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * 数组节点
 * @author qiuzhenhao
 */
public class ArrayElement extends ContainerElement{

    /* 元素对象节点 */
    @Getter
    private ObjectElement itemObjectElement;

    @Setter
    private String mapFunctionValue;

    public ArrayElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
        this.itemObjectElement = new ObjectElement(configuration, namespace, templateId, dataDefinition, alias);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new ArrayProcessor(processContext, this, Processor.childExpression(this, parentExpression));
    }

    @Override
    public void addChildElement(Element element) {
        super.addChildElement(element);
        this.itemObjectElement.addChildElement(element);
    }

    @Override
    public void copyChildElement(ContainChildElementable otherContainer) {
        super.copyChildElement(otherContainer);
        this.itemObjectElement.copyChildElement(otherContainer);
    }

    public Optional<String> getMapFunctionValueOptional() {
        return Optional.ofNullable(mapFunctionValue);
    }
}
