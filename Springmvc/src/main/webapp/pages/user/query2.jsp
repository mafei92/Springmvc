<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
%>

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
	
	<table id="dg" title="Client Side Pagination" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:true,
				pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th field="cb" align="center"><input type="checkbox" /></th>
				<th field="id" align="center">编号</th>
				<th field="name" align="center">用户名</th>
				<th field="mail" align="center">电子邮件</th>
				<th field="phone" align="center">电话</th>
				<th field="createTime" align="center">创建时间</th>
				<th field="updateTime" align="center">修改时间</th>
				<th field="userLevel" align="center">级别</th>
			</tr>
		</thead>
	</table>
	<script>
		var rows = [];
		jQuery.ajax({
			async: false,
	        type: "POST",
	        cache: false,
	        url: "<%=path %>/user/queryForJson.do",
	        dataType: "json",
	        timeout: 5000,
	        global: false,
	        success: function (data){
	        	var list = data.userList;
	            for(var i = 0; i < list.length; i++){
					rows.push({
						cb: '<input type="checkbox" onclick="selected();"/>',
						id: list[i].userId,
						name: list[i].userName,
						mail: list[i].mail,
						phone: list[i].phone,
						createTime: list[i].createTime,
						updateTime: list[i].updateTime,
						userLevel: list[i].userLevel
					});
				}
	        },
			error:function(){
				alert("error");
			}
	    });
		
		function getData(){
			return rows;
		}
		
		function pagerFilter(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
				data = {
					total: data.length,
					rows: data
				}
			}
			var dg = $(this);
			var opts = dg.datagrid('options');
			var pager = dg.datagrid('getPager');
			pager.pagination({
				onSelectPage:function(pageNum, pageSize){
					opts.pageNumber = pageNum;
					opts.pageSize = pageSize;
					pager.pagination('refresh',{
						pageNumber:pageNum,
						pageSize:pageSize
					});
					dg.datagrid('loadData',data);
				}
			});
			if (!data.originalRows){		
				data.originalRows = (data.rows);
			}
			var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
			var end = start + parseInt(opts.pageSize);
			data.rows = (data.originalRows.slice(start, end));
			return data;
		}
		
		$(function(){
			$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
		});

		function selected(){
			var ro = $("#dg").datagrid('getSelected');
			if(ro){
				alert(ro.id+","+ro.name);
			}
		}
	</script>
</body>
</html>