package com.lhxie.dao;

import com.lhxie.model.Course;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer courseid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer courseid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}