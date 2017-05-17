package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ObjectElement;

/**
 * 副本模板节点处理器
 * @author qiuzhenhao
 */
public class DuplicateTemplateProcessor extends ObjectProcessor{

    public DuplicateTemplateProcessor(ProcessContext processContext, ObjectElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        // 始终为true
        return true;
    }

    @Override
    public void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        if (parentProcessor instanceof ObjectInArrayProcessor) {
            ObjectInArrayProcessor processor = new ObjectInArrayProcessor(processContext, element, parentProcessor.expression, ((ObjectInArrayProcessor) parentProcessor).size);
            processor.setNode((ObjectNode) parentProcessor.node);
            processor.process(parentProcessor);
        } else {
            super.handleCoreLogic(parentProcessor);
        }
    }
}
