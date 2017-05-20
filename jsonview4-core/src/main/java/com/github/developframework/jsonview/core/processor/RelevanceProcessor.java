package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.ArrayExpression;
import com.github.developframework.expression.Expression;
import com.github.developframework.expression.ObjectExpression;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.dynamic.RelFunction;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ProxyObjectElement;
import com.github.developframework.jsonview.core.element.RelevanceElement;
import com.github.developframework.jsonview.core.exception.InvalidArgumentsException;
import com.github.developframework.jsonview.core.exception.JsonviewException;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 关联节点处理器
 *
 * @author qiuzhenhao
 */
@Slf4j
public class RelevanceProcessor extends ArrayProcessor {


    public RelevanceProcessor(ProcessContext processContext, RelevanceElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        Optional<Object> valueOptional = processContext.getDataModel().getData(element.getDataDefinition().getExpression());
        if (valueOptional.isPresent()) {
            this.value = valueOptional.get();
//            this.node = ((ObjectNode) parentProcessor.getNode()).putArray(element.showName());
            return true;
        }
        if (!element.isNullHidden()) {
            ((ObjectNode) parentProcessor.getNode()).putNull(element.showName());
        }
        return false;
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        final ObjectInArrayProcessor objectInArrayProcessor = (ObjectInArrayProcessor) parentProcessor;
        final ArrayExpression arrayExpression = (ArrayExpression) objectInArrayProcessor.getExpression();
        Optional<Object> itemOptional = processContext.getDataModel().getData(arrayExpression);
        if (itemOptional.isPresent()) {
            RelFunction relFunction = getRelFunctionInstance(((RelevanceElement) element).getRelFunctionValue());
            Object[] targets = getTargets();
            List<Integer> indexList = new LinkedList<>();
            for (int i = 0; i < targets.length; i++) {
                if (relFunction.relevant(itemOptional.get(), arrayExpression.getIndex(), targets[i], i)) {
                    indexList.add(i);
                }
            }
            final ObjectExpression targetExpression = (ObjectExpression) element.getDataDefinition().getExpression();
            // 每个索引转化成ArrayExpression
            List<ArrayExpression> arrayExpressions = indexList.stream().map(index -> new ArrayExpression(targetExpression.getPropertyName(), index)).collect(Collectors.toList());
            // 类型策略
            typeStrategy(parentProcessor, arrayExpressions, targetExpression);
        }
    }

    /**
     * 获得 RelFunction对象
     *
     * @param relFunctionValue
     * @return RelFunction对象
     */
    private RelFunction getRelFunctionInstance(String relFunctionValue) {
        Optional<Object> converterOptional = processContext.getDataModel().getData(relFunctionValue);
        return (RelFunction) converterOptional.orElseGet(() -> {
            try {
                Object obj = Class.forName(relFunctionValue).newInstance();
                if (obj instanceof RelFunction) {
                    return obj;
                } else {
                    throw new InvalidArgumentsException("rel-function", relFunctionValue, "It's not a RelFunction instance.");
                }
            } catch (ClassNotFoundException e) {
                throw new InvalidArgumentsException("rel-function", relFunctionValue, "class not found, and it's also not a expression.");
            } catch (IllegalAccessException | InstantiationException e) {
                throw new JsonviewException("Can't new RelFunction instance.");
            }
        });
    }

    /**
     * 获得目标数组
     *
     * @return 目标数组
     */
    private Object[] getTargets() {
        Class<?> targetClass = this.value.getClass();
        if (targetClass.isArray()) {
            return (Object[]) this.value;
        } else if (List.class.isAssignableFrom(targetClass)) {
            List list = (List) this.value;
            Object[] array = new Object[list.size()];
            return list.toArray(array);
        } else {
            throw new InvalidArgumentsException("data", expression.toString(), "The data must be array or list type.");
        }
    }

    /**
     * 类型策略
     *
     * @param parentProcessor  上层处理器
     * @param arrayExpressions 表达式列表
     * @param targetExpression 目标表达式
     */
    private void typeStrategy(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor, List<ArrayExpression> arrayExpressions, ObjectExpression targetExpression) {

        switch (((RelevanceElement) element).getRelevanceType()) {
            case AUTO: {
                if (arrayExpressions.size() == 1) {
                    generateObjectStructure(parentProcessor, arrayExpressions);
                } else {
                    generateArrayStructure(parentProcessor, arrayExpressions);
                }
            }
            break;
            case SINGLE: {
                if (arrayExpressions.size() > 1) {
                    log.warn("You have more items in {}, and you choose relevance type \"SINGLE\".", targetExpression.toString());
                }
                generateObjectStructure(parentProcessor, arrayExpressions);
            }
            break;
            case MULTIPLE: {
                generateArrayStructure(parentProcessor, arrayExpressions);
            }
            break;
        }
    }

    /**
     * 生成对象结构
     *
     * @param parentProcessor  上层处理器
     * @param arrayExpressions 表达式列表
     */
    private void generateObjectStructure(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor, List<ArrayExpression> arrayExpressions) {
        Expression targetExpression = arrayExpressions.get(0);
        final DataDefinition dataDefinition = new DataDefinition(null, targetExpression);
        final ProxyObjectElement proxyObjectElement = new ProxyObjectElement(processContext.getJsonviewConfiguration(), element, dataDefinition);
        proxyObjectElement.copyChildElement(element);
        Processor<? extends Element, ? extends JsonNode> processor = proxyObjectElement.createProcessor(processContext, null, targetExpression);
        processor.process(parentProcessor);
    }

    /**
     * 生成数组结构
     *
     * @param parentProcessor  上层处理器
     * @param arrayExpressions 表达式列表
     */
    private void generateArrayStructure(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor, List<ArrayExpression> arrayExpressions) {
        this.node = ((ObjectNode) parentProcessor.getNode()).putArray(element.showName());
        for (ArrayExpression childArrayExpression : arrayExpressions) {
            super.single(childArrayExpression, arrayExpressions.size());
        }
    }
}
