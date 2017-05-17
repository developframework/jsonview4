package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ObjectElement;

/**
 * 虚拟对象节点处理器
 *
 * @author qiuzhenhao
 */
public class VirtualObjectProcessor extends ObjectProcessor {

    public VirtualObjectProcessor(ProcessContext processContext, ObjectElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        this.node = ((ObjectNode) parentProcessor.getNode()).putObject(element.showName());
        return true;
    }
}
