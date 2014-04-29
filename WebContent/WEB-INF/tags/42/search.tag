<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="action" required="true"%>
<%@ attribute name="method" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="column" required="true"%>
<%@ attribute name="placeholder" required="false"%>
<%@ attribute name="inline" required="false"%>

<form class="" action="${action}" method="GET">
	<div class="${inline}">
		 
		<input type="hidden" name="orderby" value="${orderby}" /> 
		<input type="hidden" name="desc" value="${desc}" />
		<c:if test="${method eq 'pageNumber'}" >
			<input type="hidden" name="search" value="${search}" />
		</c:if>
		
		<input class="form-control" type="${method}" id="${id}"
			name="${method}" value="${value}" placeholder="${placeholder}"
			size="${column}" />
	</div>
</form>