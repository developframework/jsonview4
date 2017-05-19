package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ElseElement;

import java.util.Iterator;

/**
 * Else节点处理器
 * @author qiuzhenhao
 */
public class ElseProcessor extends FunctionalProcessor<ElseElement, ObjectNode>{

    public ElseProcessor(ProcessContext processContext, ElseElement element, ObjectNode node, Expression parentExpression) {
        super(processContext, element, node, parentExpression);
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        for (Iterator<Element> iterator = element.childElementIterator(); iterator.hasNext();) {
            final Element childElement = iterator.next();
            final Processor<? extends Element, ? extends JsonNode> nextProcessor = childElement.createProcessor(processContext, node, expression);
            nextProcessor.process(parentProcessor);
        }
    }
}
