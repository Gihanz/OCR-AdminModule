<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page errorPage="ExceptionPage.jsp"%>
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
<link href="css/SpryMenuBarHorizontal.css" rel="stylesheet"	type="text/css">
</head>
<body  class="page-width">
<%@ include file="header.jsp"%>
<div id="apDiv5">
<form:form modelAttribute="searchAreaForm" action="searchArea" method="post">
<div>
	<c:if test="${not empty searchUserAreaResults}">
		<table id="listUserArea" style="width: 100%" class="display">
		<tr>
			<td>Name<span id="asterisk">*</span></td>
				<td><input style="width: 175px;" id="name" name="name" value="${userName}"
					maxlength="20" type="text" readonly><input type="hidden"
					id="unlock" name="unlock" value=0></td>
				<td><label id="errorName" style="color: red">Enter
						Name...</label><label id="errornamemaxlength" style="color: red">Name
						Error.Maximum size is 20 Charecters.</label></td>
				<td><form:hidden path="name" value="${userName}"/> </td>
		</tr>
		<tr>
		<td>Area</td>
		<td><form:select path="areaName" name="areaName" style="width: 175px;" multiple="true">
				<option class="option45" value="">Please Select</option>
				<c:forEach items="${areas}" var="current">
				<c:if test="${searchUserAreaResults.areaName eq areas.areaName}">
						<option class="option45" value="${current.areaName}" selected>${current.areaName}</option>
					</c:if>
					<c:if test="${searchUserAreaResults.areaName ne current.areaName}">
						<option class="option45" value="${current.areaName}">${current.areaName}</option>
					</c:if>
				</c:forEach>
				</form:select>
		</td>
		</tr>	
		</table>
		</c:if>		
		</div>
		</form:form>
		</div>
		</body>