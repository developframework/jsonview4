package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.processor.DuplicateTemplateProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;

import java.util.Optional;

/**
 *
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class DuplicateTemplateElement extends ObjectElement{

    public DuplicateTemplateElement(JsonviewConfiguration configuration, JsonviewTemplate jsonviewTemplate) {
        super(configuration, jsonviewTemplate.getNamespace(), jsonviewTemplate.getTemplateId(), null, null);
        super.copyChildElement(jsonviewTemplate);
    }

    @Override
    public Optional<Processor<? extends Element, ? extends JsonNode>> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        DuplicateTemplateProcessor processor = new DuplicateTemplateProcessor(processContext, this, parentExpression);
        processor.setNode(parentNode);
        return Optional.of(processor);
    }
}
