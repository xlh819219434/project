package com.lhxie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhxie.dao.StudentMapper;
import com.lhxie.model.Student;
import com.lhxie.service.IStudentServcie;

@Service
public class StudentServcieImpl implements IStudentServcie {
	@Autowired
	private StudentMapper studentMapper;

	@Override
	public List<Student> listAllStudent() {
		return studentMapper.listAllStudent();
	}

	@Override
	public Student findById(int id) {
		return studentMapper.selectByPrimaryKey(id);
	}

}
