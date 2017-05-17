package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.processor.ElseProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;

import java.util.Optional;

/**
 * else节点
 * @author qiuzhenhao
 */
public class ElseElement extends ContainerFunctionalElement{

    public ElseElement(JsonviewConfiguration configuration, String namespace, String templateId) {
        super(configuration, namespace, templateId);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new ElseProcessor(processContext, this, parentNode, parentExpression);
    }
}
