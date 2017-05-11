package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.FunctionalElement;

/**
 * 功能型节点处理器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public abstract class FunctionalProcessor<ELEMENT extends FunctionalElement, NODE extends JsonNode> extends Processor<ELEMENT, NODE> {


    public FunctionalProcessor(ProcessContext processContext, ELEMENT element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected Expression childExpression(Expression parentExpression) {
        return parentExpression;
    }

}