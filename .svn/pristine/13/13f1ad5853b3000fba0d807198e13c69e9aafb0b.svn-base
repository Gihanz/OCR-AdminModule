<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page isELIgnored="false" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"

   "http://www.w3.org/TR/html4/loose.dtd">



<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>JSP Page</title>

        <link href="css/other.css" rel="stylesheet" type="text/css">

        <link href="css/login.css" rel="stylesheet" type="text/css">

        <script type="text/javascript">

        function validateForm()

        {

        	var flag = false;

    		var userName = document.getElementById('userName').value;

    		if(null!=userName && userName!='')

    			flag = true;

    		else

    			alert('Please enter UserName');

    		if(flag)

    			{

		    		var password = document.getElementById('password').value;

		    		if(null!=password && password!='')

		    			flag = true;

		    		else

	    			{

	    			alert('Please enter Password');

	    			flag = false;

	    			}

    			}

    		/* document.getElementById('loadingImg').style.visibility="visible"; */

    		

    	return flag;

        }

        </script>

    </head>

    <!-- <body  background="../images/CaseManagerBackground.jpg"> -->

     <body  background="<c:url value="/images/CaseManagerBackground.jpg"/>">

   <div class="header"><img src="<c:url value="/images/CaseManagerBanner.png"/>"></div>

    <c:if test="${not empty responseMessage}">

<div style="color: red;" align="center">${responseMessage}</div>



</c:if>

<!-- <div id="apDiv5"> -->

<div class="login">

 <form:form modelAttribute="logonForm" action="submitLogon" method="post">

<h3 align="left">&nbsp;&nbsp;&nbsp;&nbsp;Welcome to Admin Module</h3>

        <table align="left">

        <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;

        User name:<form:input path="userName" name="userName" id="userName" value="" /></td></tr>

        <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;

        Password:&nbsp;&nbsp;<form:password path="password" name="password" id="password" value=""/></td></tr>

		<tr><td><input type="submit" name="Submit"value="Login" onClick="return validateForm()"/></td><%-- <td><input id="cancel" type="reset"	value="Reset" /><img id="loadingImg" style="margin-left:10px; display: none;" alt=""  src="<c:url value="/images/loadingImg.gif"/>" height="100px"> --%>

					<!-- <div class="btn" style="text-align: right; dispay: block"></div></td> --></tr>

			<tr>

			<td class="image" style="padding-top:10px;" align="right" >

 				 <img src="<c:url value="/images/BOC.png"/>" height="100px">

			</td>

			</tr>

			

		

		</table>

		</form:form>

		</div>

    </body>

</html>