package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.dynamic.Condition;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.IfElement;
import com.github.developframework.jsonview.core.exception.JsonviewException;

import java.util.Iterator;
import java.util.Optional;

/**
 * if节点处理器
 *
 * @author qiuzhenhao
 * @date 2017/5/9
 */
public class IfProcessor extends FunctionalProcessor<IfElement, ObjectNode> {

    public IfProcessor(ProcessContext processContext, IfElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected Expression childExpression(Expression parentExpression) {
        return parentExpression;
    }

    @Override
    protected void process(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        final String conditionValue = element.getConditionValue();
        Optional<Object> conditionOptional = processContext.getDataModel().getData(conditionValue);
        Object condition = conditionOptional.orElseGet(() -> {
            try {
                return Class.forName(conditionValue).newInstance();
            } catch (ClassNotFoundException e) {
                throw new JsonviewException("The condition's Class \"%s\" not found, and it's also not a expression.", conditionValue);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new JsonviewException("Can't new condition instance.");
            }
        });
        if (condition instanceof Condition) {
            // 验证条件
            if (((Condition) condition).verify(processContext.getDataModel(), parentProcessor.getExpression())) {
                // 执行if
                executeIfTrue(parentProcessor);
            } else {
                // 执行else
                executeIfFalse(parentProcessor);
            }
        } else {
            throw new JsonviewException("The expression \"%s\" is not Condition instance.", element.getConditionValue());
        }
    }

    /**
     * 执行条件真的逻辑
     *
     * @param parentProcessor
     */
    private void executeIfTrue(final ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        for (Iterator<Element> iterator = element.childElementIterator(); iterator.hasNext(); ) {
            final Element childElement = iterator.next();
            final Optional<Processor<? extends Element, ? extends JsonNode>> nextProcessorOptional = childElement.createProcessor(processContext, node, expression);
            nextProcessorOptional.ifPresent(nextProcessor -> nextProcessor.process(parentProcessor));
        }
    }

    /**
     * 执行条件假的逻辑
     *
     * @param parentProcessor
     */
    private void executeIfFalse(final ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        element.getElseElement().ifPresent(elseElement -> {
            Optional<Processor<? extends Element, ? extends JsonNode>> elseProcessorOptional = elseElement.createProcessor(processContext, (ObjectNode) parentProcessor.getNode(), parentProcessor.getExpression());
            elseProcessorOptional.ifPresent(elseProcessor -> elseProcessor.process(parentProcessor));
        });
    }

}
