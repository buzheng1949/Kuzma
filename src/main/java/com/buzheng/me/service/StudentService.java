package com.buzheng.me.service;

import org.springframework.stereotype.Service;

/**
 * Created by buzheng on 18/1/13.
 */
public interface StudentService {
    /**
     * 删除学生信息
     */
    void deleteStudentInformation();

    /**
     * 更新学生信息
     */
    void updateStudentInformation();

    /**
     * 插入学生信息
     */
    void insertStudentInformation();
}
