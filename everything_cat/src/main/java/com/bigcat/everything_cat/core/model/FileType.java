package com.bigcat.everything_cat.core.model;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * FileType模型类表示文件扩展名归类
 *
 * @auther koekie
 * @date 2019/3/26 18:56
 * @function
 */

public enum FileType {

    IMG("jpg","jpeg","png","bmp","gif"),
    DOC("doc","pdf","ppt","pptx","xls","txt"),
    BIN("exe","jar","sh","msi"),
    ARCHIVE("zip","rar"),
    OTHER("*");

    private Set<String> extend = new HashSet<>();

    FileType(String... extend) {
        this.extend.addAll(Arrays.asList(extend));
    }


    public static FileType lookupByExtend(String extend) {
        for(FileType fileType : FileType.values()){
            if(fileType.extend.contains(extend)) {
                return fileType;
            }
        }
        return  FileType.OTHER;
    }

    public static FileType lookupByName(String name) {
        for(FileType fileType : FileType.values()){
            if(fileType.name().equals(name)){
                return fileType;
            }
        }
        return FileType.OTHER;
    }

    public static void main(String[] args) {
        System.out.println(FileType.lookupByExtend("exe"));//bin
        System.out.println(FileType.lookupByExtend("md"));//other
        System.out.println(FileType.lookupByName("BIN"));//bin
        System.out.println(FileType.lookupByName("SHELL"));//other
    }
}

