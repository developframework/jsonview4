package com.github.developframework.jsonview.core.dynamic;

/**
 * 数组映射接口
 * @author qiuzhenhao
 */
public interface MapFunction<T, R> {

    /**
     * 映射方法
     * @param t
     * @param index
     * @return
     */
    R apply(T t, int index);
}
