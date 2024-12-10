<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page errorPage="ExceptionPage.jsp"%>

 <%
//HttpSession session = request.getSession(false);
String role = (String)session.getAttribute("userLoggedIn");
%>
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
var allowedRoles = false;
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
	
	
	 function move_list_items(sourceid, destinationid,roleSelectedSrc)
	{
 		<c:set var="myVal" value='<%=role%>'/> 
 		var role="${myVal}";
 		var allowedFlag=true;
 		if(role=='Manager')
		{
 			//var branchRoles = $("#branchAllowedRoles").val();
 			var branchRoles = document.getElementById('branchAllowedRoles').value;
  			var roleSelected = document.getElementById(sourceid).value;
  			var currentRole = document.getElementById(roleSelectedSrc).value;
 			if(!jQuery.isEmptyObject(roleSelected))
			{
	 			
 				var index = branchRoles.toLowerCase().indexOf((currentRole.toLowerCase()));
				if(index!=-1)
	 			{
	 				 $("#"+sourceid+"  option:selected").appendTo("#"+destinationid);
				}
				else
				{
					allowedFlag=false;
					//alert("You do not have permission to update this role");
				}
				
 				var index = branchRoles.toLowerCase().indexOf((roleSelected.toLowerCase()));
				if(allowedFlag && index!=-1)
	 			{
	 				 $("#"+sourceid+"  option:selected").appendTo("#"+destinationid);
				}
				else
				{
					allowedFlag=false;
					//alert("You do not have permission to update this role");
				}
				if(!allowedFlag)
					{
						alert("You do not have permission to update this role");
					}
			}
 			else
 				{
 					alert('Please Select Role');
 				}
		}
 		else
		{
 			 $("#"+sourceid+"  option:selected").appendTo("#"+destinationid);
		}
	   
	}
	
	function move_Productlist_items(sourceid, destinationid,roleSelectedSrc)
	{
		<c:set var="myVal" value='<%=role%>'/> 
	 		var role="${myVal}";
	 		if(role=='Manager')
			{
	 			//var branchRoles = $("#branchAllowedRoles").val();
	 			var branchRoles = document.getElementById('branchAllowedRoles').value;
	 			var roleSelected = document.getElementById(roleSelectedSrc).value;
	 			if(!jQuery.isEmptyObject(roleSelected))
				{
	 				var index = branchRoles.toLowerCase().indexOf((roleSelected.toLowerCase()));
					if(index!=-1)
		 			{
		 				 $("#"+sourceid+"  option:selected").appendTo("#"+destinationid);
					}
					else
					{
						alert("You do not have permission to update this role");
					}
				}
	 			else
 				{
 					alert('Please Select Role');
 				}
	 			 
			}
	 		else
 			{
	 			 $("#"+sourceid+"  option:selected").appendTo("#"+destinationid);
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
	
	function getSelectedOption(roleSelected,productCatselected,areaSelected,provinceSelected) {
		
		//var roleSelected = document.getElementById(roleSel);
		var roleSel = document.getElementById(roleSelected);
	    var flag = false;
	    var containsAreaFlag = false;
	    var containsProvinceFlag = false;
	    var areaFlag = false;
	    var provinceFlag = true;
	    var branchFlag = true;
	    var rlcManagerFlag = false;
	    var rlcProvinceFlag = true;
	    var flag2 = false;
	    var flag3 = false;
	    var flag4 = false;
	    var provinceCommaFlag = false;
	    var areaCommaFlag = false;
	    var roleSelectedVal;
		var opt;
	    for ( var i = 0, len = roleSel.options.length; i < len; i++ ) {
	        opt = roleSel.options[i];
	        opt.selected = true;
	        if ( opt.selected === true ) {
	        	if(i==0)
	        		roleSelectedVal=opt.value;
	        	else if(i==(roleSel.options.length-1))
	        		roleSelectedVal=roleSelectedVal+","+opt.value;
	        	else
	        		roleSelectedVal=roleSelectedVal+","+opt.value;
	        	
	            flag = true;
	        }
	    }
		
		var productCatSel = document.getElementById(productCatselected);
	    var flag1 = false;
	    var productSelectedVal;
		var opt;
	    for ( var i = 0, len = productCatSel.options.length; i < len; i++ ) {
	        opt = productCatSel.options[i];
	        opt.selected = true;
	        if ( opt.selected === true ) {
	        	if(i==0)
	        		productSelectedVal=opt.value;
	        	else if(i==(productCatSel.options.length-1))
	        		productSelectedVal=","+productSelectedVal+opt.value;
	        	else
	        		productSelectedVal=","+productSelectedVal+opt.value;
	        	
	            flag1 = true;
	        }
	    }
	    
	    if(flag) //Combination of branch roles allowed
    	{
    	 	var str = "CreditAssistance,CreditOfficer,Inquser,ICO,BranchManager,RelationshipOfficer,RelationshipManager,DGM_Corporate_Offshore";
    	 	var branchRoles = document.getElementById('branchAllowedRoles').value;
    	 	
    	 	var branchRolesArray = branchRoles.split(',');
    	 	
    	 	//var branchIndexOf = str.toLowerCase().search((roleSelectedVal.toLowerCase()));
    	 	var roleArray = roleSelectedVal.split(',')
    	 	/* for (var i = 0; i < roleArray.length; i++) {
			    if(roleArray[i]=='CreditAssistance'|| roleArray[i]=='CreditOfficer'||roleArray[i]=='Inquser'||roleArray[i]=='ICO'||roleArray[i]=='BranchManager'|| roleArray[i]=='RelationshipOfficer'||roleArray[i]=='RelationshipManager'|| roleArray[i]=='AGM_Corporate'||roleArray[i]=='Agm_Corporate_Credit' ||roleArray[i]=='DGM_Corporate_Offshore')
			    	{
			    	branchFlag = true;
			    	}
			    else
			    	{
			    	branchFlag = false;
			    	break;
			    	}
			} */ 
			
			 for (var i = 0; i < roleArray.length; i++) {		
				 //alert('role is '+branchRoles.toLowerCase().indexOf(roleArray[i].toLowerCase()));
				var index1 = branchRoles.indexOf(roleArray[i]);				
				if(index1==-1)
					{
					branchFlag = false;
					break;
					}
			 }
			
		 
    			 var areaSel = document.getElementById(areaSelected);
    			 if(areaSel.options.length > 0)
				 {
    				 containsAreaFlag = true; 
				 }
    			 var provinceSel = document.getElementById(provinceSelected);
	    	 		if(provinceSel.options.length>0)
	    	 			{
	    	 			containsProvinceFlag = true;
	    	 			}
	    	 		
    	}
	    
		if(!branchFlag && flag)
	    	{
	    	 	var str = "BranchManager,AreaManager";
	    	 	//var areaManagerIndexOf = roleSelectedVal.toLowerCase().search((str.toLowerCase()));
	    	 	//var areaManagerIndexOf = roleSelectedVal.toLowerCase().indexOf((str.toLowerCase()));
	    		 //var areaManagerIndexOf = roleSelectedVal.search("AreaManager,BranchManager");
	    		// alert(areaManagerIndexOf);
	    		 
	    		 var roleArray = roleSelectedVal.split(',')
	     	 	for (var i = 0; i < roleArray.length; i++) {
	 			    if(roleArray[i]=='AreaManager' || roleArray[i]=='BranchManager')
	 			    	{
	 			    	areaFlag = true;
	 			    	provinceFlag=false;
	 			    	}
	 			    else
	 			    	{
	 			    	branchFlag = false;
	 			    	areaFlag = false;
	 			    	break;
	 			    	}
	 			}
	   					var areaSel = document.getElementById(areaSelected);
		   			    var areaSelectedVal1;
		   				var opt;
		   			    for ( var i = 0, len = areaSel.options.length; i < len; i++ ) {
		   			    	containsAreaFlag = true; 
		   			        opt = areaSel.options[i];
		   			        opt.selected = true;
		   			        if ( opt.selected === true ) {
		   			        	if(i==0)
		   			        		areaSelectedVal1=opt.value;
		   			        	else if(i==(areaSel.options.length-1))
		   			        		areaSelectedVal1=","+areaSelectedVal1+opt.value;
		   			        	else
		   			        		areaSelectedVal1=","+areaSelectedVal1+opt.value;
		   			        	
		   			            flag2 = true;
		   			        }
	   			   		 } 
		   			 var provinceSel = document.getElementById(provinceSelected);
		    	 		if(provinceSel.options.length>0)
		    	 			{
		    	 			containsProvinceFlag = true;
		    	 			}
		    	 		
    			 
	    	} 
	     if(!branchFlag || !areaFlag)
	    	 {
	    	 //var str = "CreditAssistance,CreditOfficer,ICO,Inquser,BranchManager,AreaManager";
	    	 	//var provinceManagerIndexOf = str.toLowerCase().search((roleSelectedVal.toLowerCase()));
	    	 	//if(provinceManagerIndexOf!=-1)
	   			 var roleArray = roleSelectedVal.split(',')
	    	 	/* for (var i = 0; i < roleArray.length; i++) {
				    if(roleArray[i]=='CreditAssistance'|| roleArray[i]=='CreditOfficer'||roleArray[i]=='Inquser'||roleArray[i]=='ICO'||roleArray[i]=='BranchManager' || roleArray[i]=='AreaManager' || roleArray[i]=='RelationshipOfficer' || roleArray[i]=='RelationshipManager' || roleArray[i]=='AGM_Corporate'||roleArray[i]=='Agm_Corporate_Credit' || roleArray[i]=='DGM_Corporate_Offshore')
				    	{
				    	provinceFlag = false;
				    	break; //Only Province is allowed
				    	}
				     else
				    	{
				    	provinceFlag = true;
				    	} 
				} */
				var branchRoles = document.getElementById('branchAllowedRoles').value;
	    	 	var branchRolesArray = branchRoles.split(',');
	   			 for (var i = 0; i < roleArray.length; i++) {			 
	 				var index = branchRoles.indexOf(roleArray[i]);
	 				if(roleArray[i]=='RLC_Manager')
 					{
	 					rlcManagerFlag = true;
 					}
	 				else
 					{
	 					rlcProvinceFlag=false;
 					}
	 				if(index!=-1)
	 					{
	 					//branchFlag = true;
	 					provinceFlag = false;
	 					break;
	 					}
	 				
	 			 }
	    	 		var containsComma = roleSelectedVal.search(",");
					     var provinceSel = document.getElementById(provinceSelected);
						    
						    var provinceSelectedVal;
							var opt;
						    for ( var i = 0, len = provinceSel.options.length; i < len; i++ ) {
						    	containsProvinceFlag = true; 
						        opt = provinceSel.options[i];
						        opt.selected = true;
						        if ( opt.selected === true ) {
						        	provinceSelectedVal = opt.value;
						        	if(rlcManagerFlag)
						        	{
						        		if (provinceSelectedVal.indexOf("RLC") == -1) {
						        			  // Code if string starts with this substring
						        			rlcProvinceFlag=false;
						        			  break;
						        			}
						        	}
						        	else
						        		{
						        		if (provinceSelectedVal.indexOf("RLC") == 0) {
						        			  // Code if string starts with this substring
						        			rlcProvinceFlag=true;
						        			  break;
						        			}
						        		else
						        			{
						        			rlcProvinceFlag=false;
						        			}
						        		}						        		
						        	if(i==0)
						        		provinceSelectedVal=opt.value;
						        	else if(i==(provinceSel.options.length-1))
						        		provinceSelectedVal=","+provinceSelectedVal+opt.value;
						        	else
						        		provinceSelectedVal=","+provinceSelectedVal+opt.value;
						        	
						            flag3 = true;
						        		
					        }
					    }
	    			 if(areaSel.options.length > 0)
					 {
	    				 containsAreaFlag = true; 
					 }
	    	 }
	     
	    	//alert(branchFlag);
 	 		//alert(areaFlag)
	     var name = document.getElementById('name').value;
	     //var branch = $('#branch').text();
	     
	     var branch = document.getElementById('branch').options[document.getElementById("branch").selectedIndex ].text;
	     var confirmData = " '"+name+"' of branch '"+branch+"' with roles '"+roleSelectedVal+"'"
	    if(!flag)
	    	{
	    		alert('Please Select  Role');
	    	}
	    else if(!flag1)
	    	{
	    		alert('Please Select ProductCategory');
	    		flag = flag1;
	    	}
		    /* else if(areaCommaFlag)
	    	{
	    		alert('Cannot assign more than 1 role for Area Manager ');
	    		flag = false;
	    	}
	    else if(provinceCommaFlag)
	    	{
		    	alert('Cannot assign more than 1 role for Province Manager ');
	    		flag = false;
	    	} */
	    	
	   /* else if(!rlcManagerFlag && !rlcProvinceFlag)
	    		{
				   alert('Cannot Select RLC province');	
					flag = false;
	    		} */
    	else if(!rlcProvinceFlag && rlcManagerFlag)
    		{
    			alert('Please Select RLC province');	
    			flag = false;
    		}
    	else if(!rlcManagerFlag && rlcProvinceFlag)
   		{
			alert('Cannot Select RLC province');	
			flag = false;
   		}
     	else if(branchFlag && areaFlag)
   		{
   			alert('Please Select Correct role, AreaManager is not allowed');
   			flag = false;
   		}
     	else if(branchFlag && provinceFlag)
   		{
   			alert('Please Select Correct role, Branch & Province are not allowed');
   			flag = false;
   		}     	
     	else if(!branchFlag && !areaFlag && !provinceFlag)
   		{
   			alert('Please Select Correct role, Branch or AreaManager or Province are not allowed');
   			flag = false;
   		}
    	 else if(!branchFlag && areaFlag && !containsAreaFlag)
	    	{
		    	alert('Please Select Area');
	    		flag = false;
	    	}
	    else if(provinceFlag &&  !containsProvinceFlag)
 		{
	    	alert('Please Select Province');
 		flag = false;
 		}    	
	    else
	    	{
    		if(areaFlag)
   			{
    			if(containsProvinceFlag)
	    		{
		    		alert("Cannot assign a Province");
		    		flag = false;
	    		}
    		}
    		else if(provinceFlag)
   			{
   				if(containsAreaFlag)
					{
	   					alert("Cannot assign an Area");
			    		flag = false;
					}
   			}
    		else if(branchFlag)
   			{
    			if(containsAreaFlag)
				{
   					alert("Cannot assign an Area");
		    		flag = false;
				}
    			if(containsProvinceFlag)
	    		{
		    		alert("Cannot assign a Province");
		    		flag = false;
	    		}
   			}
    		if(flag)
    			{
	    			if(confirm("Are you sure want to update user"+confirmData))
	 				   flag = true;
	 			 else
	 				flag = false;
	 	    	}
 	    
    		}
	    	
	
	    return flag; 
	}
	
	function checkRole(role)
	{
		var isBranchRole = true;
		var branchRoles = document.getElementById('branchAllowedRoles').value;
	 	var branchRolesArray = branchRoles.split(',');
	 	for(var j=0;j<branchRolesArray.length;j++)
		{
	 		if(branchRolesArray[j]==role)
 			{
 			
 			}
		}
		
		return 
	}
	
	function disableForm()
	{
		/* var role = document.getElementById('roleSet').options[document.getElementById("roleSet").selectedIndex ].text;
		var str = "CreditAssistance,CreditOfficer,BranchManager";
	 	var roleIndexOf = str.toLowerCase().search((role.toLowerCase()));
	 	if(roleIndexOf!=-1)
 		{
	 		document.getElementById('AreaTr').style.visibility = "hidden";
	 		document.getElementById('provinceTr').style.visibility = "hidden";
 		} */
 		
		
		
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
	return false;
	}

	
	
</script>
</head>
<body background="<c:url value="/images/CaseManagerBackground.jpg"/>">
<%@ include file="header.jsp"%>
<div id="apDiv5">
<c:if test="${not empty responseMessage}">
<div style="color: red;" align="center">${responseMessage}</div>

</c:if>
<form:form modelAttribute="searchForm" action="searchUser" method="post">
<table align="Center">
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
					</form:form>
</div>
		<c:if test="${not empty updateUserForm}">
		<div id="apDiv51" style="padding-right: 15px; ">
			<form:form modelAttribute="updateUserForm" action="update"	method="post">
					<table>
						<tr>
						<td>User ID:</td>
							<td><input  path="name" id="name" name="name" value="${userName}" type="text" readonly style="width: 160px;" /></td>							
						 <td><form:hidden path="ntId" value="${userName}"/></td>
						 <form:hidden path="existingBid" value="${updateUserForm.existingBid}"/> 
						 <form:hidden path="existingRoleSet" value="${updateUserForm.existingRoleSet}"/> 
						 <form:hidden path="existingProductCategorySet" value="${updateUserForm.existingProductCategorySet}"/> 
						 <form:hidden path="existingAreaNameSet" value="${updateUserForm.existingAreaNameSet}"/> 
						 <form:hidden path="existingProvinceNameSet" value="${updateUserForm.existingProvinceNameSet}"/> 
						 
						<input type="hidden" id="branchAllowedRoles" name="branchAllowedRoles" value="${branchAllowedRoles}"/>						
						</tr>
						<tr><tr></tr><tr></tr><tr></tr>
						<tr>
						<td>Initials:</td>				
						<td>
						<form:input path="firstName" name="firstName" id="firstName" style="width: 160px;" value="${firstName}" disabled="true"></form:input>
						</td>
						</tr>
						<tr><tr></tr><tr></tr><tr></tr>
						<td>Last Name:</td>				
						<td>
						<form:input path="middleName" name="middleName" id="middleName"  style="width: 160px;" value="${middleName}" disabled="true"/>
						</td>
						</tr>
						<tr><tr></tr><tr></tr><tr></tr>
						<br>
						<tr>
						<td>Role:<span id="asterisk">*</span></td>			
						<td><form:select path="roleSet" name="roleSet" style="width: 200px;height:80px" multiple="true">
						<c:forEach items="${updateUserForm.roleSet}" var="current">
									<option class="option45" value="${current}" selected>${current}</option>
									</c:forEach> 
									</form:select>
							</td>
							<td>
							<input id="moveleft" type="button" value="<<" onclick="move_list_items('masterRoleSet','roleSet','roleSet');" /><input id="moveright" type="button" value=">>"  onclick="move_list_items('roleSet','masterRoleSet','roleSet');" /></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<form:select path="masterRoleSet" name="masterRoleSet" id="masterRoleSet" style="width: 200px;height:80px" multiple="true">
							<c:forEach items="${roles}" var="current">
								<form:option class="option45" value="${current}">${current}</form:option>
							</c:forEach>
							</form:select>
							</td>
						</tr>
						<tr></tr><tr></tr><tr></tr>
						<tr>
						<td>Product Category:<span id="asterisk">*</span></td>			
						<td><form:select path="productCategorySet" name="productCategorySet" style="width: 200px;height:80px" multiple="true">
									<c:forEach items="${updateUserForm.productCategorySet}" var="current">
									<option class="option45" value="${current}" selected>${current}</option>
									</c:forEach>
									</form:select>
							</td>
							<td>
							<input id="moveleft" type="button" value="<<" onclick="move_Productlist_items('masterSetProductCategoryName','productCategorySet','roleSet');" /><input id="moveright" type="button" value=">>"  onclick="move_Productlist_items('productCategorySet','masterSetProductCategoryName','roleSet');" /></td>
							
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<form:select path="masterSetProductCategoryName" name="masterSetProductCategoryName" id="masterSetProductCategoryName" style="width: 200px;height:80px" multiple="true">
							<c:forEach items="${productCategories}" var="current">
								<form:option class="option45" value="${current}">${current}</form:option>
							</c:forEach>
							</form:select>
							</td>
						</tr><tr></tr><tr></tr>
						<tr>
							<td>Branch:<span id="asterisk">*</span></td>
							<td><form:select path="branch" name="branch" style="width: 200px;">
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
						</tr><tr></tr><tr></tr>
						<tr id="AreaTr">		
							<td>Area:<span id="asterisk">*</span></td>
							<td><form:select path="areaName" name="areaName" id="areaName" style="width: 200px;height:80px" multiple="true">
									<c:forEach items="${updateUserForm.areaName}" var="current">
									<form:option class="option45" value="${current}" selected="selected">${current}</form:option>				
									</c:forEach>
									</form:select>
							</td>
							<td>
							<input id="moveleft" type="button" value="<<" onclick="move_list_items('masterSetAreaName','areaName','roleSet');" /><input id="moveright" type="button" value=">>"  onclick="move_list_items('areaName','masterSetAreaName','roleSet');" /></td>
							
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<form:select path="masterSetAreaName" name="masterSetAreaName" id="masterSetAreaName" style="width: 200;height:80px" multiple="true">
							<c:forEach items="${areas}" var="current">
								<form:option class="option45" value="${current}">${current}</form:option>
							</c:forEach>
							</form:select>
							</td>
						</tr> <tr></tr><tr></tr>
						<tr id="provinceTr">
							<td>Province:<span id="asterisk">*</span></td>
							<td><form:select path="provinceName" name="provinceName" id="provinceName" style="width: 200px;height:80px" multiple="true">
									<c:forEach items="${updateUserForm.provinceName}" var="current">
									<form:option class="option45" value="${current}" selected="selected">${current}</form:option>
									</c:forEach>
									</form:select>
							</td>
							<td>
							<input id="moveleft" type="button" value="<<" onclick="move_list_items('masterSetProvinceName','provinceName','roleSet');" /><input id="moveright" type="button" value=">>"  onclick="move_list_items('provinceName','masterSetProvinceName','roleSet');" /></td>
							
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<form:select path="masterSetProvinceName" name="masterSetProvinceName" id="masterSetProvinceName" style="width: 200px;height:80px" multiple="true">
							<c:forEach items="${province}" var="current">
								<form:option class="option45" value="${current}">${current}</form:option>
							</c:forEach>
							</form:select>
							</td>
						</tr><tr></tr><tr></tr>
						</table>
						<br><br><br>
				<table align="center" style="padding-right: 230x;" width="40%">
				<tr>
				<td>
				<input type="submit"  name="Update"	value="Update" onclick="return getSelectedOption('roleSet','productCategorySet','areaName','provinceName')"/></td>
				<td><input type="button" onclick="window.location.href='<%=request.getContextPath()%>/search?responseMessage='"	value="Cancel" /></td></tr>
			</table>
			</form:form>
			</div>
					
			</c:if>