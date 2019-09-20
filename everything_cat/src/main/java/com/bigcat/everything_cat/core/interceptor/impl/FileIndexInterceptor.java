package com.bigcat.everything_cat.core.interceptor.impl;

import com.bigcat.everything_cat.core.common.FileConvertThing;
import com.bigcat.everything_cat.core.dao.FileIndexDao;
import com.bigcat.everything_cat.core.interceptor.FileInterceptor;
import com.bigcat.everything_cat.core.model.Thing;

import java.io.File;

/**
 * @Author:koekie
 * @Date: 2019/9/18
 * @Description:
 */
public class FileIndexInterceptor implements FileInterceptor {


    private final FileIndexDao fileIndexDao;

    public FileIndexInterceptor(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
    }

    //打印,转换,写入数据库(Thing)
    @Override
    public void apply(File file) {
        Thing thing = FileConvertThing.convert(file);
        this.fileIndexDao.insert(thing);
    }
}

