<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%String path = request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/themes/icon.css"/>
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>
</head>
<body style="background: #FFFFFF">
	<form id="addForm" action="<%=path %>/role/saveRole.do" method="post">
		<table width="100%">
			<tr>
				<td align="right">角色名称：</td>
				<td align="left">
					<form:input path="role.roleName" id="roleName" cssClass="easyui-textbox"
						data-options="required:true" missingMessage="角色名称必须填写"/>
				</td>
			</tr>
			<tr>
				<td align="right">角色描述：</td>
				<td align="left">
					<form:input path="role.roleDesc" id="roleDesc" cssClass="easyui-textbox"
						data-options="multiline:true" style="width:200px;height:70px"/>
				</td>
			</tr>
			<tr>
				<td align="right">菜单权限： </td>
				<td align="left">
					<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="loadMenu();">添加</a>
				</td>
			</tr>
			<tr><td colspan="2" align="left" style="padding-left: 30px"><span id="haveMenus"></span></td></tr>
		</table>
	</form>
</body>
</html>