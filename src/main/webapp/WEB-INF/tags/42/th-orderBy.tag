<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="text" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="action" required="true"%>
<%@ attribute type="com.excilys.computerdatabase.pages.Page" name="page" required="true"%>
<%@ attribute name="width" required="false"%>
<%@ attribute name="search" required="false"%>

<c:if test="${page.getOrderBy() eq value}">
	<c:choose>
		<c:when test="${page.getDesc() eq true}">
			<c:set scope="request" var="descLocal" value="false" />
		</c:when>
		<c:otherwise>
			<c:set scope="request" var="descLocal" value="true" />
		</c:otherwise>
	</c:choose>
</c:if>

<th style="width:${width}"><a href=<c:url value="${action}">
				<c:param name="pageNumber" value="${page.getCurrentPage()}" />
				<c:param name="orderby" value="${value}"/>
				<c:param name="desc" value="${descLocal}"/>
				<c:param name="search" value="${search}"/>
			</c:url>
	>
		<c:out value="${text}"></c:out>
	</a>
</th>