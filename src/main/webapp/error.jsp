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
		<c:if test="${codigoStatus != null}">
			<c:if test="${statusCode == 404}">
				<p>Página não encontrada: (Erro 404)</p>
			</c:if>

			<c:if test="${statusCode == 500}">
				<p>Erro interno do servidor: (Erro 500)</p>
			</c:if>
		</c:if>
	</p>
	<p>
		<c:if test="${mensagem != null}">
			<p>Mensagem: ${message}</p>
		</c:if>
	</p>
	<c:if test="${exception != null}">
		<p>Exceção: ${exception}</p>
	</c:if>
</body>
</html>