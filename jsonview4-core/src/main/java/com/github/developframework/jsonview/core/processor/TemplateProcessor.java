package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.DuplicateTemplateElement;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;

import java.util.Optional;

/**
 * 模板处理器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class TemplateProcessor extends ObjectProcessor{

    public TemplateProcessor(ProcessContext processContext, JsonviewTemplate jsonviewTemplate, Expression parentExpression) {
        super(processContext, jsonviewTemplate, parentExpression);
    }

    public TemplateProcessor(ProcessContext processContext, JsonviewTemplate jsonviewTemplate, ObjectNode node, Expression parentExpression, DataDefinition dataDefinition) {
        super(processContext, jsonviewTemplate, parentExpression);
        jsonviewTemplate.setDataDefinition(dataDefinition);
        this.node = node;
    }

    @Override
    protected Expression childExpression(Expression parentExpression) {
        return parentExpression;
    }

    @Override
    public void process(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        Optional<JsonviewTemplate.Extend> extendOptional = ((JsonviewTemplate) element).getExtend();
        if (extendOptional.isPresent()) {
            JsonviewTemplate.Extend extend = extendOptional.get();
            // 提取模板
            JsonviewTemplate extendTemplate = processContext.getJsonviewConfiguration().extractTemplate(extend.getNamespace(), extend.getTemplateId());
            // 定义扩展口回调接口实现
            final ExtendPortProcessor.ExtendCallback callback = parentProcessorInCallback -> {
                // 复制一个副本节点进行回调处理
                DuplicateTemplateElement duplicateTemplateElement = ((JsonviewTemplate) this.element).createDuplicateTemplateElement();
                Optional<Processor<? extends Element, ? extends JsonNode>> processorOptional = duplicateTemplateElement.createProcessor(processContext, (ObjectNode) parentProcessorInCallback.node, parentProcessorInCallback.expression);
                processorOptional.ifPresent(processor -> processor.process(parentProcessorInCallback));
            };
            processContext.pushExtendCallback(extend.getPort(), callback);
            extendTemplate.createProcessor(processContext, node, expression).ifPresent(processor -> processor.process(parentProcessor));
        } else {
            super.process(parentProcessor);
        }
    }
}
