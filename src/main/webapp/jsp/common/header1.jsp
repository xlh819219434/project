<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
%>
<header class="navbar navbar-inverse navbar-fixed-top docs-nav headerDiv" role="banner">
   <div class="container">
   		<div class="row">
   			<div class="col-md-12">
		       <div class="navbar-header">
		           <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
		               <span class="sr-only">Toggle navigation</span>
		               <span class="icon-bar"></span>
		               <span class="icon-bar"></span>
		               <span class="icon-bar"></span>
		           </button>
		           <a href="${ctx}/" class="navbar-brand">
		               <img src="${ctx}/skins/skin1/images/logo.png" alt="Noteshare欢迎你！">
		           </a>
		       </div>
		       <nav id="hb-nav" class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
		           <ul class="nav navbar-nav">
		               <li><a href="http://noteshare.site" target="_blank">IT导航</a></li>
		           </ul>
		           	<c:choose>
						<c:when test="${empty user}">
				           <ul class="nav navbar-nav navbar-right">
				               <li><a href="${ctx}/user/gotoLogin.html"><i class="fa fa-sign-in"></i><span class="glyphicon glyphicon-share" style="margin-right: 4px;"></span>登录 </a></li>
				               <li><a href="${ctx}/user/gotoRegister.html"><i class="fa fa-pencil"></i><span class="glyphicon glyphicon-pencil" style="margin-right: 4px;"></span>注册</a></li>
				           </ul>
						</c:when>
						<c:otherwise>
				           <ul class="nav navbar-nav navbar-right">
				               <li id="userInfo" class="dropdown">
				                   <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
				                       <img class="img-circle Icon" src="${ctx}/userImages/defaultUserPic.jpg">
				                      		${user.nickname}
				                       <b class="caret"></b>
				                   </a>
				                   <ul id="userMenu" class="dropdown-menu">
				                       <li>
				                           <a href="javascript:void(0);">我的消息
				                               <span class="fa fa-envelope pull-right"></span>
				                           </a>
				                       </li>
				                       <li>
				                           <a href="${ctx}/note/myNoteManager.html">我的笔记
				                               <span class="fa fa-envelope pull-right"></span>
				                           </a>
				                       </li>
				                       <li class="divider"></li>
				                       <li><a href="${ctx}/user/gotoUserDetail.html">账号设置
				                           <span class="glyphicon glyphicon-cog pull-right"></span></a>
				                       </li>
				                       <li class="divider"></li>
				                       <li><a href="${ctx}/user/logout.html">安全退出
				                               <span class="glyphicon glyphicon-log-out pull-right"></span></a>
				                       </li>
				                   </ul>
				               </li>
				           </ul>
						</c:otherwise>
					</c:choose>
		       </nav>
   			</div>
   		</div>
   </div>
</header>