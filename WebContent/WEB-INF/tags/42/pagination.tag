<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="startPage" required="true"%>
<%@ attribute name="endPage" required="true"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="orderby" required="true"%>
<%@ attribute name="desc" required="true"%>


<c:set var="predecessor" value="${currentPage-1}" />
<c:if test="${currentPage==startPage}">
	<c:set var="predecessor" value="${currentPage}" />
</c:if>

<c:set var="next" value="${currentPage+1}" />
<c:if test="${currentPage==endPage}">
	<c:set var="next" value="${currentPage}" />
</c:if>
	
	<div class=""  style="text-align:center; width:100%;">
		<ul class="pagination" style="margin:1px">

			<li><a
				href=<c:url value="${action}">
						<c:param name="pageNumber" value="${predecessor}" />
						<c:param name="orderby" value="${orderby}"/>
						<c:param name="desc" value="${desc}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>&laquo;</a></li>

			<c:forEach var="page" begin="${startPage}" end="${endPage}">

				<c:if test="${page==currentPage}">
					<li class="active"><a
						href=<c:url value="${action}">
						<c:param name="pageNumber" value="${page}" />
						<c:param name="orderby" value="${orderby}"/>
						<c:param name="desc" value="${desc}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>
							${page} </a></li>
				</c:if>
				<c:if test="${page!=currentPage}">
					<li><a
						href=<c:url value="${action}">
						<c:param name="pageNumber" value="${page}" />
						<c:param name="orderby" value="${orderby}"/>
						<c:param name="desc" value="${desc}"/>
						<c:param name="search" value="${search}"/>
					</c:url>>
							${page} </a></li>
				</c:if>
			</c:forEach>

			<li><a
				href=<c:url value="${action}">
						<c:param name="pageNumber" value="${next}" />
						<c:param name="orderby" value="${orderby}"/>
						<c:param name="desc" value="${desc}"/>
						<c:param name="search" value="${search}"/>			
					</c:url>>&raquo;</a></li>
		</ul>
		
		<div class="pull-right pagination" style="margin:2px;">
				<KD:search value="${currentPage}" method="pageNumber"
			action="Dashboard" column="2" />
		</div>
	</div>
	
	
