<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="text" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="orderby" required="true"%>
<%@ attribute name="width" required="false"%>

<c:if test="${orderby eq value}">
	<c:choose>
		<c:when test="${desc eq 'true'}">
			<c:set scope="request" var="desc" value="false" />
		</c:when>
		<c:otherwise>
			<c:set scope="request" var="desc" value="true" />
		</c:otherwise>
	</c:choose>
</c:if>

<th style="width:${width}"><a href=<c:url value="${action}">
				<c:param name="pageNumber" value="${currentPage}" />
				<c:param name="orderby" value="${value}"/>
				<c:param name="desc" value="${desc}"/>
				<c:param name="search" value="${search}"/>
			</c:url>
	>
		<c:out value="${text}"></c:out>
	</a>
</th>