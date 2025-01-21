<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pedido</title>

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
		<c:if test="${mensagem != null}">
			<div class="alert">${mensagem}</div>
		</c:if>

		<a class="btn btn-success btn-sm" style="height: 37px"
			href="${pageContext.request.contextPath}/Pedido?acao=cadastrarPedido"
			title="Cadastrar Pedido"><i class="bi bi-pencil-square"></i>
			Fazer Pedido</a>

		<div class="table-responsive mt-4">
			<table class="table table-striped table-bordered">
				<thead style="background-color: #fd7e14; color: white;">
					<tr>
						<th>Data do Pedido</th>
						<th>Nome do Cliente</th>
						<th>Total em R$</th>
						<th>Observação</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pedido" items="${pedidos}">
						<tr>
							<td>${pedido.dataPedido}</td>
							<td>${pedido.cliente.nome}</td>
							<td>${pedido.total}</td>
							<td>${pedido.observacao}</td>
							<td><a class="btn btn-primary btn-sm"
								href="${pageContext.request.contextPath}/Pedido?acao=editarPedido&codigo=${pedido.codigo}"
								title="Editar Pedido"><i class="bi bi-pencil-square"></i></a> <a
								class="btn btn-danger btn-sm"
								href="${pageContext.request.contextPath}/Pedido?acao=removerPedido&codigo=${pedido.codigo}"
								title="Remover Pedido"><i class="bi bi-trash"></i></a></td>
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