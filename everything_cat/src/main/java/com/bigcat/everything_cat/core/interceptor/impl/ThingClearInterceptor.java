package com.bigcat.everything_cat.core.interceptor.impl;

import com.bigcat.everything_cat.core.dao.FileIndexDao;
import com.bigcat.everything_cat.core.interceptor.ThingInterceptor;
import com.bigcat.everything_cat.core.model.Thing;

import java.util.Queue;

/**
 * @Author:koekie
 * @Date: 2019/9/19
 * @Description:
 */
public class ThingClearInterceptor implements Runnable,ThingInterceptor {

    private final FileIndexDao fileIndexDao;
    private final Queue<Thing> thingQueue;

    public ThingClearInterceptor(FileIndexDao fileIndexDao, Queue<Thing> thingQueue) {
        this.fileIndexDao = fileIndexDao;
        this.thingQueue = thingQueue;
    }

    @Override
    public void apply(Thing thing) {
        this.fileIndexDao.delete(thing);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thing thing = this.thingQueue.poll();
            if (thing != null) {
                this.apply(thing);
            }
        }
    }
}

