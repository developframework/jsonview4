package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ObjectElement;

import java.util.Optional;

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
            ObjectInArrayProcessor processor = new ObjectInArrayProcessor(processContext, element, parentProcessor.expression, ((ObjectInArrayProcessor) parentProcessor).size) {

                @Override
                protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
                    Optional<Object> valueOptional = processContext.getDataModel().getData(expression);
                    if (valueOptional.isPresent()) {
                        this.value = valueOptional.get();
                        // this.node = processContext.getJsonviewConfiguration().getObjectMapper().createObjectNode();
                        // 需要重写这个地方 不再传入新建的ObjectNode，而是使用父处理器的node
                        this.node = (ObjectNode) parentProcessor.node;
                        return true;
                    }
                    return false;
                }
            };
            processor.setNode((ObjectNode) parentProcessor.node);
            processor.process(parentProcessor);
        } else {
            super.handleCoreLogic(parentProcessor);
        }
    }
}
