package com.bigcat.everything_cat.core.dao;

import com.bigcat.everything_cat.core.model.Condition;
import com.bigcat.everything_cat.core.model.Thing;

import java.util.List;

/**
 * @auther koekie
 * @date 2019/9/18 19:24
 * @function 数据库访问对象
 */

public interface FileIndexDao {

    void insert(Thing thing);

    void delete(Thing thing);

    List<Thing> query(Condition condition);

}

