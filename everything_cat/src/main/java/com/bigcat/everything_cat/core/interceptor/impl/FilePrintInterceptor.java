package com.bigcat.everything_cat.core.interceptor.impl;

import com.bigcat.everything_cat.core.interceptor.FileInterceptor;

import java.io.File;

/**
 * @Author:koekie
 * @Date: 2019/9/18
 * @Description:
 */
public class FilePrintInterceptor implements FileInterceptor {
    @Override
    public void apply(File file) {
        System.out.println(file.getAbsolutePath());
    }
}

