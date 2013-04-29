<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>My application</title>
</head>
<body>
<h1>Consume a web service and use the RabbitMQ queue to send RDF triples</h1>
<form:form modelAttribute="invocationParams" action="${pageContext.request.contextPath}/execute" method="post">
  <form:label for="numberOfThreads" path="numberOfThreads">Number of parallel threads accessing the web service</form:label>
  <form:input path="numberOfThreads" type="text"/>
  <br />
  <form:label for="numberOfInvocations" path="numberOfInvocations">Number of invocations for each thread</form:label>
  <form:input path="numberOfInvocations" type="text"/>
  <br />   
  <input type="submit" value="Execute"/>
</form:form>

<c:if test="${executed}">
	<p>Value: ${executed}</p>
</c:if>

</body>
</html>
