<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link href="<c:url value="/css/header.css"/>" rel="stylesheet"	type="text/css">
<script src="<c:url value="/js/jquery-1.8.3.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/js/datetimepicker_css.js" />"></script>


<link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet"
	type="text/css">
<link href="<c:url value="/css/DivPopUp.css" />" rel="stylesheet"
	type="text/css">
<script src="<c:url value="/js/jquery-ui1.js" />"></script>

<%@ page isELIgnored="false"%>

 
<%
//HttpSession session = request.getSession(false);
String userName = (String)session.getAttribute("userName");
String userLoggedIn = (String)session.getAttribute("userLoggedIn");
%>
<style>
.selected {
    background-color: red ;
}

a.current {
  background:red ;
  color:white;
}

a {text-decoration:none;}
.active {background:rgb(247, 192, 13); padding:1px 6px;}

/* a:hover {  background-color: #3b3b3b; } */

#nav{
width:100%;
text-align:center;
min-width:1300px;
height:80px;
position:absolute;
top:0;
left:0;
background:#fff;
list-style:none;
border-bottom: 1px solid #000;
}

#nav li{
display:inline;
}

#nav .nav{
display:inline-block;
background-color:#000;
color:#FFF;
font-family: 'Oswald', sans-serif;
letter-spacing:1px;
font-size:16pt;
line-height:18pt;
font-weight:400;
text-decoration:none;
margin-right: 3px;
margin-left: 3px;
margin-top:35px;
padding:0px 3px 2px 3px;
}

#nav .nav:hover{
background:#FFFF00;
color:#000;
}

#nav .active { 
    /* css here */
    background:#FFFF00;
color:#000;
}
 


</style>
<script type="text/javascript">
/* $(document).ready(function(){
    $('#subnav a').each(function(index) {
        if(this.href.trim() == window.location)
            $(this).addClass("selected");
    });
    
   
});
 */
 
 //Get the current page link
 
 

 //Search your menu for a linkURL that is similar to the active pageURL
  $(window).load(function(){
      var url = window.location.href;
      var ctx = "${pageContext.request.contextPath}";
       var provinceUserURL = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'searchUserProvince';
       var submitUserProvince = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'submitUserProvince';
       var searchProvince = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'searchProvince';
       
       var areaUserURL = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'searchUserArea';
       var submitUserArea = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'submitUserArea';
       var searchArea = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'searchArea';
       

       var searchUserURL = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'searchUser';
       /* var searchUser = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+"search?responseMessage=''"; */
       var searchUser = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+"search";
       
       var editUser = url.search("edit=true");
	   var searchUserResponse = url.indexOf("search?responseMessage=");	
       
	   
	   var getUserADURL = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'getUserAD';
	   var createUserURL = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'createUser';
	   var searchADUser = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+"searchAD?responseMessage=''";
	   var searchADUserResponse = url.indexOf("searchAD?responseMessage=");	
	   
	   
	   var searchDeleteUser = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'searchDeleteUser';
	   var deleteUserResponse = url.indexOf("searchDeleteUser?responseMessage=");	
	   var deleteUser = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'deleteUser';
	   var deleteUserSubmitURL = window.location.protocol+"//"+window.location.hostname+":"+window.location.port+ctx+"/"+'submitDeleteUser';
	   
	   if(url==provinceUserURL)
   	  {
   	  	url = searchProvince;
   	  } 
	   else if(url==submitUserProvince)
   	   {
   	   url = searchProvince;
   	   }
	   else if(url==searchUserURL)
	   {
		 url = searchUser;
	   }
	   else if(url==searchUser)
	   {
   	   	//url = searchUser;
   	 	url = searchUser;
   	   }
       else if(url==areaUserURL)
   	   {
   	   	url = searchArea;
   	   }
       else if(url==submitUserArea)
   	   {
   	   url = searchArea;
   	   }       
       else if(editUser!=-1)
   	   {
    	   url = searchUser;
   	   }       
       else if(searchUserResponse!=-1)
	   	{
    	   url = searchUser;
	   	}
        else if(url==deleteUser)
   	   {
    	   url = searchDeleteUser;
   	   } 
       else if(url==deleteUserSubmitURL)
   	   {
    	   url = searchDeleteUser;
   	   } 
       else if(deleteUserResponse!=-1)
   	   {
    	   url = searchDeleteUser;
   	   }
       else if(url==getUserADURL)
   	   {
   	   		url = searchADUser;
   	   }
       else if(url==createUserURL)
   	   {
   	   		url = searchADUser;
   	   } 
       else if(searchADUserResponse!=-1)
   	   {
    	   url = searchADUser;
   	   }
       else if(url==deleteUserSubmitURL)
    	   {
    	   url = deleteUser;
    	   }
      $('#menuwrapper td').find('.active').removeClass('active');
      $('#menuwrapper td a').filter(function(){
          return this.href == url;
      }).parent().addClass('active');
  });
 
</script>
</head>
<body class="page-width">
	<div id="container" class="page-content">
	<div class="site-banner-web zaHeader"></div>
	<div id="menuwrapper" style="background: linear-gradient(to bottom, #32323A 0%, #141414 100%);">
	<table width="100%" >
	<c:if test="${userLoggedIn eq \"Admin\"}">
	<tr>
	<td style="margin-left:50px;"><a href="<%=request.getContextPath()%>/searchAD?responseMessage=''" style="font-size:small; ">Create-User</a></td>
	<td style="margin-left:50px;"><a href="<%=request.getContextPath()%>/search" style="font-size:small;">Edit-User</a></td>
	<td style="margin-left:50px;"><a href="<%=request.getContextPath()%>/searchDeleteUser" style="font-size:small;">Delete-User</a></td>
	</tr>
	</c:if>
	<c:if test="${userLoggedIn eq \"Manager\"}">
	<tr>
	<td><a href="<%=request.getContextPath()%>/search" style="font-size:small;">Edit-User</a></td>
	</tr>
	</c:if>
	</table>		
			<%--    <ul>
			<li><a  id="link1"  href="<%=request.getContextPath()%>/searchAD?responseMessage=''">Create User</a><li></ul> 
			 <ul>
			<li><a  id="link1"  href="<%=request.getContextPath()%>/deleteUser">Delete User</a><li></ul> 
			<ul>
			<li><a  id="link2"  href="<%=request.getContextPath()%>/search?responseMessage=''" onclick="highlight('search')">Edit-User Role</a><li></ul> 
			<li><a  id="link2"  href="<%=request.getContextPath()%>/search">Edit-User Role</a><li></ul>
			<ul>
			<li><a id="link3"  href="<%=request.getContextPath()%>/searchBranch">User-Branch Mapping</a><li></ul> 							
			<ul>
			<li><a id="link4"  href="<%=request.getContextPath()%>/searchArea">User-Area Mapping</a><li></ul> 
		    <ul>
		    <li><a  id="link5"  href="<%=request.getContextPath()%>/searchProvince">User-Province Mapping</a><li></ul>
		    
		     <ul style="align:right">
		    <li><a id="link6" class="rightdiv"  href="<%=request.getContextPath()%>/logout">&nbsp; &nbsp; &nbsp; &nbsp;LogOut</a></li>
		    </ul> --%> 
		    <div align="right" class="rightdiv"><a href="<%=request.getContextPath()%>/logout" style="font-size:small;"><img  alt="LogOut"  src="<c:url value="/images/door-in-icon.png"/>"><span>LogOut</span></a></div>
		    <div align="right" class="rightdiv"><span style="color:black;"><h3>Welcome <%=userName%></h3> </span></div>
			</div>
	</div> 
</body>
