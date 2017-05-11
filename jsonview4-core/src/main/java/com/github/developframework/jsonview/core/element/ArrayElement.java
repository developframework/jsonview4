package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
 * @date 2017/5/8
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
    public Optional<Processor<? extends Element, ? extends JsonNode>> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        ArrayProcessor processor = new ArrayProcessor(processContext, this, parentExpression);
        Optional<Object> optional = processContext.getDataModel().getData(processor.getExpression());
        if (optional.isPresent()) {
            final ArrayNode arrayNode = parentNode.putArray(this.showName());
            processor.setNode(arrayNode);
            return Optional.of(processor);
        }
        if (!nullHidden) {
            parentNode.putNull(this.showName());
        }
        return Optional.empty();
    }

    @Override
    public void addChildElement(Element element) {
        super.addChildElement(element);
        this.itemObjectElement.addChildElement(element);
    }

    public Optional<String> getMapFunctionValueOptional() {
        return Optional.ofNullable(mapFunctionValue);
    }
}
