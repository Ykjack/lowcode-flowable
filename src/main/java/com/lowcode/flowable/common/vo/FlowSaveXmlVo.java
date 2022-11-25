package com.lowcode.flowable.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @PACKAGE_NAME: com.lowcode.flowable.common.vo
 * @NAME: FlowSaveXmlVo
 * @USER: jack
 * @DATE: 11/25/2022
 * @PROJECT_NAME: flowable-master
 **/
@Data
public class FlowSaveXmlVo implements Serializable {

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程分类
     */
    private String category;

    /**
     * xml 文件
     */
    private String xml;
}
