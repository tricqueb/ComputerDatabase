<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ attribute name="action" required="true"%>
<%@ attribute name="method" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="column" required="true"%>
<%@ attribute name="springlabel" required="true"%>
<%@ attribute name="inline" required="false"%>


<!-- Getting OrderBy and direction values -->
<c:set var="orderBy"
	value="${page.getSort().iterator().next().getProperty()}" />
<c:set var="direction"
	value="${page.getSort().iterator().next().getDirection()}" />


<spring:message code="${springlabel}" var="placeholder" />

<form class="" action="${action}" method="GET">
	<div class="${inline}">
		 <input class="form-control" type="${method}" id="${id}"
			name="${method}" value="${value}" placeholder="${placeholder}"
			size="${column}" />
			<input type="hidden" name="sort" value="${orderBy},${direction}" /> 
		<c:if test="${method == 'page'}" >
			<input type="hidden" name="search" value="${search}" />
		</c:if>
	</div>
</form>