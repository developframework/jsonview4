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
 */
public class IfProcessor extends FunctionalProcessor<IfElement, ObjectNode> {

    public IfProcessor(ProcessContext processContext, IfElement element, ObjectNode node, Expression parentExpression) {
        super(processContext, element, node, parentExpression);
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
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
        boolean verifyResult;
        if (condition instanceof Boolean) {
            verifyResult = ((Boolean) condition).booleanValue();
        } else if (condition instanceof Condition) {
            // 验证条件
            verifyResult = ((Condition) condition).verify(processContext.getDataModel(), parentProcessor.getExpression());
        } else {
            throw new JsonviewException("The expression \"%s\" is not Condition instance.", element.getConditionValue());
        }
        if (verifyResult) {
            // 执行if
            executeIfTrue(parentProcessor);
        } else {
            // 执行else
            executeIfFalse(parentProcessor);
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
            final Processor<? extends Element, ? extends JsonNode> nextProcessor = childElement.createProcessor(processContext, node, expression);
            nextProcessor.process(parentProcessor);
        }
    }

    /**
     * 执行条件假的逻辑
     *
     * @param parentProcessor
     */
    private void executeIfFalse(final ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        element.getElseElement().ifPresent(elseElement -> {
            Processor<? extends Element, ? extends JsonNode> elseProcessor = elseElement.createProcessor(processContext, (ObjectNode) parentProcessor.getNode(), parentProcessor.getExpression());
            elseProcessor.process(parentProcessor);
        });
    }

}
