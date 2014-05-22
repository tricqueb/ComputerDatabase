<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="lang" required="false"%>

<c:url value="">
	<c:param name="currentPage" value="${pagination.getCurrentPage()}" />
	<c:param name="orderById" value="${page.orderByColumn}"/>
	<c:param name="orderDirection" value="${page.orderDirection}"/>
	<c:param name="search" value="${page.search}"/>
	<c:param name="language" value="${lang}"/>
</c:url>