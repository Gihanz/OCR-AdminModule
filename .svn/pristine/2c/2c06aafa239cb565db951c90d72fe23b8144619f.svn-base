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
	
</script>
<script type="text/javascript">
function fill() {
	setTimeout("$('#suggestions').hide();", 200);
	setTimeout("$('#suggestions1').hide();", 200);
}
function lookUpPFNum(inputString) {
	if(inputString.length < 5) {
		$('#suggestions').hide();
	} else {
		$('#autoSuggestionsList').html("");
		<c:forEach items="${userIds}" var="current" varStatus="status">
		 	var pfNum = new String("<c:out value="${current.ntId}" />");
			for(var j=0;j<inputString.length;j++){
				if((pfNum.charAt(j))==(inputString.charAt(j).toUpperCase())) {
					if(inputString.length == j+1){
						$('#suggestions').show();
						$('#autoSuggestionsList').append("<li value=\""+pfNum+"\">"+pfNum+"\n"+"</li>");
						var count=0;
						$('#autoSuggestionsList li').click(function(event) {
							$('#ntId').val($.trim($(this).text()));
							var newKey = new String($.trim($(this).text()));
							if(count == 0){
							//populateName(newKey);
							count++;
							}
							setTimeout("$('#suggestions').hide();", 200);
							return false;
				        });
						break;
					}
				}
				else break;
			}
			</c:forEach>
	}
}
function validateForm()
{
	var flag=false;
	var name = document.getElementById('name').value;
	var branch = document.getElementById('branch').options[document.getElementById("branch").selectedIndex ].text;
	var role = document.getElementById('roleSet').options[document.getElementById("roleSet").selectedIndex ].text;
	var ouput = " '"+name+"' with roles '"+role+"'"+" of branch '"+branch+"'";
	if(confirm("Are you sure want to delete user"+ouput))
		   flag = true;
	 else
		flag = false;
	
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
<form:form modelAttribute="searchForm" action="deleteUser" method="post">
		<table align="center">
			<tr>
				<td><h3>User:</h3><td>
				<td><form:input path="ntId" name="ntId" id="ntId" onkeyup="lookUpPFNum(this.value);"  style="width:250px;height:25px;" onblur="fill();"></form:input></td>
		<td>
	<input type="submit" name="search" value="Search" /></td>
	<td><input type="button" onclick="window.location.href='<%=request.getContextPath()%>/deleteUser'" value="Cancel" /></td>
		<div class="suggestionsBox" id="suggestions" style="display: none;">
			<div class="suggestionList" id="autoSuggestionsList">
			</div>
		</div>
		</tr>
		</table>	
</form:form>
</div>
	<div class="header-background">
		<c:if test="${edit eq false}">
			<h2>Delete User</h2>
		</c:if>		
	</div>
	<br>
			<div class="leftdiv">
	<c:if test="${not empty updateUserForm}">
		<div id="apDiv51">
			<form:form modelAttribute="updateUserForm" action="submitDeleteUser"	method="post">
					<table>
					
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr class="hiddenRow"></tr>
						<tr>
						<td>User ID:</td>
							<td><input  path="name" id="name" name="name" value="${userName}"
								maxlength="20"  style="width: 160px;" type="text" readonly/><input type="hidden"
								id="unlock" name="unlock" value=0></td>
							<td><label id="errorName" style="color: red">Enter
									Name...</label><label id="errornamemaxlength" style="color: red">Name
									Error.Maximum size is 20 Charecters.</label></td>
									
						 <td><form:hidden path="ntId" value="${userName}"/></td>
						 <form:hidden path="existingBid" value="${updateUserForm.existingBid}"/> 
						 <form:hidden path="existingRoleSet" value="${updateUserForm.existingRoleSet}"/> 
						 <form:hidden path="existingProductCategorySet" value="${updateUserForm.existingProductCategorySet}"/> 
						 
						</tr>
						<tr></tr><tr></tr><tr></tr>
						<tr>
						<td>Initials:</td>				
						<td>
						<form:input path="firstName" name="firstName" id="firstName" style="width: 160px;" value="${firstName}" disabled="true"></form:input>
						</td>
						</tr>
						<tr></tr><tr></tr><tr></tr>
						<td>Last Name:</td>				
						<td>
						<form:input path="middleName" name="middleName" id="middleName"  style="width: 160px;" value="${middleName}" disabled="true"/>
						</td>
						</tr>
						<tr></tr><tr></tr><tr></tr>
						<tr>
						<td>Role:</td>			
						<td><form:select path="roleSet" name="roleSet" style="width: 200px;height:80px" multiple="true" disabled="true">
						<c:forEach items="${updateUserForm.roleSet}" var="current">
									<option class="option45" value="${current}" selected>${current}</option>
									</c:forEach> 
									</form:select>
							</td>
							
						</tr>
						<tr></tr><tr></tr><tr></tr>
						<tr>
						<td>Product Category:</td>			
						<td><form:select path="productCategorySet" name="productCategorySet" style="width: 200px;height:80px" multiple="true" disabled="true">
									<c:forEach items="${updateUserForm.productCategorySet}" var="current">
									<option class="option45" value="${current}" selected>${current}</option>
									</c:forEach>
									</form:select>
							</td>
							</tr>
						<tr></tr><tr></tr><tr></tr>
						<tr>
							<td>Branch:</td>
							<td><form:select path="branch" name="branch" style="width: 200px;" disabled="true">
									<c:forEach items="${branches}" var="current">
									<c:if test="${updateUserForm.branch eq current.branchName}">
											<option class="option45" value="${current.bid}" selected>${current.branchName}</option>
										</c:if>
										<c:if test="${updateUserForm.branch ne current.branchName}">
											<option class="option45" value="${current.bid}">${current.branchName}</option>
										</c:if>										
									</c:forEach>
									</form:select>
							</td>
						</tr>
					</table>
					<table>
					<tr>
					<td><input type="submit" name="Update" value="Delete" onClick="return validateForm()"/></td>
					<td><input type="button" onclick="window.location.href='<%=request.getContextPath()%>/deleteUser'" value="Cancel" /></td>
					</tr>
					</table>
					
			</form:form>
			</div>
					
			</c:if>
	</div>
	</body>
	</html>