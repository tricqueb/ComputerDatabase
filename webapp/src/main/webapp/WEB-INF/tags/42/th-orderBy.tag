<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="text" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="width" required="false"%>


<!-- Getting OrderBy and direction values -->
<c:set var="orderBy" value="${page.getSort().iterator().next().getProperty()}"/>
<c:set var="direction" value="${page.getSort().iterator().next().getDirection()}"/>


<c:set scope="request" var="orderDirectionLocal" value="ASC" />
<c:if test="${orderBy eq value}">
	<c:choose>
		<c:when test="${direction eq 'DESC'}">
			<c:set scope="request" var="orderDirectionLocal" value="ASC" />
		</c:when>
		<c:otherwise>
			<c:set scope="request" var="orderDirectionLocal" value="DESC" />
		</c:otherwise>
	</c:choose>
</c:if>

<th style="width:${width}"><a href=<c:url value="${action}">
						<c:param name="page" value="${page.getNumber()}" />
						<c:param name="sort" value="${value},${orderDirectionLocal}"/>
						<c:param name="search" value="${search}"/>
			</c:url>
	>
		<c:out value="${text}"></c:out>
	</a>
</th>