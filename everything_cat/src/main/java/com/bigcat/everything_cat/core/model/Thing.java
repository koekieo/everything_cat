package com.bigcat.everything_cat.core.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther koekie
 * @date 2019/3/26 19:20
 * @function
 */
@Data
public class Thing {

    /*
     *文件名称(不含路径)
     */
    private String name;

    /**
    * 文件路径
    */
    private String path;

    /**
     * 文件路径深度
     */
    private Integer depth;

    /**
     * 文件类型
     */
    private FileType fileType;
}

