<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Monitor</title>
</head>
<body>
<h1>
	This is a monitor!
</h1>

<form:form modelAttribute="queryParams" action="${pageContext.request.contextPath}/setQuery" method="post">
  <form:label for="numberOfQueries" path="numberOfQueries">Number of C-SPARQL queries</form:label>
  <form:input path="numberOfQueries" type="text"/>  
  <br />   
  <form:label for="initialThreshold" path="initialThreshold">Initial threshold value</form:label>
  <form:input path="initialThreshold" type="text"/>  
  <br />  
  <input type="submit" value="Set"/>
</form:form>

<c:if test="${set}">
	<p>Number of queries: ${numberOfQueries}</p>
	<p>Initial threshold value: ${initialThreshold}</p>
</c:if>
</body>
</html>
