package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import com.github.developframework.jsonview.core.processor.RelevanceProcessor;
import lombok.Getter;
import lombok.Setter;

/**
 * 关联节点
 *
 * @author qiuzhenhao
 */
public class RelevanceElement extends FunctionalElement {

    @Getter
    @Setter
    private String relFunctionValue;
    @Getter
    private ArrayElement arrayElement;

    public RelevanceElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias, String relFunctionValue) {
        super(configuration, namespace, templateId);
        arrayElement = new ArrayElement(configuration, namespace, templateId, dataDefinition, alias);
        this.relFunctionValue = relFunctionValue;
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new RelevanceProcessor(processContext, this, parentExpression, parentNode);
    }

    @Override
    public void addChildElement(Element element) {
        super.addChildElement(element);
        arrayElement.addChildElement(element);
    }

    public void setMapFunctionValue(String mapFunctionValue) {
        arrayElement.setMapFunctionValue(mapFunctionValue);
    }
}
