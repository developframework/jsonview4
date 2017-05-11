package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import com.github.developframework.jsonview.core.processor.VirtualObjectProcessor;

import java.util.Optional;

/**
 * 虚拟对象节点
 * @author qiuzhenhao
 * @date 2017/5/9
 */
public class VirtualObjectElement extends ObjectElement {

    public VirtualObjectElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    @Override
    public Optional<Processor<? extends Element, ? extends JsonNode>> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        VirtualObjectProcessor processor = new VirtualObjectProcessor(processContext, this, parentExpression);
        final ObjectNode objectNode = parentNode.putObject(this.showName());
        processor.setNode(objectNode);
        return Optional.of(processor);
    }
}