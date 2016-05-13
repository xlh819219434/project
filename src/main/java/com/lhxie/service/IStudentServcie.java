package com.lhxie.service;

import java.util.List;

import com.lhxie.model.Student;

public interface IStudentServcie {
	public List<Student> listAllStudent();
	
	public Student findById(int id);
}
