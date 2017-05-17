package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.ContainerElement;

/**
 * 容器节点处理器
 *
 * @author qiuzhenhao
 */
public abstract class ContainerProcessor<ELEMENT extends ContainerElement, NODE extends JsonNode> extends ContentProcessor<ELEMENT, NODE>{

    public ContainerProcessor(ProcessContext processContext, ELEMENT element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }
}
