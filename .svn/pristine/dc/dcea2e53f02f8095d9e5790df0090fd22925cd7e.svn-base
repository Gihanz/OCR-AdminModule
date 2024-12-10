<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page errorPage="ExceptionPage.jsp"%>
<link href="css/header.css" rel="stylesheet" type="text/css">
<link href="css/users.css" rel="stylesheet" type="text/css">
<script src="<c:url value="/js/jquery.min.js"/>" type="text/javascript"></script>
<script src="js/user.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
	var exception="<%=request.getAttribute("exception")%>";
		if (exception == "exception") {
			$("h2").html("Exception page");
			$("div").css("background-color", "#f8f8f8");
		}

	});
</script>
</head>
<body class="page-width">
<c:if test="${edit eq true}">
			<div class="leftdiv">
				<form name="createUser" 
				action="<%=request.getContextPath()%>/update/${user.uid}"
					method="post">
					<table>
					<tr>
							<td>Email<span id="asterisk"></span></td>
							<td>
								<input id=""ntId"" name="ntId"
								value="${user.ntId}" type="text" readonly>
								</td>
						</tr>
					</table>
					<div class="btn" style="text-align: right; dispay: block">
				<input id="save" class="wpsButtonText" type="button" name="update"
					value="Update" onClick="return validateForm('update')"/><input id="cancel" type="button"
					onclick="window.location.href='<%=request.getContextPath()%>/users'"
					value="Cancel" />
			</div>
			</form>				
				</div>
				</c:if>
</body>
</html>
