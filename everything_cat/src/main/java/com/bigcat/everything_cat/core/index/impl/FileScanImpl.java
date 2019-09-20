package com.bigcat.everything_cat.core.index.impl;

import com.bigcat.everything_cat.config.EverythingConfig;
import com.bigcat.everything_cat.core.index.FileScan;
import com.bigcat.everything_cat.core.interceptor.FileInterceptor;

import java.io.File;
import java.util.LinkedList;
import java.util.Set;

/**
 * @Author:koekie
 * @Date: 2019/9/18
 * @Description:
 */
public class FileScanImpl implements FileScan {

    private final LinkedList<FileInterceptor> interceptors = new LinkedList<>();
    private EverythingConfig config = EverythingConfig.getInstance();

    @Override
    public void index(String path) {
        Set<String> excludePaths = config.getHandlerPath()
                .getExcludePath();
        //C:\Windows
        //C:\Windows C:\Windows\system32
        //判断 A path 是否在 B path 中
        for (String excludePath : excludePaths) {
            if (path.startsWith(excludePath)) {
                return;
            }
        }
        File file = new File(path);
        if (file.isDirectory()) {
            File[]files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    index(f.getAbsolutePath());
                }
            }
        }
       for (FileInterceptor interceptor : this.interceptors){
            interceptor.apply(file);
       }
    }

    @Override
    public void interceptor(FileInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    public static void main(String[] args) {
        FileScan scan = new FileScanImpl();
        scan.index("C:\\Users\\10762\\Documents");
    }
}

