package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.ObjectProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;

/**
 * 代理对象节点
 * @author qiuzhenhao
 */
public class ProxyObjectElement extends ObjectElement{

    public ProxyObjectElement(JsonviewConfiguration configuration, ContainerElement containerElement, DataDefinition dataDefinition) {
        super(configuration, containerElement.namespace, containerElement.templateId, dataDefinition, containerElement.alias);
        this.copyChildElement(containerElement);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new ObjectProcessor(processContext, this, parentExpression);
    }
}
