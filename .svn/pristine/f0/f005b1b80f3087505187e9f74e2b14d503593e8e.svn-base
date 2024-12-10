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
<link href="css/login.css" rel="stylesheet" type="text/css">
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

<style>
/*  table,th,td {
	border: 1px solid black;
	border-collapse: collapse;
}

th,td {
	padding: 5px;
}  */
</style>
<script>
	<%-- $(document).ready(function() {
	var exception="<%=request.getAttribute("exception")%>";
		if (exception == "exception") {
			$("h2").html("Exception page");
			$("div").css("background-color", "#f8f8f8");
		}
		$('#listUsers').dataTable();
	}); --%>
	
		
</script>
<script type="text/javascript">
function fill() {
	setTimeout("$('#suggestions').hide();", 200);
	setTimeout("$('#suggestions1').hide();", 200);
}
function lookUpPFNum(inputString) {
	if(inputString.length <5) {
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
	//var userName = $('#ntId').val;
	var flag = false;
	var userName = document.getElementById('ntId').value;
	console.log(userName);
	if(null!=userName && userName!='')
		flag = true;
	else
		alert('Please enter UserName');
return flag;
}

</script>


</head>
<body  background="<c:url value="/images/CaseManagerBackground.jpg"/>">
<%@ include file="header.jsp"%>
<div id="apDiv5">
 <c:if test="${not empty responseMessage}">
<div style="color: red;" align="center">${responseMessage}</div>

</c:if>
<form:form modelAttribute="searchForm" action="searchUser" method="post">
	<table align="center">
			<tr>
				<td><h3>User:</h3><td>
				<td><form:input path="ntId" name="ntId" id="ntId" onkeyup="lookUpPFNum(this.value);"  style="width:250px;height:25px;" onblur="fill();"></form:input></td>
								<td><input type="submit" name="search" value="Search" onClick="return validateForm()"/></td>
				<td><input type="button" onclick="window.location.href='<%=request.getContextPath()%>/search?responseMessage='"	value="Cancel" /></td>
  						<div class="suggestionsBox" id="suggestions" style="display: none;">
					<div class="suggestionList" id="autoSuggestionsList">
					</div>
				</div>
				</tr>
	</table>			
	<div>

		<c:if test="${not empty searchResults}">
		<table id="listUsers" style="width: 100%" class="display">
		<thead>
				<tr>
					<th style="text-align:left;">UserName</th>
					<th style="text-align:left;">FirstName</th>
					<th style="text-align:left;">LastName</th>
					<th style="text-align:left;">BranchName</th>
					<th style="text-align:left;">ProductCategory</th>
					<th style="text-align:left;">Role</th>
					<th>Edit</th>
					<th>Add</th>
					<th>Delete</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${searchResults}" var="current">
					<tr>
					<td>${current.userName}</td>
					<td>${current.firstName}</td>
					<td>${current.middleName}</td>
					<td>${current.branchName}</td>
					<td>${current.productCategory}</td>
					<td>${current.roleName}</td>
					<td style="text-align: center">
					 <a href="<%=request.getContextPath()%>/user?edit=true&urpcid=${current.urpcid}&userName=${current.userName}&roleName=${current.roleName}"><img src="images/2.png" alt="Edit" title="Edit User"></a>
							</td>
					<td style="text-align: center">
					<a href="<%=request.getContextPath()%>/user?edit=false&urpcid=${current.userName}&userName=${current.userName}&roleName=${current.roleName}"><img src="images/4.jpg" alt="Add" title="Add User"></a> 
							</td>							
					<td style="text-align: center">
					<a href="<%=request.getContextPath()%>/delete?urpcid=${current.urpcid}&userName=${current.userName}&roleName=${current.roleName}"><img src="images/4.jpg" alt="Add" title="Add User"></a> 
							</td>
					</tr>
					</c:forEach>
			</tbody>			
		</table>
		
		</c:if>	
		
		</div>	
		</form:form>	
</div>
</body>
</html>