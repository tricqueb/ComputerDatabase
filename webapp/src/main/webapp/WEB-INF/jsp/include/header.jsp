<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript">
  var labels = new Array();
  labels['error.10'] = "<spring:message code='error.10' javaScriptEscape='true' />";
  labels['error.11'] = "<spring:message code='error.11' javaScriptEscape='true' />";
  labels['error.20'] = "<spring:message code='error.20' javaScriptEscape='true' />";
  labels['error.21'] = "<spring:message code='error.21' javaScriptEscape='true' />";
  labels['error.31'] = "<spring:message code='error.31' javaScriptEscape='true' />";
  labels['error.33'] = "<spring:message code='error.33' javaScriptEscape='true' />";
  labels['js.format.date'] = "<spring:message code='js.format.date' javaScriptEscape='true'/>";
</script>
</head>
<body style="padding-bottom: 70px;" class="">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/computer-database/Dashboard"> <spring:message
						code="label.title" /></a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Language <b class="caret"></b></a>
					<ul class="dropdown-menu">
						
							<li><a href="<KD:dashboardLink lang="en_EN"/>"> English </a></li>
						<li><a href="<KD:dashboardLink lang="fr_FR"/>">Français</a></li>
					</ul></li>
		</div>
	</nav>