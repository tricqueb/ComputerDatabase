<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="lang" required="false"%>

<c:set var="orderBy"
	value="${page.getSort().iterator().next().getProperty()}" />
<c:set var="direction"
	value="${page.getSort().iterator().next().getDirection()}" />

<c:url value="">
	<c:param name="pageNumber" value="${page.getNumber()}" />
	<c:param name="sort" value="${orderBy},${direction}" />
	<c:param name="search" value="${search}" />
	<c:param name="language" value="${lang}" />
</c:url>