package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.data.FunctionSign;
import com.github.developframework.jsonview.core.element.ContentElement;

/**
 * 内容节点处理器
 *
 * @author qiuzhenhao
 */
public abstract class ContentProcessor<ELEMENT extends ContentElement, NODE extends JsonNode> extends Processor<ELEMENT, NODE> {

    public ContentProcessor(ProcessContext processContext, ELEMENT element, Expression parentExpression) {
        super(processContext, element, null, parentExpression);
    }

    @Override
    protected Expression childExpression(Expression parentExpression) {
        final DataDefinition dataDefinition = element.getDataDefinition();
        if (dataDefinition.getFunctionSign() == FunctionSign.ROOT) {
            return dataDefinition.getExpression();
        }
        if (parentExpression == null) {
            return dataDefinition.getExpression();
        }
        return Expression.concat(parentExpression, dataDefinition.getExpression());
    }

}
