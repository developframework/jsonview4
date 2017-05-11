package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ObjectElement;

import java.util.Iterator;
import java.util.Optional;

/**
 * 对象节点处理器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class ObjectProcessor extends ContainerProcessor<ObjectElement, ObjectNode> {

    public ObjectProcessor(ProcessContext processContext, ObjectElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected void process(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        for (Iterator<Element> iterator = element.childElementIterator(); iterator.hasNext();) {
            final Element childElement = iterator.next();
            final Optional<Processor<? extends Element, ? extends JsonNode>> nextProcessorOptional = childElement.createProcessor(processContext, node, expression);
            nextProcessorOptional.ifPresent(nextProcessor -> {
                nextProcessor.process(this);
            });
        }
    }
}
