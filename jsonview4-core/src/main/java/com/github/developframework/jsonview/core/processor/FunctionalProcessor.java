package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.FunctionalElement;

/**
 * 功能型节点处理器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public abstract class FunctionalProcessor<ELEMENT extends FunctionalElement, NODE extends JsonNode> extends Processor<ELEMENT, NODE> {


    public FunctionalProcessor(ProcessContext processContext, ELEMENT element, NODE node, Expression parentExpression) {
        super(processContext, element, node, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        return true;
    }

}
