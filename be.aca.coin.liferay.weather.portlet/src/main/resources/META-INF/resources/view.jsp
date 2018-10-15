<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<c:if test="${!empty weather}">
	<p>It is <fmt:formatNumber value="${weather.temperature}" type="number" maxFractionDigits="1"/> degrees<c:if test="${!empty city}"> in ${city}</c:if>, so...</p>
	<c:choose>
		<c:when test="${weather.temperature <= 0}">
			<p><img src="${renderRequest.contextPath}/images/scarf.jpg"/></p>
			<h3>Scarf</h3>
			<p>&euro; 10,99</p>
		</c:when>
		<c:when test="${weather.temperature > 0 && weather.temperature < 20}">
			<p><img src="${renderRequest.contextPath}/images/jacket.jpg"/></p>
			<h3>Jacket</h3>
			<p>&euro; 34,95</p>
		</c:when>
		<c:otherwise>
			<p><img src="${renderRequest.contextPath}/images/flipflops.jpg"/></p>
			<h3>Flipflops</h3>
			<p>&euro; 5,99</p>
		</c:otherwise>
	</c:choose>
	<button class="btn btn-default">Buy now!</button>
</c:if>