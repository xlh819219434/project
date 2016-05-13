package com.lhxie.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lhxie.model.Student;
import com.lhxie.service.IStudentServcie;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private IStudentServcie studentServcieImpl;

	@RequestMapping("/showUser/{id}")
	// 推荐使用的方式
	public String showUser(@PathVariable int id, HttpServletRequest req) {

		Student user = studentServcieImpl.findById(id);

		req.setAttribute("user", user);

		return "showUser";
	}
}
