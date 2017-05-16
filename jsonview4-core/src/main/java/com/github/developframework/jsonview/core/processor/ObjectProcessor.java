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
 *
 * @author qiuzhenhao
 */
public class ObjectProcessor extends ContainerProcessor<ObjectElement, ObjectNode> {

    public ObjectProcessor(ProcessContext processContext, ObjectElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        Optional<Object> valueOptional = processContext.getDataModel().getData(expression);
        if (valueOptional.isPresent()) {
            final ObjectNode objectNode = ((ObjectNode) parentProcessor.getNode()).putObject(element.showName());
            this.setNode(objectNode);
            this.value = valueOptional.get();
            return true;
        }
        if (!element.isNullHidden()) {
            node.putNull(element.showName());
        }
        return false;
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        for (Iterator<Element> iterator = element.childElementIterator(); iterator.hasNext();) {
            final Element childElement = iterator.next();
            final Processor<? extends Element, ? extends JsonNode> childProcessor = childElement.createProcessor(processContext, node, expression);
            childProcessor.process(this);
        }
    }
}
