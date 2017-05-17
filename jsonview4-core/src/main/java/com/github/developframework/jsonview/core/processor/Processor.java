package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import lombok.Getter;
import lombok.Setter;

/**
 * 处理器顶级基类
 * @author qiuzhenhao
 * @date 2017/5/6
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

    protected Object value;

    public Processor(ProcessContext processContext, ELEMENT element, NODE node, Expression parentExpression) {
        this.processContext = processContext;
        this.element = element;
        this.node = node;
        this.expression = childExpression(parentExpression);
    }

    /**
     * 生成子表达式
     * @param parentExpression 父表达式
     * @return 子表达式
     */
    protected abstract Expression childExpression(Expression parentExpression);

    /**
     * 准备操作
     * @param parentProcessor 上层处理器
     * @return
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
