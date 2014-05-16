<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class=container>

<p class="bg-danger">
<h2> An error ${errorCode} happens !</h2>
<pre>
${message}
(\___/)
(='.'=) 
(")_(")
</pre>
</p>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />