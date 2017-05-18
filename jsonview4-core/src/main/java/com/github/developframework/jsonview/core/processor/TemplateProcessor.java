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

    public TemplateProcessor(ProcessContext processContext, JsonviewTemplate jsonviewTemplate, ObjectNode node, Expression parentExpression) {
        super(processContext, jsonviewTemplate, parentExpression);
        this.node = node;
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        // 始终为true
        return true;
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        Optional<JsonviewTemplate.Extend> extendOptional = ((JsonviewTemplate) element).getExtend();
        if (extendOptional.isPresent()) {
            JsonviewTemplate.Extend extend = extendOptional.get();
            // 提取模板
            JsonviewTemplate extendTemplate = processContext.getJsonviewConfiguration().extractTemplate(extend.getNamespace(), extend.getTemplateId());
            // 定义扩展口回调接口实现
            final ExtendPortProcessor.ExtendCallback callback = parentProcessorInCallback -> {
                // 复制一个副本节点进行回调处理
                DuplicateTemplateElement duplicateTemplateElement = ((JsonviewTemplate) this.element).createDuplicateTemplateElement();
                Processor<? extends Element, ? extends JsonNode> processor = duplicateTemplateElement.createProcessor(processContext, (ObjectNode) parentProcessorInCallback.node, parentProcessorInCallback.expression);
                processor.process(parentProcessorInCallback);
            };
            processContext.pushExtendCallback(extend.getPort(), callback);
            Processor<? extends Element, ? extends JsonNode> extendTemplateProcessor = extendTemplate.createProcessor(processContext, node, expression);
            extendTemplateProcessor.process(parentProcessor);
        } else {
            super.handleCoreLogic(parentProcessor);
        }
    }
}
