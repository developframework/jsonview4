package com.github.developframework.jsonview.core.dynamic;

/**
 * 简单的关联实现：根据索引一对一关联
 *
 * @author qiuzhenhao
 */
public class SimpleRelFunction implements RelFunction {

    @Override
    public boolean relevant(Object sourceItem, int sourceIndex, Object target, int targetIndex) {
        return sourceIndex == targetIndex;
    }
}
