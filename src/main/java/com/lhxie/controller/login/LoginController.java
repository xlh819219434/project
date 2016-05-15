package com.lhxie.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhxie.service.user.IUserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	IUserService userServiceImpl;
	@RequestMapping("/login")
	public String login() {
		return "/user/login";
	}
	
	@RequestMapping("/checkLogin")
	@ResponseBody
	public String checkLogin() {
		JSONObject json = new JSONObject();
		json.put("result", "success");
		return json.toJSONString();
	}
	@RequestMapping("/register")
	public String registerUser() {
		return "/user/register";
	}
}
