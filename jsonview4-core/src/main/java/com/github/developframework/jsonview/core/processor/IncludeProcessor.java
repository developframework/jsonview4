package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.IncludeElement;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;

import java.util.Iterator;

/**
 * 包含功能节点处理器
 * @author qiuzhenhao
 */
public class IncludeProcessor extends FunctionalProcessor<IncludeElement, ObjectNode>{
    public IncludeProcessor(ProcessContext processContext, IncludeElement element, ObjectNode node, Expression parentExpression) {
        super(processContext, element, node, parentExpression);
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        JsonviewConfiguration jsonviewConfiguration = processContext.getJsonviewConfiguration();
        JsonviewTemplate jsonviewTemplate = jsonviewConfiguration.extractTemplate(element.getTargetNamespace(), element.getTargetTemplateId());
        for (Iterator<Element> iterator = jsonviewTemplate.childElementIterator(); iterator.hasNext();) {
            final Element childElement = iterator.next();
            final Processor<? extends Element, ? extends JsonNode> childProcessor = childElement.createProcessor(processContext, node, expression);
            childProcessor.process(parentProcessor);
        }
    }
}
