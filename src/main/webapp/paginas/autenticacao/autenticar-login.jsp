<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Autenticar Cliente</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/bootstrap-5.3.0.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
	<jsp:include page="/paginas/template/navbar/navbar.jsp" />

	<main class="container mt-5">
		<c:if test="${tipoMensagem != null && tipoMensagem == 'erro'}">
			<c:if test="${mensagem != null}">
				<div class="alert alert-danger" role="alert">${mensagem}</div>
			</c:if>
		</c:if>

		<c:if test="${tipoMensagem != null && tipoMensagem == 'perigo'}">
			<c:if test="${mensagem != null}">
				<div class="alert alert-warning" role="alert">${mensagem}</div>
			</c:if>
		</c:if>

		<c:if test="${tipoMensagem != null && tipoMensagem == 'sucesso'}">
			<c:if test="${mensagem != null}">
				<div class="alert alert-success" role="alert">${mensagem}</div>
			</c:if>
		</c:if>

		<form action="${pageContext.request.contextPath}/Autenticacao"
			method="post" class="row g-3 mt-4">
			<div class="col-md-6">
				<label for="email" class="form-label">Email</label> <input
					id="email" type="text" name="email" class="form-control"
					placeholder="Insira o email" required="required">
			</div>

			<div class="col-md-6">
				<label for="senha" class="form-label">Senha</label> <input
					id="senha" type="password" name="senha" class="form-control"
					placeholder="Insira a senha" required="required">
			</div>

			<div class="col-12 text-center">
				<button type="reset" class="btn btn-secondary">Limpar</button>
				<button type="submit" class="btn btn-success">Realizar
					Login</button>
				<a class="btn btn-success btn-sm" style="height: 37px"
					href="${pageContext.request.contextPath}/Cliente?acao=cadastrarCliente"
					title="Cadastrar Cliente"><i class="bi bi-pencil-square"></i>
					Cadastre-se</a>
			</div>
		</form>
	</main>

	<jsp:include page="/paginas/template/footer/footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>