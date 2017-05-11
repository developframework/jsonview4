package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ExtendPortElement;

/**
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class ExtendPortProcessor extends FunctionalProcessor<ExtendPortElement, JsonNode>{

    public ExtendPortProcessor(ProcessContext processContext, ExtendPortElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected void process(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        processContext.getExtendCallback(element.getPortName()).ifPresent(extendCallback -> extendCallback.call(parentProcessor));
    }

    /**
     * 扩展口回调接口
     *
     * @author qiuzhenhao
     * @date 2017/5/8
     */
    public interface ExtendCallback {

        void call(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor);
    }
}
