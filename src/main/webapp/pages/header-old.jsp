<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*,com.sbv.discrepancy.utils.*"%>

<c:set var="SUCCESSMSG" value="<%=session.getAttribute(Utils.SUCCESSMSGKEY) %>" scope="page" />
<c:set var="ERRORMSG" value="<%=session.getAttribute(Utils.ERRORMSGKEY) %>" scope="page" />
<c:set var="MSG" value="<%=session.getAttribute(Utils.MSG) %>" scope="page" />
<%
if(session.getAttribute(Utils.SUCCESSMSGKEY)!=null)
session.removeAttribute(Utils.SUCCESSMSGKEY);

if(session.getAttribute(Utils.ERRORMSGKEY)!=null)
session.removeAttribute(Utils.ERRORMSGKEY);


if(session.getAttribute(Utils.MSG)!=null)
session.removeAttribute(Utils.MSG);
%>

<c:set var="COBSOURCEID" value="<%=Utils.COB_SOURCE_ID%>" scope="page" />
<c:set var="BOCSOURCEID" value="<%=Utils.BOC_SOURCE_ID%>" scope="page" />
<c:set var="SARBSOURCEID" value="<%=Utils.SARB_SOURCE_ID%>" scope="page" />
<c:set var="APPROVED" value="<%=Utils.APPROVED%>" scope="page" />
<c:set var="DECLINED" value="<%=Utils.DECLINED%>" scope="page" />
<c:set var="PENDING_FOR_APPROVAL"
	value="<%=Utils.PENDING_FOR_APPROVAL%>" scope="page" />
<c:set var="PENDING_FOR_APPROVAL_LEVEL1"
	value="<%=Utils.PENDING_FOR_APPROVAL_LEVEL1%>" scope="page" />
<c:set var="PENDING_FOR_APPROVAL_LEVEL2"
	value="<%=Utils.PENDING_FOR_APPROVAL_LEVEL2%>" scope="page" />
<c:set var="EXPIRED" value="<%=Utils.EXPIRED%>" scope="page" />

<c:set var="WHOLESALE" value="<%=Utils.Wholesale%>" scope="page" />
<c:set var="SARB" value="<%=Utils.SARB%>" scope="page" />
<c:set var="CLAIM" value="<%=Utils.Claim%>" scope="page" />
<c:set var="DISCREPANCY" value="<%=Utils.Discrepancy%>" scope="page" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>Discrepancy Management</title>
<SCRIPT>
var CONTEXT_ROOT="<%=request.getContextPath()%>";
</SCRIPT>
<link href="<c:url value="/css/header.css"/>" rel="stylesheet"
	type="text/css">
<script src="<c:url value="/js/jquery-1.8.3.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/datetimepicker_css.js" />"></script>


<link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet"
	type="text/css">
<link href="<c:url value="/css/DivPopUp.css" />" rel="stylesheet"
	type="text/css">
<script src="<c:url value="/js/jquery-ui1.js" />"></script>

<%@ page isELIgnored="false"%>

<script type="text/javascript">
var errorTitle="ERROR";
var validationTitle="ERROR";
function setMessageDialog(title,msgText){
    $('#MessageDialog').dialog('option', 'title', title);
	document.getElementById("messageDialogTxt").innerHTML =msgText;
    openMessageDiaglog();
}


	//jquery
	$(document).ready(function() {
		console.log("in 1");
		document.getElementById("message").innerHTML = "Paragraph changed!";

		$(function() {
			$("#dialog").dialog({

				autoOpen : false,
				modal : true,
				show : {
					duration : 1000
				},
				hide : {

					duration : 1000
				}
			});
			
			$("#Validation").dialog({

				autoOpen : false,
				modal : true,
				show : {
					duration : 1000
				},
				hide : {

					duration : 1000
				}
			});
			
				$("#MessageDialog").dialog({

				autoOpen : false,
				modal : true,
				show : {
					duration : 500
				},
				hide : {

					duration : 500
				}
			});
		});

		$("#delete").click(function() { //#cancel is the id of the button
			$("#dialog").dialog("open");
			var state = true;
			if (state) {
				$("#dialog").animate({
					backgroundColor : "grey",
					color : "rgba(0, 0, 0, 0.48)",
					width : 500
				}, 1000);
			}
		});

		openConfirmBox = function() {
			console.log("inside cnfrm function");
			$("#dialog").dialog("open");
			var state = true;
			if (state) {
				$("#dialog").animate({
					backgroundColor : "grey",
					color : "rgba(0, 0, 0, 0.48)",
					width : 500
				}, 1000);
			}
		};

		$("#canelNoButton").click(function() {
			$("#dialog").dialog("close");
		});
		
		
		openMessageDiaglog = function() { // #cancel is the id of the button
		
		$("#MessageDialog").dialog("open");
		var state = true;
		if (state) {
			$("#MessageDialog").animate({
				backgroundColor : "grey",
				color : "rgba(0, 0, 0, 0.48)",
				
				width : 500
			}, 1000);
		}
	};
	$("#MessageDialogOkButton").click(function() {
		$('.ui-dialog').css("zIndex","100");
		$("#MessageDialog").dialog("close");
		$('#jg_popup_inner').css("zIndex","10000000000000000");
		if($("#REFRESH_FG").val()==1){
		   location.reload();
		   $.blockUI({ 
            message: $('#loadingmessage'), 
            css: { 
                top:  ($(window).height() - 100) /2 + 'px', 
                left: ($(window).width() - 100) /2 + 'px', 
                width: '100px' ,
				zIndex:2147483656
            } 
        }); 
		   }
	});
		
		<c:if test="${ not empty MSG && fn:length(MSG) gt 0 }">
		
		   document.getElementById("messageDialogTxt").innerHTML = "${MSG}";
		   $("#MessageDialog").dialog();
			openMessageDiaglog();
		 </c:if>
		
		
	});
</script>

</head>
<body class="page-width">
	<div id="container" class="page-content">

		<div class="site-banner-web zaHeader">
			<img border="0" style="margin-left: 10px;"
				src="<%=request.getContextPath()%>/images/logo_sbv.png" /> <img
				border="0" style="margin-left: 90px;"
				src="<%=request.getContextPath()%>/images/SBVhome.jpg" /> </a>
			<table align="right"
				style="height: 110px; background: transparent; padding-right: 10px; word-wrap: break-word; width: 250px; table-layout: fixed;">
				<tr>
					<td
						style="font-size: 15px; vertical-align: bottom; padding-bottom: 30px;">
						Welcome<br>${currentUser.name}
					</td>

				</tr>
			</table>
		</div>

		<div id="menuwrapper"
			style="background: linear-gradient(to bottom, #32323A 0%, #141414 100%);">
			<c:if test="${not empty currentUser.userId}">
				<ul>
					<li id="first-menu-item"><a
						href="#">Discrepancy</a>
						      <ul>
								
									<li><a href="<%=request.getContextPath()%>/option">WholeSale</a></li>						
									<li><a href="<%=request.getContextPath()%>/sarb">SARB</a></li>
								   <li><a href="<%=request.getContextPath()%>/retail">Retail</a></li>
								
							</ul>
						
						
						</li>
					<%-- <c:if
						test="${currentUser.createRole eq true || currentUser.createUser eq true }"> --%>
					<c:if
						test="${currentUser.discrepancyRoleVO.createUser==1 || currentUser.discrepancyRoleVO.createRole==1 }">
						<li><a href="#"> Admin</a>
							<ul>
								<%-- <c:if test="${currentUser.createUser eq true }"> --%>
								<c:if test="${currentUser.discrepancyRoleVO.createUser==1 }">
									<li><a href="<%=request.getContextPath()%>/users">User</a></li>
								</c:if>
								<%-- <c:if test="${currentUser.createRole eq true }"> --%>
								<c:if test="${currentUser.discrepancyRoleVO.createRole==1 }">
									<li><a href="<%=request.getContextPath()%>/roles">Role</a></li>
								</c:if>
							</ul></li>
					</c:if>
					<li><a href="<%=request.getContextPath()%>/report">
							Reporting</a></li>
					<c:if test="${currentUser.discrepancyRoleVO.monitoring==1 }">
						<li><a href="<%=request.getContextPath()%>/monitoring">
								Support Desk</a></li>
					</c:if>
					<li><a href="<%=request.getContextPath()%>/logout"> Log
							Off</a></li>
				</ul>
			</c:if>
		</div>

		<div id="dialog" title="Confirm" style="display:none">
			<img src="<c:url value="/images/question.png" />" height="150px;"
				width="130px">

			<div id="ConfirmBoxCancel">
				<div id="message"></div>

				<hr>
				<input type="button" id="canelYesButton" value="Yes"
					onClick="return returnConfirmation()" /> <input type="button"
					id="canelNoButton" value="No">


			</div>
		</div>

		<div id="Validation" title="Validaion" style="display:none">
			<div id="validationCancel">
				<div id="redColor">Error:</div>
				<div id="message3"></div>
				<hr>
				<input type="submit" id="valiationOkButton" value="OK">
			</div>
		</div>
		
		<div id="MessageDialog" title="Message" style="display:none">
			<div id="MessageDialogPopup" style="height:70px;">
				<div style="color:#000000;height:100%;" id="messageDialogTxt" ></div>
			</div>
			<div style="height:40px;text-align:right;margin-right:30px;" class="blackColor">
				<input type="submit" id="MessageDialogOkButton" value="OK" style="width:80px" class="button-main">
			</div>
		</div>
		
		<c:if test="${ not empty SUCCESSMSG}">
		<div id="sucessMsg" >
		
		<p style="padding-top:5px;text-align: center;color: green;font-weight: bold;">${SUCCESSMSG}</p>
		</div>
        </c:if>
		<c:if test="${ not empty ERRORMSG}">
		<div id="errorMsg" >
		<p style="padding-top:5px;text-align: center;color:red;font-weight: bold;">${ERRORMSG}</p>
		</div>
        </c:if>
		<input type="hidden" id="REFRESH_FG" name="REFRESH_FG" value="0">
</body>
</html>