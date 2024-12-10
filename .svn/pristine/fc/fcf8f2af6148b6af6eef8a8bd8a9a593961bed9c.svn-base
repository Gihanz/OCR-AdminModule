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
<link href="css/login.css" rel="stylesheet" type="text/css">
<script src="<c:url value="/js/jquery.min.js"/>" type="text/javascript"></script>
<script src="js/user.js" type="text/javascript"></script>
<link href="css/users.css" rel="stylesheet" type="text/css">
<link href="css/jqueryui.css" rel="stylesheet" type="text/css">
<link href="css/jquery.dataTables.css" rel="stylesheet" type="text/css">
<script src="js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="js/datatables.jqueryui.js" type="text/javascript"></script>
<link href="css/other.css" rel="stylesheet" type="text/css">
<link href="css/SpryMenuBarHorizontal.css" rel="stylesheet"	type="text/css">
<script type="text/javascript">

function validateForm()
{
	//var userName = $('#ntId').val;
	var flag = true;
	var ftName = document.getElementById('firstName').value;
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
	else
	{
		var name = document.getElementById('name').value;
		var branch = document.getElementById('branch').options[document.getElementById("branch").selectedIndex ].text;
		var role = document.getElementById('role').options[document.getElementById("role").selectedIndex ].text;
	 var output = " '"+name+"'  with role '"+role+"' for branch '"+branch+"'";
	 if(confirm("Are you sure want to create user "+output))
		   flag = true;
	 else
		flag = false;
	}
return flag;
}
</script>
</head>

<body background="<c:url value="/images/CaseManagerBackground.jpg"/>">
<%@ include file="header.jsp"%>
<div id="apDiv5">
<c:if test="${not empty responseMessage}">
<div style="color: red;" align="center">${responseMessage}</div>

</c:if>
	<form:form modelAttribute="updateUserForm" action="getUserAD" method="post">
<table align="center">
						<tr>
							<td>User :<td>
							<td><form:input path="ntId" name="ntId" id="ntId"  style="width:250px;height:25px;"></form:input></td>
							<td><input type="submit" name="search" value="Search"/></td>
							<td><input type="button" onclick="window.location.href='<%=request.getContextPath()%>/searchAD?responseMessage='" value="Cancel"/></td>
							</tr>			
			</form:form>
			</table>
	<c:if test="${edit eq false}">
<%-- 	<div class="header-background">
		<c:if test="${edit eq false}">
			<h2>Create User</h2>
		</c:if>
		<c:if test="${edit eq true}">
			<h2>Create User</h2>
		</c:if>
	</div> --%>
	<br>
			<div id="apDiv51">
				<form:form modelAttribute="updateUserForm" name="createUser"	action="createUser" method="post">
					<table >
					<tr></tr>
						<tr></tr>
						<tr>
						<tr class="hiddenRow"></tr>
						<tr>
						<td>User ID:</td>
							<td><input style="width: 160px;" id="name" name="name" value="${userName}"
								maxlength="20" type="text" readonly>
									
							<!--<td><form:input path="ntId" name="ntId" id="ntId" onkeyup="lookUpPFNum(this.value);"  style="width:250px;height:25px;" onblur="fill();"></form:input></td>
  						 <div class="suggestionsBox" id="suggestions" style="display: none;">
					<div class="suggestionList" id="autoSuggestionsList">
					</div>
				</div> -->
						</tr><tr></tr><tr></tr><tr></tr>
						<tr>
						<td>Initials:<span id="asterisk">*</span></td>				
						<td>
						<form:input path="firstName" name="firstName" id="firstName" style="width: 160px;"></form:input>
						</td>
						</tr>
						<tr><tr></tr><tr></tr><tr></tr>
						<td>Last Name:<span id="asterisk">*</span></td>				
						<td>
						<form:input path="middleName" name="middleName" id="middleName"value="" style="width: 160px;"/>
						</td>
						</tr><tr></tr><tr></tr><tr></tr>
						<tr>
						<td>Role:<span id="asterisk">*</span></td>			
						<td><form:select path="role" name="role" style="width: 200px;">
									<option class="option45" value="">Please Select</option>
									<c:forEach items="${roles}" var="current">
											<option class="option45" value="${current.rid}">${current.roleName}</option>									
									</c:forEach>
									</form:select>
							</td>
						</tr><tr></tr><tr></tr><tr></tr>
						<tr>
						<td>Product Category:<span id="asterisk">*</span></td>			
						<td><form:select path="productCategory" name="productCategory" style="width: 200px;">
									<option class="option45" value="">Please Select</option>
									<c:forEach items="${productCategory}" var="current">
											<option class="option45" value="${current.productCategoryId}">${current.productCategory}</option>
									</c:forEach>
									</form:select>
							</td>
						</tr><tr></tr><tr></tr><tr></tr>
						<tr>
							<td>Branch:<span id="asterisk">*</span></td>
							<td><form:select path="branch" name="branch" style="width: 200px;">
									<option class="option45" value="">Please Select</option>
									<c:forEach items="${branches}" var="current">
										<option class="option45" value="${current.bid}">${current.branchName}</option>
									</c:forEach>
									</form:select>
							</td>
						</tr><tr></tr><tr></tr><tr></tr>
						</table>
						<table><tr><td>
						<input type="submit" name="search" value="Add" onClick="return validateForm()" /></td>
						<td><input type="button" onclick="window.location.href='<%=request.getContextPath()%>/searchAD?responseMessage='" value="Cancel" /></td>
						</tr>
			</table>
						</form:form>
			</div>
	</c:if>
	</div>
	</body>
	</html>