package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.LinkProcessor;
import com.github.developframework.jsonview.core.processor.ObjectProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;

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
        return new LinkProcessor(processContext, this, Processor.childExpression(this, parentExpression));
    }

    public LinkInsideObjectElement createLinkInsideObjectElement() {
        LinkInsideObjectElement linkInsideObjectElement = new LinkInsideObjectElement(configuration, namespace, templateId, dataDefinition, alias);
        linkInsideObjectElement.copyChildElement(this);
        return linkInsideObjectElement;
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
