<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib
	prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String ctx = request.getContextPath();
	if ("/".equals(ctx)) {
		ctx = "";
	}
	request.setAttribute("ctx", ctx);
%>