package com.github.developframework.jsonview.core.processor;

/**
 * 数组一对一链接处理器接口
 * @author qiuzhenhao
 * @date 2017/5/9
 */
public interface OneToOneProcessor {

    /**
     * 检查size
     * @param parentArraySize 父数组size
     */
    void checkSize(int parentArraySize);

    /**
     * 设置索引
     * @param index 索引
     */
    void setIndex(int index);
}
