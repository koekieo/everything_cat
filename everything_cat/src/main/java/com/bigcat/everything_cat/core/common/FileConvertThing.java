package com.bigcat.everything_cat.core.common;

import com.bigcat.everything_cat.core.model.FileType;
import com.bigcat.everything_cat.core.model.Thing;

import java.io.File;

/**
 * @Author:koekie
 * @Date: 2019/9/18
 * @Description:文件对象转换Thing对象的辅助类
 */
public class FileConvertThing {

    public static Thing convert(File file){
        Thing thing = new Thing();
        String name = file.getName();
        thing.setName(name);
        thing.setPath(file.getAbsolutePath());
        //目录 -> *
        //文件 -> 有扩展名,通过扩展名获取FileType
        // 无扩展 *
        int index = name.lastIndexOf(".");
        String extend = "*";
        if (index != -1 && (index+1) < name.length()) {
            extend = name.substring(index + 1);
        }
        thing.setFileType(FileType.lookupByExtend(extend));
        thing.setDepth(computePathDepth(file.getAbsolutePath()));
        return thing;
    }
    private static int computePathDepth(String path){
        //path => D:\a\b =>2
        int depth = 0;
        for (char c : path.toCharArray()){
            if (c == File.separatorChar) {
                depth += 1;
            }
        }
        return depth;
    }
}

