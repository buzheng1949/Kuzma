package com.buzheng.me.domain;

import lombok.Data;

@Data
public class ActionLog {
    private Integer id;

    private Integer userId;

    private Integer tradeItemId;

    private Integer type;

    private Integer time;


}