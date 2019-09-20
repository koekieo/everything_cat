package com.bigcat.everything_cat.core.interceptor;

import java.io.File;

/**
 * @Author:koekie
 * @Date: 2019/9/18
 * @Description:
 */
public interface FileInterceptor {
    /**
     * 文件拦截器,处理指定文件
     * @param file :
     * @return
     */
    void apply(File file);
}

