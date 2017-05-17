package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.ArrayExpression;
import com.github.developframework.expression.Expression;
import com.github.developframework.expression.ObjectExpression;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.data.FunctionSign;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.LinkElement;
import com.github.developframework.jsonview.core.exception.InvalidArgumentsException;
import com.github.developframework.jsonview.core.exception.LinkSizeNotEqualException;

import java.util.Collection;

/**
 * 一对一链接节点处理器
 *
 * @author qiuzhenhao
 */
public class LinkProcessor extends FunctionalProcessor<LinkElement, ObjectNode> {


    public LinkProcessor(ProcessContext processContext, LinkElement element, ObjectNode node, Expression parentExpression) {
        super(processContext, element, node, parentExpression);
    }

    @Override
    protected Expression childExpression(Expression parentExpression) {
        final DataDefinition dataDefinition = element.getInsideObjectElement().getDataDefinition();
        if (dataDefinition.getFunctionSign() == FunctionSign.ROOT) {
            return dataDefinition.getExpression();
        }
        if (parentExpression == null) {
            return dataDefinition.getExpression();
        }
        return Expression.concat(parentExpression, dataDefinition.getExpression());
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        final ObjectInArrayProcessor objectInArrayProcessor = (ObjectInArrayProcessor) parentProcessor;
        ObjectExpression targetObjectExpression = (ObjectExpression) expression;

        this.value = processContext.getDataModel().getDataRequired(targetObjectExpression);
        int size;
        if (value.getClass().isArray()) {
            size = ((Object[]) value).length;
        } else if (value instanceof Collection<?>) {
            size = ((Collection<?>) value).size();
        } else {
            throw new InvalidArgumentsException("data", expression.toString(), "Data must be array or List type.");
        }

        if(size != objectInArrayProcessor.getSize()) {
            throw new LinkSizeNotEqualException(element.getNamespace(), element.getTemplateId());
        }
        return true;
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        final ObjectInArrayProcessor objectInArrayProcessor = (ObjectInArrayProcessor) parentProcessor;
        final ArrayExpression arrayExpression = (ArrayExpression) objectInArrayProcessor.getExpression();
        ArrayExpression targetExpression = new ArrayExpression(((ObjectExpression) expression).getPropertyName(), arrayExpression.getIndex());
        Processor<? extends Element, ? extends JsonNode> nextProcessor = element.getInsideObjectElement().createProcessor(processContext, objectInArrayProcessor.node, targetExpression);
        nextProcessor.process(parentProcessor);
    }
}
