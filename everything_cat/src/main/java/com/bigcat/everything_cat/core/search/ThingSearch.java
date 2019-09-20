package com.bigcat.everything_cat.core.search;

import com.bigcat.everything_cat.core.model.Condition;
import com.bigcat.everything_cat.core.model.Thing;

import java.util.List;

/**
 * @auther koekie
 * @date 2019/9/18 19:19
 * @function文件检索
 */

public interface ThingSearch {

    /**
     * 根据condition条件检索数据
     * @param condition :
     * @return
     */
    List<Thing> search(Condition condition);
}
