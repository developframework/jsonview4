package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
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

}
