<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute type="com.excilys.computerdatabase.pages.Pagination" name="pagination" required="true"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="orderBy" required="true"%>
<%@ attribute name="orderDirection" required="true"%>


<c:set var="predecessor" value="${pagination.getCurrentPage()-1}" />
<c:if test="${pagination.getCurrentPage()==pagination.getStartPage()}">
	<c:set var="predecessor" value="${pagination.getCurrentPage()}" />
</c:if>

<c:set var="next" value="${pagination.getCurrentPage()+1}" />
<c:if test="${pagination.getCurrentPage()==pagination.getEndPage()}">
	<c:set var="next" value="${pagination.getCurrentPage()}" />
</c:if>
	
	<div class=""  style="text-align:center; width:100%;">
		<ul class="pagination" style="margin:1px">

			<li><a
				href=<c:url value="${action}">
						<c:param name="currentPage" value="${predecessor}" />
						<c:param name="orderById" value="${orderBy}"/>
						<c:param name="orderDirection" value="${orderDirection}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>&laquo;</a></li>

			<c:forEach var="displayedPage" begin="${pagination.getStartPage()}" end="${pagination.getEndPage()}">

				<c:if test="${displayedPage==pagination.getCurrentPage()}">
					<li class="active"><a
						href=<c:url value="${action}">
						<c:param name="currentPage" value="${displayedPage}" />
						<c:param name="orderById" value="${orderBy}"/>
						<c:param name="orderDirection" value="${orderDirection}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>
							${displayedPage} </a></li>
				</c:if>
				<c:if test="${displayedPage!=pagination.getCurrentPage()}">
					<li><a
						href=<c:url value="${action}">
						<c:param name="currentPage" value="${displayedPage}" />
						<c:param name="orderById" value="${orderBy}"/>
						<c:param name="orderDirection" value="${orderDirection}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>
							${displayedPage} </a></li>
				</c:if>
			</c:forEach>

			<li><a
				href=<c:url value="${action}">
						<c:param name="currentPage" value="${next}" />
						<c:param name="orderById" value="${orderBy}"/>
						<c:param name="orderDirection" value="${orderDirection}"/>
						<c:param name="search" value="${search}"/>			
					</c:url>>&raquo;</a></li>
		</ul>
		
		<div class="pull-right pagination" style="margin:2px;">
				<KD:search value="${pagination.getCurrentPage()}" method="currentPage"
			action="Dashboard" column="2" />
		</div>
	</div>
	
	
