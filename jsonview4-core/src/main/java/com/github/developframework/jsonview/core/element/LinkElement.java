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
import lombok.Getter;

/**
 * 一对一链接节点
 *
 * @author qiuzhenhao
 */
public class LinkElement extends ContainerFunctionalElement{

    @Getter
    private ObjectElement insideObjectElement;

    public LinkElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId);
        insideObjectElement = new ObjectElement(configuration, namespace, templateId, dataDefinition, alias) {

            @Override
            public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
                return new ObjectProcessor(processContext, this, parentExpression) {
                    @Override
                    protected Expression childExpression(Expression parentExpression) {
                        return parentExpression;
                    }
                };
            }
        };
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new LinkProcessor(processContext, this, parentNode, parentExpression);
    }

    @Override
    public void addChildElement(Element element) {
        insideObjectElement.addChildElement(element);
    }

    @Override
    public void copyChildElement(ContainChildElementable otherContainer) {
        insideObjectElement.copyChildElement(otherContainer);
    }
}
