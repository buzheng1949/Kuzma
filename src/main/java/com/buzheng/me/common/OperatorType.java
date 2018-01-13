package com.buzheng.me.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by buzheng on 18/1/13.
 * 操作类型
 */
public enum OperatorType {

    DELETE(1, "删除"),
    UPDATE(2, "修改"),
    SCAN(0, "浏览");
    @Getter
    @Setter
    public Integer type;
    @Getter
    @Setter
    public String desc;


    OperatorType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
