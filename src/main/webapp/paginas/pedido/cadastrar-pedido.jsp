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
			<div
				class="alert ${tipoMensagem == 'sucesso' ? 'alert-success' : tipoMensagem == 'falha' ? 'alert-danger' : ''}"
				role="alert">${mensagem}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/Pedido" method="post"
			class="row g-3 mt-4">
			<input type="hidden" id="codigo" name="codigo"
				value="${pedido.codigo}">

			<div class="col-md-6">
				<label for="nomeCliente" class="form-label">Nome do Cliente</label>
				<input id="nomeCliente" type="text" name="cliente.nome"
					value="${pedido.cliente.nome}" class="form-control"
					placeholder="Nome do Cliente" readonly="readonly">
			</div>

			<div class="col-md-6">
				<label for="lanche" class="form-label">Escolha seu Lanche</label> <select
					class="form-select" id="lanche" name="lanches" multiple="multiple"
					required="required">
					<option selected="selected">Selecione...</option>
					<c:forEach var="lanche" items="${lanches}">
						<option value="${lanche.codigo}">${lanche.nome}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-3">
				<label for="total" class="form-label">Total</label> <input
					id="total" type="text" name="total" value="${pedido.total}"
					class="form-control" placeholder="Total" readonly="readonly">
			</div>

			<div class="col-12">
				<label for="observacao" class="form-label">Observação</label>
				<textarea id="observacao" name="observacao" class="form-control"
					rows="5" placeholder="Alguma observação sobre o pedido?">${pedido.observacao}</textarea>
			</div>

			<div class="col-12 text-end">
				<button type="reset" class="btn btn-secondary">Limpar</button>
				<button type="submit" class="btn btn-success">Salvar Pedido</button>
				<a class="btn btn-success btn-sm" style="height: 37px"
					href="${pageContext.request.contextPath}/Pedido?acao=listarPedido"
					title="Voltar"><i class="bi bi-pencil-square"></i> Voltar</a>
			</div>
		</form>
	</main>

	<jsp:include page="/paginas/template/footer/footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>