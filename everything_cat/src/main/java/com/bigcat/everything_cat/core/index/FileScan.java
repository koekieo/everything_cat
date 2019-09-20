package com.bigcat.everything_cat.core.index;


import com.bigcat.everything_cat.core.dao.DataSourceFactory;
import com.bigcat.everything_cat.core.dao.FileIndexDao;
import com.bigcat.everything_cat.core.dao.impl.FileIndexDaoImpl;
import com.bigcat.everything_cat.core.index.impl.FileScanImpl;
import com.bigcat.everything_cat.core.interceptor.FileInterceptor;
import com.bigcat.everything_cat.core.interceptor.impl.FileIndexInterceptor;
import com.bigcat.everything_cat.core.interceptor.impl.FilePrintInterceptor;

import javax.sql.DataSource;

public interface FileScan {

    //path -> FIle -> Thing -> Database

    /**
     * 将指定path路径下的所有目录和文件以及子目录和文件递归扫描索引到数据库
     * @param path :
     * @return
     */
    void index(String path);
    /**
     * 拦截器对象
     * @param interceptor :
     * @return
     */
    void interceptor(FileInterceptor interceptor);

    public static void main(String[] args) {
        FileScan fileScan = new FileScanImpl();
        //打印输出拦截器
        fileScan.interceptor(new FilePrintInterceptor());
        //索引文件到数据库的拦截器
        DataSource dataSource = DataSourceFactory.getInstance();
        FileIndexDao fileIndexDao = new FileIndexDaoImpl(dataSource);
        fileScan.interceptor(new FileIndexInterceptor(fileIndexDao));
        fileScan.index("C:\\Users\\10762\\Documents");
    }
}

