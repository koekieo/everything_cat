package com.bigcat.everything_cat.config;


import lombok.Data;
import lombok.Getter;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Getter
public class HandlerPath {

    //包含的目录
    private Set<String> includePath = new HashSet<>();
    //排除目录
    private Set<String> excludePath = new HashSet<>();

    private HandlerPath() {

    }

    public void addIncludePath(String path) {
        this.includePath.add(path);
    }

    public void addExcludePath(String path) {
        this.excludePath.add(path);
    }
    public static HandlerPath getDefaultHandlerPath() {

        HandlerPath handlerPath = new HandlerPath();
        Iterable<Path> paths = FileSystems.getDefault().getRootDirectories();
        //默认要包含的目录,即构建索引时需要处理的路径
        paths.forEach(path -> {
            handlerPath.addIncludePath(path.toString());
        });
        //默认要排除的目录,即构建索引时不需要处理的路径
        String systemName = System.getProperty("os.name");
        if (systemName.contains("Windows")) {
            //windows
            handlerPath.addExcludePath("C:\\Windows");
            handlerPath.addExcludePath("C:\\Program Files");
            handlerPath.addExcludePath("C:\\ProgramData");
            handlerPath.addExcludePath("C:\\Program Files(x86)");

        } else {
            //Linux
            handlerPath.addExcludePath("/root");
            handlerPath.addExcludePath("/temp");

        }
        return handlerPath;
    }
}

