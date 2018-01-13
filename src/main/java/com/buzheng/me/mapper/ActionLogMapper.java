package com.buzheng.me.mapper;

import com.buzheng.me.domain.ActionLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface ActionLogMapper {

    /**
     * 增加操作记录
     * @param record  增加操作记录
     * @return
     */
    @Insert("INSERT INTO ActionLog(userId, tradeItemId,type, time) VALUES(#{userId}, #{tradeItemId},#{type},#{time})")
    int insert(ActionLog record);


}