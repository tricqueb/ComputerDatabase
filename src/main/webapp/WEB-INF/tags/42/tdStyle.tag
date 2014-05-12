<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="id" required="false"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="hidden" required="false"%>
<%@ attribute name="action" required="true"%>


<td id="${id}" style="display:${hidden};"><c:out value="${value}"> </c:out></td>



