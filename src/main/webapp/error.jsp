<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Erro</title>
</head>
<body>
	<h1>Ops! Algo deu errado.</h1>
	<p>
		<c:if test="${mensagem != null}">
			<p>Mensagem: ${mensagem}</p>
		</c:if>
	</p>
</body>
</html>