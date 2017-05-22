package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.data.FunctionSign;
import com.github.developframework.jsonview.core.element.ContentElement;
import com.github.developframework.jsonview.core.element.Element;
import lombok.Getter;
import lombok.Setter;

/**
 * 处理器顶级基类
 * @author qiuzhenhao
 * @param <ELEMENT> 描述处理哪种节点
 * @param <NODE> JsonNode
 */
@Getter
public abstract class Processor<ELEMENT extends Element, NODE extends JsonNode> {

    /* 处理过程上下文 */
    protected ProcessContext processContext;

    /* 节点 */
    protected ELEMENT element;

    /* 节点树 */
    @Setter
    protected NODE node;

    protected Expression expression;
    @Setter
    protected Object value;

    public Processor(ProcessContext processContext, ELEMENT element, NODE node, Expression expression) {
        this.processContext = processContext;
        this.element = element;
        this.node = node;
        this.expression = expression;
    }

    /**
     * 生成子表达式
     * @param contentElement 内容节点
     * @param parentExpression 父表达式
     * @return 子表达式
     */
    public static Expression childExpression(ContentElement contentElement, Expression parentExpression) {
        final DataDefinition dataDefinition = contentElement.getDataDefinition();
        if (dataDefinition.getFunctionSign() == FunctionSign.ROOT) {
            return dataDefinition.getExpression();
        }
        if (parentExpression == null) {
            return dataDefinition.getExpression();
        }
        return Expression.concat(parentExpression, dataDefinition.getExpression());
    }

    /**
     * 准备操作
     * @param parentProcessor 上层处理器
     * @return 是否继续执行处理逻辑
     */
    protected abstract boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor);

    /**
     * 处理核心逻辑
     * @param parentProcessor 上层处理器
     */
    protected abstract void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor);

    /**
     * 处理过程
     * @param parentProcessor 上层处理器
     */
    public final void process(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        if(prepare(parentProcessor)) {
            handleCoreLogic(parentProcessor);
        }
    }
}
