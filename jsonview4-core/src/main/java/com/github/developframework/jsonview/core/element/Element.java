package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import lombok.Getter;

/**
 * 节点顶级基类
 * @author qiuzhenhao
 */
@Getter
public abstract class Element {

    /* 配置 */
    protected JsonviewConfiguration configuration;
    /* 所在命名空间 */
    protected String namespace;
    /* 所在模板 */
    protected String templateId;

    public Element(JsonviewConfiguration configuration, String namespace, String templateId) {
        this.configuration = configuration;
        this.namespace = namespace;
        this.templateId = templateId;
    }

    /**
     * 创建处理该节点的处理器
     * @param processContext 处理过程上下文
     * @param parentNode 父json树节点
     * @param parentExpression 父表达式
     * @return 处理器
     */
    public abstract Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression);
}
