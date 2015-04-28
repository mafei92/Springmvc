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
<script type="text/javascript">
$(document).ready(function(){
	$('#userList').datagrid({
        striped : true,//设置为true将交替显示行背景
        singleSelect: true,//为true时只能选择单行
        remoteSort : false,
        fitColumns: true,
        loadMsg : '数据加载中,请稍后......',
        pagination : true,//分页
        rownumbers : true,//行数 
		method :'POST',
		url :'<%=path%>/user/queryForJson.do',
		onLoadSuccess:function(data){
			
		},
		columns:[[
		  	//{field:'userId', width:$(this).width() * 0.36, align:'center', title:'用户id'},
		  	{field:'userName', width:$(this).width() * 0.2, align:'center', title:'用户名'},
		  	{field:'trueName', width:$(this).width() * 0.2, align:'center', title:'姓名'},
		  	{field:'mail', width:$(this).width() * 0.3, align:'center', title:'邮箱'},
		  	{field:'phone', width:$(this).width() * 0.2, align:'center', title:'电话'},
		  	{field:'createTime', width:$(this).width() * 0.2, align:'center', title:'创建时间',
		  		formatter:function(value,row){
		  			if(value != null && value != ''){
		  				return formatDate(value);
		  			}else{
		  				return "";
		  			}
			    }	
		  	},
		  	{field:'updateTime', width:$(this).width() * 0.2, align:'center', title:'修改时间',
		  		formatter:function(value,row){
		  			if(value != null && value != ''){
		  				return formatDate(value);
		  			}else{
		  				return "";
		  			}
			    }		
		  	},
		  	{field:'userLevel', width:$(this).width() * 0.1, align:'center', title:'级别'}
		]]
	});
});
function formatDate(date){
	var date = new Date(date);
	var year = date.getFullYear();
	var month = ("0" + (date.getMonth() + 1)).slice(-2);
	var day = ("0" + date.getDate()).slice(-2);
	var h = ("0" + date.getHours()).slice(-2);
	var m = ("0" + date.getMinutes()).slice(-2);
	var s = ("0" + date.getSeconds()).slice(-2);
	return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
}
</script>
</head>
<body style="background: #FFFFFF">
	<table id="userList" title="用户列表"></table>
</body>
</html>