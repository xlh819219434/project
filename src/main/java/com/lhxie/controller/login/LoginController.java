package com.lhxie.controller.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
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
	public String checkLogin(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		JSONObject json = new JSONObject();
		json.put("result", "success");
		ServletOutputStream sos = resp.getOutputStream();
		sos.print(JSON.toJSONString(json));
		sos.flush();
		sos.close();
		return json.toJSONString();
	}
	@RequestMapping("/register")
	public String registerUser() {
		return "/user/register";
	}
}
