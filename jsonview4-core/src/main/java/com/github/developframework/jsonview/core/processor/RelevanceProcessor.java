package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.ArrayExpression;
import com.github.developframework.expression.Expression;
import com.github.developframework.expression.ObjectExpression;
import com.github.developframework.jsonview.core.dynamic.RelFunction;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.RelevanceElement;
import com.github.developframework.jsonview.core.exception.InvalidArgumentsException;
import com.github.developframework.jsonview.core.exception.JsonviewException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 关联节点处理器
 *
 * @author qiuzhenhao
 */
public class RelevanceProcessor extends ArrayProcessor {


    public RelevanceProcessor(ProcessContext processContext, RelevanceElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        Optional<Object> valueOptional = processContext.getDataModel().getData(element.getDataDefinition().getExpression());
        if(valueOptional.isPresent()) {
            this.value = valueOptional.get();
            this.node = ((ObjectNode) parentProcessor.getNode()).putArray(element.showName());
            return true;
        }
        if(!element.isNullHidden()) {
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
            for (ArrayExpression childArrayExpression : arrayExpressions) {
                super.single(childArrayExpression, arrayExpressions.size());
            }
        }
    }

    private RelFunction getRelFunctionInstance(String functionValue) {
        Optional<Object> converterOptional = processContext.getDataModel().getData(functionValue);
        return (RelFunction) converterOptional.orElseGet(()->{
            try {
                Object obj = Class.forName(functionValue).newInstance();
                if (obj instanceof RelFunction) {
                    return obj;
                } else {
                    throw new InvalidArgumentsException("rel-function", functionValue, "It's not a RelFunction instance.");
                }
            } catch (ClassNotFoundException e) {
                throw new InvalidArgumentsException("rel-function", functionValue, "class not found, and it's also not a expression.");
            } catch (IllegalAccessException | InstantiationException e) {
                throw new JsonviewException("Can't new RelFunction instance.");
            }
        });
    }

    private Object[] getTargets() {
        Class<?> targetClass = this.value.getClass();
        if(targetClass.isArray()) {
            return (Object[]) this.value;
        } else if(List.class.isAssignableFrom(targetClass)) {
            List list = (List) this.value;
            Object[] array = new Object[list.size()];
            return list.toArray(array);
        } else {
            throw new InvalidArgumentsException("data", expression.toString(), "The data must be array or list type.");
        }
    }
}
