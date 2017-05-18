package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.*;

/**
 * 一对一链接节点
 *
 * @author qiuzhenhao
 */
public class LinkElement extends ObjectElement{

    public LinkElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        LinkProcessor linkProcessor = new LinkProcessor(processContext, this, Processor.childExpression(this, parentExpression));
        linkProcessor.setNode(parentNode);
        return linkProcessor;
    }

    /**
     * 创建一个代理节点处理任务
     * @return
     */
    public ContentElement createProxyContentElement() {
        if(isChildElementEmpty()) {
            // 如果没有子节点，视为普通属性节点处理
            return new NormalPropertyElement(configuration, namespace, templateId, dataDefinition, alias) {
                @Override
                public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
                    return new NormalPropertyProcessor(processContext, this, parentExpression);
                }
            };
        } else {
            // 如果有子节点，视为对象节点处理
            ObjectElement objectElement = new ObjectElement(configuration, namespace, templateId, dataDefinition, alias) {
                @Override
                public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
                    return new ObjectProcessor(processContext, this, parentExpression);
                }
            };
            objectElement.copyChildElement(this);
            return objectElement;
        }
    }

    /**
     * 内置节点
     */
    public class LinkInsideObjectElement extends ObjectElement {

        public LinkInsideObjectElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
            super(configuration, namespace, templateId, dataDefinition, alias);
        }

        @Override
        public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
            return new ObjectProcessor(processContext, this, parentExpression);
        }
    }
}
