<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page errorPage="ExceptionPage.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
<link href="css/header.css" rel="stylesheet" type="text/css">
<link href="css/users.css" rel="stylesheet" type="text/css">
<script src="<c:url value="/js/jquery.min.js"/>" type="text/javascript"></script>
<script src="js/user.js" type="text/javascript"></script>
<link href="css/users.css" rel="stylesheet" type="text/css">
<link href="css/jqueryui.css" rel="stylesheet" type="text/css">
<link href="css/jquery.dataTables.css" rel="stylesheet" type="text/css">
<script src="js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="js/datatables.jqueryui.js" type="text/javascript"></script>
<link href="css/other.css" rel="stylesheet" type="text/css">
<link href="css/SpryMenuBarHorizontal.css" rel="stylesheet"
	type="text/css">
<style type="text/css">

</style>
<script>
	$(document).ready(function() {
	var exception="<%=request.getAttribute("exception")%>";
		if (exception == "exception") {
			$("h2").html("Exception page");
			$("div").css("background-color", "#f8f8f8");
		}

	});
	
	function validateForm()
	{
		//var userName = $('#ntId').val;
		var flag = true;
		/* var ftName = document.getElementById('firstName').value;
		var middleName = document.getElementById('middleName').value;
		var role = document.getElementById('role').value;
		var productCat = document.getElementById('productCategory').value;
		var branch = document.getElementById('branch').value;
		if(null!=ftName && ftName=='')
			flag = false;
		else if(null!=middleName && middleName=='')
			flag = false;
		else if(null!=role && role=='')
			flag = false;
		else if(null!=productCat && productCat=='')
			flag = false;
		else if(null!=branch && branch=='')
			flag = false;
		
		if(!flag)
		{
			alert('Mandatory fields are missing');
		}
		else */
		{
		 if(confirm("Are you sure want to update user?"))
			   flag = true;
		 else
			flag = false;
		}
	return flag;
	}
</script>
</head>

<body class="page-width">
<%@ include file="header.jsp"%>
<div id="apDiv5">
<c:if test="${not empty responseMessage}">
<div style="color: red;" align="center">${responseMessage}</div>

</c:if>
<div class="header-background">
		<c:if test="${edit eq false}">
			<h2>Create User</h2>
		</c:if>
		<c:if test="${edit eq true}">
			<h2>Update User</h2>
		</c:if>
	</div>
	<br>
	<c:if test="${edit eq false}">
			<div class="leftdiv">
				<form:form modelAttribute="updateUserForm" name="createUser"	action="createUser" method="post">
					<table style="font-family: Arial; font-size: 12px;">
						<tr class="hiddenRow"></tr>
						<tr>
						<td>User ID<span id="asterisk">*</span></td>
							<td><input style="width: 175px;" id="name1" name="name1" value="${userName}"
								maxlength="20" type="text" readonly><input type="hidden"
								id="unlock" name="unlock" value=0></td>
							<td><label id="errorName" style="color: red">Enter
									Name...</label><label id="errornamemaxlength" style="color: red">Name
									Error.Maximum size is 20 Charecters.</label></td>
									
							<form:hidden path="name" value="${userName}"/> </td>
						</tr>
						<tr>
						<td>Role</td>			
						<td><form:select path="role" name="role" style="width: 175px;">
									<option class="option45" value="">Please Select</option>
									<c:forEach items="${roles}" var="current">
											<option class="option45" value="${current.rid}">${current.roleName}</option>									
									</c:forEach>
									</form:select>
							</td>
						</tr>
						<tr>
						<td>Product Category</td>			
						<td><form:select path="productCategory" name="productCategory" style="width: 175px;">
									<option class="option45" value="">Please Select</option>
									<c:forEach items="${productCategory}" var="current">
											<option class="option45" value="${current.productCategoryId}">${current.productCategory}</option>
									</c:forEach>
									</form:select>
							</td>
						</tr>
						<tr>
							<td>Branch<span id="asterisk">*</span></td>
							<td><form:select path="branch" name="branch" style="width: 175px;">
									<option class="option45" value="">Please Select</option>
									<c:forEach items="${branches}" var="current">
										<option class="option45" value="${current.bid}">${current.branchName}</option>
									</c:forEach>
									</form:select>
							</td>
						</tr>
						</table>
						<div class="btn" style="text-align: right; dispay: block">
				<input id="save" class="wpsButtonText" type="submit" name="search"
					value="Add"/><input id="cancel" type="button"
					onclick="window.location.href='<%=request.getContextPath()%>/search?responseMessage='"
					value="Cancel" />
			</div>
						</form:form>
			</div>
	</c:if>
	<c:if test="${edit eq true}">
			<div class="leftdiv">
			<form:form modelAttribute="updateUserForm" action="update?urpcid=${urpcid}"	method="post">
					<table>
						<tr class="hiddenRow"></tr>
						<tr>
						<td>User ID</td>
							<td><input style="width: 175px;" id="name" name="name" value="${editUser.userName}"
								maxlength="20" type="text" readonly/><input type="hidden"
								id="unlock" name="unlock" value=0></td>
							<td><label id="errorName" style="color: red">Enter
									Name...</label><label id="errornamemaxlength" style="color: red">Name
									Error.Maximum size is 20 Charecters.</label></td>
									
						<td><form:hidden path="existingRole" value="${existingRoleName}"/><form:hidden path="name" value="${userName}"/> </td>
						
						</tr>
						<br>
						<tr>
						<td>Role<span id="asterisk">*</span></td>			
						<td><form:select path="role" name="role" style="width: 175px;">
									<c:forEach items="${roles}" var="current">
									<c:if test="${editUser.rid eq current.rid}">
											<option class="option45" value="${current.rid}" selected>${current.roleName}</option>
										</c:if>
										<c:if test="${editUser.rid ne current.rid}">
											<option class="option45" value="${current.rid}">${current.roleName}</option>
										</c:if>
									</c:forEach>
									</form:select>
							</td>
						</tr>
						<br>
						<tr>
						<td>Product Category<span id="asterisk">*</span></td>			
						<td><form:select path="productCategory" name="productCategory" style="width: 175px;">
									<c:forEach items="${productCategory}" var="current">
									<c:if test="${editUser.productCategoryId eq current.productCategoryId}">
											<option class="option45" value="${current.productCategoryId}" selected>${current.productCategory}</option>
										</c:if>
										<c:if test="${editUser.productCategoryId ne current.productCategoryId}">
											<option class="option45" value="${current.productCategoryId}">${current.productCategory}</option>
										</c:if>
									</c:forEach>
									</form:select>
							</td>
						</tr>
						<br>
						<tr>
							<td>Branch<span id="asterisk">*</span></td>
							<td><form:select path="branch" name="branch" style="width: 175px;">
									<c:forEach items="${branches}" var="current">
									<c:if test="${editUser.bid eq current.bid}">
											<option class="option45" value="${current.bid}" selected>${current.branchName}</option>
										</c:if>
										<c:if test="${editUser.bid ne current.bid}">
											<option class="option45" value="${current.bid}">${current.branchName}</option>
										</c:if>										
									</c:forEach>
									</form:select>
							</td>
						</tr>
						<%-- <tr>
						<td>Branch</td>
		<td><form:select path="branchName" name="branchName" id="branchName" style="width: 175px;" multiple="true">
				<c:forEach items="${editUser.branchName}" var="current">
				<form:option class="option45" value="${current}" selected="selected">${current}</form:option>
				</c:forEach>
				</form:select>
		</td>
		<td><input id="moveright" type="button" value=">>"  onclick="move_list_items('branchName','masterSetBranchName');" /><br><br>
		<input id="moveleft" type="button" value="<<" onclick="move_list_items('masterSetBranchName','branchName');" /></td>
		
		<td><form:select path="masterSetBranchName" name="masterSetBranchName" id="masterSetBranchName" style="width: 175px;" multiple="true">
		<c:forEach items="${branches}" var="current">
			<form:option class="option45" value="${current.branchName}">${current.branchName}</form:option>
		</c:forEach>
		</form:select>
		</td>
		</tr> --%>
					</table>
					<div class="btn" style="text-align: right; dispay: block">
				<input id="save" class="wpsButtonText" type="submit" name="Update"
					value="Update" onClick="return validateForm()"/><input id="cancel" type="button"
					onclick="window.location.href='<%=request.getContextPath()%>/search?responseMessage='"
					value="Cancel" />
			</div>
			</form:form>
			</div>
	</c:if>
	</div>
	</body>
	</html>