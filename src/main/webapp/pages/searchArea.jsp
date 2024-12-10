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
	$(document).ready(function() {
	<%-- var exception="<%=request.getAttribute("exception")%>";
		if (exception == "exception") {
			$("h2").html("Exception page");
			$("div").css("background-color", "#f8f8f8");
		} --%>
		
	});
	
	function fill() {
		setTimeout("$('#suggestions').hide();", 200);
		setTimeout("$('#suggestions1').hide();", 200);
	}
	function lookUpPFNum(inputString) {
		if(inputString.length == 0) {
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
	
	function getSelectedOption(sele) {
		var sel = document.getElementById(sele);
	    var flag = false;
	    var selectedVal;
		var opt;
	    for ( var i = 0, len = sel.options.length; i < len; i++ ) {
	        opt = sel.options[i];
	        opt.selected = true;
	        if ( opt.selected === true ) {
	        	if(i==0)
	        		selectedVal=opt.value;
	        	else if(i==(sel.options.length-1))
	        		selectedVal=","+selectedVal+opt.value;
	        	else
	        		selectedVal=","+selectedVal+opt.value;
	        	
	            flag = true;
	        }
	    }
	    if(!flag)
	    	{
	    		alert('Please Select Area');
	    	}
	    else
	    	{
	    	if(confirm("Are you sure want to update user Area?"))
				   flag = true;
			 else
				flag = false;
	    	}
	    return flag; 
	}
	
	function move_list_items(sourceid, destinationid)
	{
	    $("#"+sourceid+"  option:selected").appendTo("#"+destinationid);
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
	
	function validateSubmitForm()
	{
		var flag = true;
		var areaName = document.getElementById('areaName').value;
		if(null!=areaName && areaName=='')
			flag = false;
		
		if(!flag)
		{
			alert('Please Select Area');
		}
		else
		{
		 
		}
	return flag;
	}

	
	
</script>
</head>
<body  class="page-width">
<%@ include file="header.jsp"%>
<div id="apDiv5">
<c:if test="${not empty responseMessage}">
<div style="color: red;" align="center">${responseMessage}</div>

</c:if>
<form:form modelAttribute="searchAreaForm" action="searchUserArea" method="post">
<div>
					<table>
						<tr>
							<td>User :<td>
							<%-- <td>
							<form:select path="ntId" style="width: 175px;">
							<form:option class="option45" value="">Please Select</form:option>
							<c:forEach items="${userIds}" var="current">
												<form:option class="option45" value="${current.ntId}">${current.ntId}</form:option>
							</c:forEach>
							</form:select>
							</td> --%>
							<td><form:input path="ntId" name="ntId" id="ntId" onkeyup="lookUpPFNum(this.value);"  style="width:250px;height:25px;" onblur="fill();"></form:input></td>
  						<div class="suggestionsBox" id="suggestions" style="display: none;">
						<div class="suggestionList" id="autoSuggestionsList">
						</div>
					</div>
						</tr>
					</table>
					<div class="btn" style="text-align: right; dispay: block">
				<input id="save" class="wpsButtonText" type="submit" name="search"
					value="Search" onClick="return validateForm()"/><input id="cancel" type="button"
					onclick="window.location.href='<%=request.getContextPath()%>/searchArea'"
					value="Cancel" />
			</div>
			
			</div>
					</form:form>
	</div>	
	<c:if test="${edit eq true}">				
	<form:form modelAttribute="searchAreaForm" action="submitUserArea" method="post">
	<div id="apDiv51">
			<c:if test="${not empty searchUserAreaResults}">
		 <table id="listUserArea" style="width: 100%" class="display">
		<tr>
			<td>UserId</td>
				<td><input style="width: 175px;" id="name" name="name" value="${userName}"
					maxlength="20" type="text" readonly><input type="hidden"
					id="unlock" name="unlock" value=0></td>
				<!-- <td><label id="errorName" style="color: red">Enter
						Name...</label><label id="errornamemaxlength" style="color: red">Name
						Error.Maximum size is 20 Charecters.</label></td>-->
				<td><form:hidden path="ntId" value="${userName}"/>
				<form:hidden path="existingProductCategorySet" value="${searchUserAreaResults.existingProductCategorySet}"/> 
				</td> 
				<%-- <td><form:hidden path="urpcid" value="${searchUserAreaResults.urpcid}"/> </td> --%>
		</tr>
		<tr>
		<td>Name</td>
		<td><input style="width: 175px;" id="firstName" name="firstName" value="${name}"
					maxlength="20" type="text" readonly></td>
		</tr>		
		<tr>
		<td>Branch</td>
		<td><form:select path="branchName" name="branchName" id="branchName" style="width: 175px;" multiple="true" disabled="true">
				<c:forEach items="${searchUserAreaResults.branchName}" var="current">
				<option class="option45" value="${current}" selected>${current}</option>				
				</c:forEach>
				</form:select>
		</td>
		</tr>
		 <tr>
		<td>Role</td>
		<td><form:select path="roleName" name="roleName" id="roleName" style="width: 175px;" multiple="true" disabled="true">
				<c:forEach items="${searchUserAreaResults.roleName}" var="currentRoleName">
				<form:option class="option45" value="${currentRoleName}" selected="selected">${currentRoleName}</form:option>				
				</c:forEach>
				</form:select>
		</td>
		</tr>
		<tr>
		<td>Product</td>
		<td><form:select path="productCategory" name="productCategory" id="productCategory" style="width: 175px;" multiple="true">
				<c:forEach items="${searchUserAreaResults.productCategory}" var="currentProduct">
				<form:option class="option45" value="${currentProduct}" selected="selected">${currentProduct}</form:option>				
				</c:forEach> 
				</form:select>
		</td>
		<td>
			<input id="moveleft" type="button" value="<<" onclick="move_list_items('masterSetProductCategoryName','productCategory');" /><br><br><input id="moveright" type="button" value=">>"  onclick="move_list_items('productCategory','masterSetProductCategoryName');" /></td>
			
			<td><form:select path="masterProductCategory" name="masterProductCategory" id="masterProductCategory" style="width: 175px;" multiple="true">
			<c:forEach items="${masterProductCategories}" var="current">
				<form:option class="option45" value="${current}">${current}</form:option>
			</c:forEach>
			</form:select>
		</td>
		</tr>
		<tr>		
		<td>Area<span id="asterisk">*</span></td>
		<td><form:select path="areaName" name="areaName" id="areaName" style="width: 175px;" multiple="true">
				<c:forEach items="${searchUserAreaResults.areaName}" var="current">
				<form:option class="option45" value="${current}" selected="selected">${current}</form:option>				
				</c:forEach>
				</form:select>
		</td>
		<td>
		<input id="moveleft" type="button" value="<<" onclick="move_list_items('masterSetAreaName','areaName');" /><br><br><input id="moveright" type="button" value=">>"  onclick="move_list_items('areaName','masterSetAreaName');" /></td>
		
		<td><form:select path="masterSetAreaName" name="masterSetAreaName" id="masterSetAreaName" style="width: 175px;" multiple="true">
		<c:forEach items="${areas}" var="current">
			<form:option class="option45" value="${current}">${current}</form:option>
		</c:forEach>
		</form:select>
		</td>
		</tr> 
		</table> 
		</c:if>
		<div class="btn" style="text-align: right; dispay: block">
				<input id="save" class="wpsButtonText" type="submit" name="Submit"
					value="Submit" onclick="return getSelectedOption('areaName')"/><input id="cancel" type="button"
					onclick="window.location.href='<%=request.getContextPath()%>/searchArea'"
					value="Cancel" />
			</div>
		</div>
	</form:form>
	</c:if>
</body>
</html>