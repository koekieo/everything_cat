package com.bigcat.everything_cat.core;

import com.bigcat.everything_cat.config.EverythingConfig;
import com.bigcat.everything_cat.config.HandlerPath;
import com.bigcat.everything_cat.core.dao.DataSourceFactory;
import com.bigcat.everything_cat.core.dao.FileIndexDao;
import com.bigcat.everything_cat.core.dao.impl.FileIndexDaoImpl;
import com.bigcat.everything_cat.core.index.FileScan;
import com.bigcat.everything_cat.core.index.impl.FileScanImpl;
import com.bigcat.everything_cat.core.interceptor.impl.FileIndexInterceptor;
import com.bigcat.everything_cat.core.interceptor.impl.FilePrintInterceptor;
import com.bigcat.everything_cat.core.model.Condition;
import com.bigcat.everything_cat.core.model.Thing;
import com.bigcat.everything_cat.core.search.ThingSearch;
import com.bigcat.everything_cat.core.search.impl.ThingSearchImpl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 统一调度器 索引 检索
 * <p>
 * 配置,索引模块,检索模块,拦截器模块组合调度
 *
 */
public class EveryThingManager {

    private static volatile EveryThingManager manager;

    /**
     * 业务层
     */
    private FileScan fileScan;

    private ThingSearch thingSearch;

    //线程池的执行器
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
            .availableProcessors() *2);

    private EverythingConfig config = EverythingConfig.getInstance();
    private EveryThingManager() {

        /*
      数据库访问层
     */ /**
         * 数据库访问层
         */FileIndexDao fileIndexDao = new FileIndexDaoImpl(DataSourceFactory.getInstance());
        this.fileScan = new FileScanImpl();
        //打印缩影信息的拦截器
//        this.fileScan.interceptor(new FilePrintInterceptor());
        //索引信息写数据库的拦截器
        this.fileScan.interceptor(new FileIndexInterceptor(fileIndexDao));
        this.thingSearch = new ThingSearchImpl(fileIndexDao);

    }

    public static EveryThingManager getInstance() {
        if(manager == null) {
            synchronized (EveryThingManager.class) {
                if(manager == null) {
                    manager = new EveryThingManager();
                }
            }
        }
        return manager;
    }

    /**
     * 构建索引
     */
    public void buildIndex() {
        //建立索引
        DataSourceFactory.databaseInit(true);
        HandlerPath handlerPath = config.getHandlerPath();
        Set<String> includePaths = handlerPath.getIncludePath();
        new Thread(() -> {
                System.out.println("Build Index Started ...");
                final CountDownLatch countDownLatch = new CountDownLatch(includePaths.size());
                for (String path: includePaths) {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            fileScan.index(path);
                            countDownLatch.countDown();
                        }
                    });
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Build Index complete ...");
        }).start();


    }

    /**
     * 检索
     */

    public List<Thing> search(Condition condition) {
        //Condition 用户提供的是: name file_type
        //limit orderBy
        condition.setLimit(config.getMaxReturn());
        condition.setOrderByDepthAsc(!config.getOrderByDesc());
        return this.thingSearch.search(condition);
    }

    /**
     * 帮助
     */
    public void help() {
        System.out.println("命令列表:");
        System.out.println("退出:quit");
        System.out.println("帮助:help");
        System.out.println("索引:index");
        System.out.println("搜索:search <name> [<file-Type> img | doc" +
                " | bin | archive | other]");


    }

    /**
     * 退出
     */
    public void quit() {
        System.out.println("欢迎使用,再见");
        System.exit(0);
    }

}

