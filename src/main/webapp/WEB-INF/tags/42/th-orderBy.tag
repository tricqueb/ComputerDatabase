<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="text" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="action" required="true"%>
<%@ attribute type="com.excilys.computerdatabase.page.Page" name="page" required="true"%>
<%@ attribute name="width" required="false"%>
<%@ attribute name="search" required="false"%>

<c:set scope="request" var="orderDirectionLocal" value="false" />
<c:if test="${page.getOrderById() eq value}">
	<c:choose>
		<c:when test="${page.getOrderDirection() eq true}">
			<c:set scope="request" var="orderDirectionLocal" value="false" />
		</c:when>
		<c:otherwise>
			<c:set scope="request" var="orderDirectionLocal" value="true" />
		</c:otherwise>
	</c:choose>
</c:if>

<th style="width:${width}"><a href=<c:url value="${action}">
				<c:param name="currentPage" value="${pagination.getCurrentPage()}" />
				<c:param name="orderById" value="${value}"/>
				<c:param name="orderDirection" value="${orderDirectionLocal}"/>
				<c:param name="search" value="${search}"/>
			</c:url>
	>
		<c:out value="${text}"></c:out>
	</a>
</th>