package com.bigcat.everything_cat.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

//配置
public class EverythingConfig {

    private static volatile EverythingConfig config;

    /**
     * 索引目录
     */
    @Getter
    private HandlerPath handlerPath = HandlerPath.getDefaultHandlerPath();

    /**
     * 最大索引返回的结果数
     */
    @Getter
    @Setter
    private Integer maxReturn = 30;

    /**
     * 是否开启构建索引
     * 默认:程序运行即开启索引
     * 1. 运行程序是,指定参数
     * 2.通过功能命令index
     */
    @Getter
    @Setter
    private Boolean enableBuildIndex = false;

    /**
     * 检索时depth深度的排序规则
     * true: 表示降序
     * false: 表示升序
     * 默认是降序
     */
    @Getter
    @Setter
    private Boolean orderByDesc = false;

    private EverythingConfig() {

    }
    public static EverythingConfig getInstance() {
        if (config == null) {
            synchronized (EverythingConfig.class) {
                if (config == null) {
                    config = new EverythingConfig();
                }
            }
        }
        return config;
    }
}

