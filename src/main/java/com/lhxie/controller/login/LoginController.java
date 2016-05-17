package com.lhxie.controller.login;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lhxie.common.Tool.StringTool;
import com.lhxie.common.Tool.Tool;
import com.lhxie.model.user.User;
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
	public Map<String,Object> checkLogin(HttpServletRequest req, HttpServletResponse resp,
			User user) throws Exception {
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		String result = "";

		String encodePasswd = StringTool.toMD5HexString(user.getPasswd());
		if (encodePasswd == null) {
			result = "用户名或者密码错误";
		}
		User tempUser = userServiceImpl.selectByUserid(user.getUserid());
		if (tempUser != null) {
			if (!encodePasswd.equals(tempUser.getPasswd())) {
				result = "用户名或者密码错误";
			} else {
				result = "success";
			}

		} else {
			result = "用户名或者密码错误";
		}
		// 登录成功，写入cookie
		if ("success".equals(result)) {
			String sessionId = String.valueOf(Tool.createId());
			req.getSession().setAttribute(sessionId, tempUser);
			req.getSession().setAttribute("user", tempUser);
			String host = req.getServerName();
			// 保存密码到Cookie，注意需要加密一下
			Cookie cookie = new Cookie("sessionid", sessionId);
			cookie.setPath("/");
			cookie.setDomain(host);
			cookie.setMaxAge(99999999);
			resp.addCookie(cookie);
		}
		rtnMap.put("rtnMsg", result);
		return rtnMap;
	}

	@RequestMapping("/register")
	public String registerUser() {
		return "/user/register";
	}
}
