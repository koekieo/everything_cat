package com.bigcat.everything_cat.core.interceptor;

import com.bigcat.everything_cat.core.model.Thing;

/**
 * @auther koekie
 * @date 2019/9/19 16:37
 * @function 检索结果thing的拦截器
 */

public interface ThingInterceptor {
    void apply(Thing thing);
}
