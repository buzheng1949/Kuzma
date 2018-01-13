package com.buzheng.me.service.impl;

import com.buzheng.me.anno.Authority;
import com.buzheng.me.anno.Operator;
import com.buzheng.me.common.OperatorType;
import com.buzheng.me.domain.Student;
import com.buzheng.me.holder.UserHolder;
import com.buzheng.me.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by buzheng on 18/1/13.
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


    @Override
    @Authority
    @Operator(value = OperatorType.DELETE)
    public void deleteStudentInformation() {
        logger.error("delete student's information");
    }

    @Override
    @Authority
    @Operator(value = OperatorType.UPDATE)
    public void updateStudentInformation() {
        logger.error("update student's information");
    }

    @Override
    @Authority
    public void insertStudentInformation() {
        logger.error("insert student's information");
    }
}
