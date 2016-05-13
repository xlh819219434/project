package com.lhxie.dao;

import java.util.List;

import com.lhxie.model.Student;

public interface StudentMapper {
	int deleteByPrimaryKey(Integer userid);

	int insert(Student record);

	int insertSelective(Student record);

	Student selectByPrimaryKey(Integer userid);

	int updateByPrimaryKeySelective(Student record);

	int updateByPrimaryKey(Student record);

	public List<Student> listAllStudent();
}