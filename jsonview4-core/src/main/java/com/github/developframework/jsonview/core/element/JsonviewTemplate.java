package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import com.github.developframework.jsonview.core.processor.TemplateProcessor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Jsonview 模板
 *
 * @author qiuzhenhao
 */
@Getter
public class JsonviewTemplate extends ObjectElement{

    /* 扩展口 */
    @Setter
    private Extend extend;

    @Setter
    private String mapFunctionValue;

    public JsonviewTemplate(JsonviewConfiguration configuration, String namespace, String templateId) {
        super(configuration, namespace, templateId, null, null);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        TemplateProcessor templateProcessor = new TemplateProcessor(processContext, this, Processor.childExpression(this, parentExpression));
        templateProcessor.setNode(parentNode);
        return templateProcessor;
    }

    public Optional<Extend> getExtend() {
        return Optional.ofNullable(extend);
    }

    /**
     * 创建一个副本模板节点
     * @return 副本模板节点
     */
    public DuplicateTemplateElement createDuplicateTemplateElement() {
        return new DuplicateTemplateElement(configuration, this);
    }

    @Getter
    public class Extend {

        private String namespace;

        private String templateId;

        private String port;

        public Extend(String extendValue, String defaultNamespace) {
            String front = StringUtils.substringBefore(extendValue, ":");
            this.port = StringUtils.substringAfter(extendValue, ":");
            if (front.contains(".")) {
                this.namespace = StringUtils.substringBefore(front, ".");
                this.templateId = StringUtils.substringAfter(front, ".");
            } else {
                this.namespace = defaultNamespace;
                this.templateId = front;
            }
        }
    }
}
