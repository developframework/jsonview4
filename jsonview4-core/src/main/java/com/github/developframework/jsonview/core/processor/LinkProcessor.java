package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.ArrayExpression;
import com.github.developframework.expression.Expression;
import com.github.developframework.expression.ObjectExpression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.LinkElement;
import com.github.developframework.jsonview.core.element.ObjectElement;
import com.github.developframework.jsonview.core.exception.InvalidArgumentsException;
import com.github.developframework.jsonview.core.exception.LinkSizeNotEqualException;

import java.util.Collection;
import java.util.Optional;

/**
 * 一对一链接节点处理器
 *
 * @author qiuzhenhao
 */
public class LinkProcessor extends ObjectProcessor {

    public LinkProcessor(ProcessContext processContext, LinkElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        final ObjectInArrayProcessor objectInArrayProcessor = (ObjectInArrayProcessor) parentProcessor;
        Optional<Object> valueOptional = processContext.getDataModel().getData(expression);
        if (valueOptional.isPresent()) {
            this.value = valueOptional.get();
            int size;
            if (value.getClass().isArray()) {
                size = ((Object[]) value).length;
            } else if (value instanceof Collection<?>) {
                size = ((Collection<?>) value).size();
            } else {
                throw new InvalidArgumentsException("data", expression.toString(), "Data must be array or List type.");
            }

            if (size != objectInArrayProcessor.getSize()) {
                throw new LinkSizeNotEqualException(element.getNamespace(), element.getTemplateId());
            }
            return true;
        }
        if (!element.isNullHidden()) {
            node.putNull(element.showName());
        }
        return false;
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        final ObjectInArrayProcessor objectInArrayProcessor = (ObjectInArrayProcessor) parentProcessor;
        final ArrayExpression arrayExpression = (ArrayExpression) objectInArrayProcessor.getExpression();
        ArrayExpression targetExpression = new ArrayExpression(((ObjectExpression) expression).getPropertyName(), arrayExpression.getIndex());
        ObjectElement objectElement = ((LinkElement) element).createLinkInsideObjectElement();
        Processor<? extends Element, ? extends JsonNode> nextProcessor = objectElement.createProcessor(processContext, objectInArrayProcessor.node, targetExpression);
        nextProcessor.process(parentProcessor);
    }
}
