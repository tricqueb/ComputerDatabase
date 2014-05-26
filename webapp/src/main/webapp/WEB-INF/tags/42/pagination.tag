<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ attribute name="action" required="true"%>

<!-- Getting OrderBy and direction values -->
<c:set var="orderBy" value="${page.getSort().iterator().next().getProperty()}"/>
<c:set var="direction" value="${page.getSort().iterator().next().getDirection()}"/>

<c:set var="predecessor" value="${page.getNumber()-1}" />
<c:if test="${page.isFirstPage()}">
	<c:set var="predecessor" value="${page.getNumber()}" />
</c:if>

<c:set var="next" value="${page.getNumber()+1}" />
<c:if test="${page.getNumber()==page.getTotalPages()}">
	<c:set var="next" value="${page.getNumber()}" />
</c:if>
	
<c:set var="paginationStart" value="${page.getNumber()-5}" />
<c:if test="${paginationStart<=0}">
	<c:set var="paginationStart" value="1" />
</c:if>

<c:set var="paginationEnd" value="${page.getNumber()+5}" />
<c:if test="${paginationStart>page.getTotalPages()}">
	<c:set var="paginationEnd" value="${page.getTotalPages()}" />
</c:if>

	<div class=""  style="text-align:center; width:100%;">
		<ul class="pagination" style="margin:1px">
			<li><a
				href=<c:url value="${action}">
						<c:param name="page" value="${predecessor}" />
						<c:param name="sort" value="${orderBy},${direction}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>&laquo;</a></li>

			<c:forEach var="displayedPage" begin="${paginationStart}" end="${paginationEnd}">

				<c:if test="${displayedPage == page.getNumber()+1}">
					<li class="active"><a
						href=<c:url value="${action}">
						<c:param name="page" value="${displayedPage-1}" />
						<c:param name="sort" value="${orderBy},${direction}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>
							${displayedPage} </a></li>
				</c:if>
				<c:if test="${displayedPage!=page.getNumber()+1}">
					<li ><a
						href=<c:url value="${action}">
						<c:param name="page" value="${displayedPage-1}" />
						<c:param name="sort" value="${orderBy},${direction}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>
							${displayedPage} </a>
							</li>
				</c:if>
			</c:forEach>

			<li><a
				href=<c:url value="${action}">
						<c:param name="page" value="${next}" />
						<c:param name="sort" value="${orderBy},${direction}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>&raquo;</a></li>
		</ul>
		
		<div class="pull-right pagination" style="margin:2px;">
				<KD:search value="${page.getNumber()+1}" method="page"
			action="Dashboard" column="2" springlabel="label.search" />
		</div>
	</div>
	
	
