<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cliente</title>

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

		<a class="btn btn-success btn-sm" style="height: 37px"
			href="${pageContext.request.contextPath}/Cliente?acao=cadastrarCliente"
			title="Cadastrar Cliente"><i class="bi bi-pencil-square"></i>
			Cadastrar Cliente</a>

		<div class="table-responsive mt-4">
			<table class="table table-striped table-bordered">
				<thead style="background-color: #fd7e14; color: white;">
					<tr>
						<th>Nome</th>
						<th>Email</th>
						<th>Telefone</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="cliente" items="${clientes}">
						<tr>
							<td>${cliente.nome}</td>
							<td>${cliente.email}</td>
							<td>${cliente.telefone}</td>
							<td><a class="btn btn-primary btn-sm"
								href="${pageContext.request.contextPath}/Cliente?acao=editarCliente&codigo=${cliente.codigo}"
								title="Editar Cliente"><i class="bi bi-pencil-square"></i></a> <a
								class="btn btn-danger btn-sm"
								href="${pageContext.request.contextPath}/Cliente?acao=removerCliente&codigo=${cliente.codigo}"
								title="Remover Cliente"><i class="bi bi-trash"></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>

	<jsp:include page="/paginas/template/footer/footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>