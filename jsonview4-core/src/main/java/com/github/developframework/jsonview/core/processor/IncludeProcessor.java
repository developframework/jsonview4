package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.IncludeElement;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;

/**
 * 包含功能节点处理器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class IncludeProcessor extends FunctionalProcessor<IncludeElement, ObjectNode>{
    public IncludeProcessor(ProcessContext processContext, IncludeElement element, ObjectNode node, Expression parentExpression) {
        super(processContext, element, node, parentExpression);
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        JsonviewConfiguration jsonviewConfiguration = processContext.getJsonviewConfiguration();
        JsonviewTemplate jsonviewTemplate = jsonviewConfiguration.extractTemplate(element.getTargetNamespace(), element.getTargetTemplateId());
        TemplateProcessor templateProcessor = new TemplateProcessor(processContext, jsonviewTemplate, node, expression, parentProcessor.getElement().getDataDefinition());
        templateProcessor.process(parentProcessor);
    }
}
