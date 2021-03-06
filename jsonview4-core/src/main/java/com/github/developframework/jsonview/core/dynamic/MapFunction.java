package com.github.developframework.jsonview.core.dynamic;

/**
 * 数组映射接口
 * @author qiuzhenhao
 */
public interface MapFunction<T, R> {

    /**
     * 映射方法
     * @param t 数组元素
     * @param index 索引
     * @return 转换后的对象
     */
    R apply(T t, int index);
}
