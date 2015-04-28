<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<style scoped="scoped">
.textbox {
	height: 20px;
	margin: 0;
	padding: 0 2px;
	box-sizing: content-box;
}
</style>
<script type="text/javascript">
$(function () {
    $("#establishTime").datetimebox();
    $(".datebox :text").attr("readonly","readonly");
});

</script>
</head>
<body style="background: #FFFFFF">
	<fieldset class="fieldset-self">
	<legend>部门详细信息</legend>
	<form:hidden path="dept.deptId" id="deptId"/>
	<table width="100%">
		<tr>
			<td align="right">部门编号：</td>
			<td align="left">${dept.deptNo }</td>
			<td align="right">部门名称：</td>
			<td align="left">${dept.deptName }</td>
			<td align="right">创立时间：</td>
			<td align="left"><fmt:formatDate value="${dept.establishTime }" pattern="yyyy-MM-dd"/></td>
		</tr>
		<tr>
			<td align="right">部门经理：</td>
			<td align="left">${trueName}</td>
			<td align="right">上级部门：</td>
			<td align="left">${superDeptName }</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">部门描述：</td>
			<td align="left" colspan="5">${dept.deptDesc}</td>
		</tr>
	</table>
	</fieldset>
</body>
</html>
