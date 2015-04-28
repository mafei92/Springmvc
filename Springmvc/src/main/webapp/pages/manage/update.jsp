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
<title>add</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common.css"/>
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
    $("#takeTime").datetimebox();
    $(".datebox :text").attr("readonly","readonly");
});

function update(){
	var validate = $("#form1").form('enableValidation').form('validate');
	if(validate){
		$("#form1").submit();
		waiting();
	}else{
		return;
	}
}

function checkPrice(price){
  return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(price.toString());
}

function returnQuery(){
	var form = document.forms[1];
	form.action = "<%=path%>/manageMoney/queryManageMoney.do";
	form.submit();
	waiting();
}
</script>
</head>
<body style="background: #FFFFFF">
	<form id="form1" name="form1" method="post" action="<%=path%>/manageMoney/updateManageMoney.do">
	<fieldset class="fieldset-self">
	<legend>更新理财记录</legend>
		<table width="100%">
			<tr>
				<td align="right">途径：</td>
				<td align="left">
					<form:select path="manageMoney.inOrOut" id="inOrOut" 
							cssClass="easyui-combobox" cssStyle="width:150px;"
							data-options="required:true" missingMessage="途径必须填写">
						<form:option value="1" label="收入"/>
						<form:option value="2" label="支出"/>
					</form:select>
				</td>
				<td align="right">金额：</td>
				<td align="left"><form:input path="manageMoney.howMuch" id="howMuch"
						cssClass="easyui-textbox" data-options="required:true,validType:'money'"
						missingMessage="金额必须填写" invalidMessage="请输入有效的金额"/></td>
				<td align="right">时间：</td>
				<td align="left">
					<form:input path="manageMoney.takeTime" id="takeTime"
						data-options="required:true" missingMessage="时间必须填写"
						cssStyle="150px;"/>
					<!--<form:input path="manageMoney.takeTime" id="takeTime" cssClass="easyui-datetimebox"/>-->
				</td>
				<td align="right">当事人：</td>
				<td align="left">
					<c:if test="${user.userLevel == '0'}">
						<form:select path="manageMoney.takeId" id="takeId" cssStyle="width:150px;"
							items="${userList }" itemValue="userId" itemLabel="trueName" 
							cssClass="easyui-combobox" data-options="required:true"
							missingMessage="当事人必须填写"/>
					</c:if>
					<c:if test="${user.userLevel != '0'}">
						<input type="text" value="${user.trueName }" 
							class="easyui-textbox" data-options="required:true"
							missingMessage="当事人必须填写" readonly="true"/>
						<form:hidden path="manageMoney.takeId" id="takeId" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td align="right">事由：</td>
				<td align="left" colspan="3">
					<form:input path="manageMoney.incident" id="incident" 
						cssClass="easyui-textbox" data-options="required:true"
						missingMessage="事由必须填写" cssStyle="width:450px;"/></td>
				<td align="right">是否必要：</td>
				<td align="left">
					<form:select path="manageMoney.ifTake" id="ifTake" 
							cssClass="easyui-combobox" cssStyle="width:150px;"
							data-options="required:true" missingMessage="是否必要必须填写">
						<form:option value="0" label="必要" />
						<form:option value="1" label="非必要"/>
					</form:select>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="8">
					备注：
				</td>
			</tr>
			<tr>
				<td colspan="8">
					<form:textarea path="manageMoney.remark" id="remark" 
						cssClass="easyui-textbox" data-options="multiline:true"
						cssStyle="height:100px;width:99.5%"/>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="8">
					<form:hidden path="manageMoney.manageId" id="manageId"/>
					<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="update();">保存</a>&nbsp;
					<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="returnQuery();">取消</a>&nbsp;
				</td>
			</tr>
		</table>
	</fieldset>
	</form>
	<form></form>
</body>

</html>
