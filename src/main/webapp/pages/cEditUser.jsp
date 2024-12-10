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
	<%@ include file="header.jsp"%>
	<div class="header-background">
		<c:if test="${edit eq false}">
			<h2>Create User</h2>
		</c:if>
		<c:if test="${edit eq true}">
			<h2>Update User</h2>
		</c:if>
	</div>
	<br>
	<c:if test="${not empty currentUser.userId}">
		<c:if test="${edit eq false}">
			<div class="leftdiv">
				<form name="createUser"
					action="<%=request.getContextPath()%>/createUser" method="post"
					onsubmit="return validateForm('create')">
					<table style="font-family: Arial; font-size: 12px;">
						<tr class="hiddenRow"></tr>
						<%--<tr>
							<td>Name<span id="asterisk">*</span> ${message}</td>
							<td><input id="name" name="name" value="" type="text"
								maxlength="20"> <input type="hidden" id="unlock"
								name="unlock" value=0></td>
							<td><label id="errorName" style="color: red">Enter
									Name...</label><label id="errornamemaxlength" style="color: red">Name
									Error.Maximum size is 20 Charecters.</label></td>
						</tr>

						<tr>
							<td>Surname<span id="asterisk">*</span></td>
							<td><input id="surname" name="surname" value="" type="text"
								maxlength="20">
								</div></td>
							<td><label id="errorSurname" style="color: red">Enter
									Surname...</label><label id="errorsurnamemaxlength" style="color: red">Surname
									Error.Maximum size is 20 Characters.</label></td>
						</tr>--%>

						<tr>
							<td>User Name<span id="asterisk">*</span></td>
							<td><input id="userName" name="userName" type="text"></td>
						</tr>


						<%-- <tr>
								<td class="property">Password<span id="asterisk">*</span></td>
								<td><input id="password" class="textfield-style"
									name="password" value="" type="password"></td>
								<td><label id="errorPassword" style="color: red">Enter
										Password...</label></td>
								<td><label id="errorPasswordMissmatch" style="color: red">Password
										Mismatch...</label></td>
							</tr>
							<tr>
								<td class="property">Confirm Password<span id="asterisk">*</span></td>
								<td><input id="confirmPassword" class="textfield-style"
									name="confirmPassword" value="" type="password"></td>
								<td><label id="errorCnfrmPassword" style="color: red">Enter
										Confirm Password...</label></td>

							</tr>--%>

						<tr>
							<td>Role<span id="asterisk">*</span></td>
							<td><select name="discrepanyRoleId" style="width: 175px;">
									<option class="option45" value="">Please Select</option>
									<c:forEach items="${roles}" var="current">
										<option class="option45" value="${current.roleId}">${current.roleTitle}</option>
									</c:forEach>

							</select></td>
							<td><label id="errorrole" style="color: red">Select
									role...</label></td>
						</tr>
						<tr>
							<td>Cash Centre<span id="asterisk">*</span></td>
							<td><select name="cashCenterId" style="width: 175px;">
									<option class="option45" value="">Please Select</option>
									<option class="option45" value="All">All</option>
									<c:forEach items="${cashCenterVOList}" var="current">
										<option class="option45" value="${current.cashCenterId}">${current.cashCenterName}</option>
									</c:forEach>

							</select></td>
							<td><label id="errorcashCenter" style="color: red">Select
									Cash Centre...</label></td>
						</tr>




					</table>
			</div>

			<div class="btn" style="text-align: right; dispay: block">
				<input id="save" class="wpsButtonText" type="button" name="save"
					value="Save" onClick="return validateForm('create')" /><input id="cancel" type="button"
					onclick="window.location.href='<%=request.getContextPath()%>/users'"
					value="Cancel" />
			</div>
			</form>
		</c:if>
		<c:if test="${edit eq true}">
			<div class="leftdiv">
				<form name="createUser"
					action="<%=request.getContextPath()%>/update/${userId}"
					method="post" onsubmit="return validateForm('update')">
					<table>
						<tr class="hiddenRow"></tr>
						<tr>
							<td>Name<span id="asterisk">*</span></td>
							<td><input id="name" name="name" value="${user.name}"
								maxlength="20" type="text" readonly> <input type="hidden"
								id="unlock" name="unlock" value=0></td>
							<td><label id="errorName" style="color: red">Enter
									Name...</label><label id="errornamemaxlength" style="color: red">Name
									Error.Maximum size is 20 Charecters.</label></td>
						</tr>

						<tr>
							<td>Surname<span id="asterisk">*</span></td>
							<td><input id="surname" name="surname" maxlength="20"
								value="${user.surname}" type="text" readonly>
								</div></td>
							<td><label id="errorSurname" style="color: red">Enter
									Surname...</label><label id="errorsurnamemaxlength" style="color: red">Surname
									Error.Maximum size is 20 Characters.</label></td>
						</tr>

					    <tr>
							<td>User Name<span id="asterisk">*</span></td>
							<td><input id="userName1" name="userName"
								value="${user.userName}" type="text" readonly>
								
								</td>
						</tr>
						 <tr>
							<td>Email<span id="asterisk"></span></td>
							<td>
								<input id=""emailId"" name="emailId"
								value="${user.emailId}" type="text" readonly>
								</td>
						</tr>

						<%--<tr>
								<td class="property">Password<span id="asterisk">*</span></td>
								<td><input id="password" class="textfield-style"
									name="password" value="${user.password}" type="password"></td>
								<td><label id="errorPasswordMissmatch" style="color: red">Password
										Mismatch...</label></td>
								<td><label id="errorPassword" style="color: red">Enter
										Password...</label></td>
							</tr>
							<tr>
								<td class="property">Confirm Password<span id="asterisk">*</span></td>
								<td><input id="confirmPassword" class="textfield-style"
									name="confirmPassword" value="${user.password}" type="password"></td>
								<td><label id="errorCnfrmPassword" style="color: red">Enter
										Confirm Password...</label></td>
							</tr>--%>

						<tr>
							<td>Role<span id="asterisk">*</span></td>
							<td><select name="discrepanyRoleId" style="width: 175px;">
									<option class="option45" value="">Please Select</option>
									<c:forEach items="${roles}" var="current">
										<c:if test="${user.discrepanyRoleId eq current.roleId}">
											<option class="option45" value="${current.roleId}" selected>${current.roleTitle}</option>
										</c:if>
										<c:if test="${user.discrepanyRoleId ne current.roleId}">
											<option class="option45" value="${current.roleId}">${current.roleTitle}</option>
										</c:if>
									</c:forEach>

							</select></td>
							<td><label id="errorrole" style="color: red">Select
									role...</label></td>
						</tr>
						<tr>
							<td>Cash Centre<span id="asterisk">*</span></td>
							<td><select name="cashCenterId" style="width: 175px;">
									<option class="option45" value="">Please Select</option>
									<option class="option45" value="All" <c:if test="${fn:length(user.cashCenterVOs)>1}"> selected </c:if>>All</option>
									<c:forEach items="${cashCenterVOList}" var="current">
										<option class="option45" value="${current.cashCenterId}"
											<c:if test="${(fn:length(user.cashCenterVOs)==1) && (user.cashCenterVOs[0].cashCenterId eq current.cashCenterId)}"> selected </c:if>>${current.cashCenterName}</option>
									</c:forEach>

							</select></td>
							<td><label id="errorcashCenter" style="color: red">Select
									Cash Cent...</label></td>
						</tr>
						<c:if test="${user.isLocked gt 2}">
							<tr>
								<td>Unlock</td>
								<td><input type="checkbox" id="unlockChk" name="unlockChk"
									onclick="checkUnlock()"></td>
								<td><label id="errorcashCenter" style="color: red"></label></td>
							</tr>
						</c:if>
					</table>
			</div>

			<div class="btn" style="text-align: right; dispay: block">
				<input id="save" class="wpsButtonText" type="button" name="update"
					value="Update" onClick="return validateForm('update')"/><input id="cancel" type="button"
					onclick="window.location.href='<%=request.getContextPath()%>/users'"
					value="Cancel" />
			</div>
			</form>
		</c:if>
	</c:if>
	<c:if test="${empty currentUser.userId}">
		<p>
			<b><center>
					Login To continue <a href="<%=request.getContextPath()%>/">Click
						Here...!</a>
				</center></b>
		</p>
	</c:if>
	</div>
	<!--Footer starts -->
	<%@ include file="footer.jsp"%>
	<!-- Footer Ends -->

</body>
</html>