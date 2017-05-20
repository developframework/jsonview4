package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.processor.DuplicateTemplateProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;

/**
 * 模板副本
 *
 * @author qiuzhenhao
 */
public final class DuplicateTemplateElement extends ProxyObjectElement {

    public DuplicateTemplateElement(JsonviewConfiguration configuration, JsonviewTemplate jsonviewTemplate) {
        super(configuration, jsonviewTemplate, null);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        DuplicateTemplateProcessor processor = new DuplicateTemplateProcessor(processContext, this, parentExpression);
        processor.setNode(parentNode);
        return processor;
    }
}
