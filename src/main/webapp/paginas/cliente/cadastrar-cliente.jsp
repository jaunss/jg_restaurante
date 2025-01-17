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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
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
		<form action="${pageContext.request.contextPath}/Cliente"
			method="post" class="row g-3 mt-4">
			<input id="codigo" type="hidden" name="codigo"
				value="${cliente.codigo}">

			<div class="col-md-6">
				<label for="nome" class="form-label">Nome</label> <input id="nome"
					type="text" name="nome" value="${cliente.nome}"
					class="form-control" placeholder="Digite seu nome" required>
			</div>

			<div class="col-md-6">
				<label for="telefone" class="form-label">Email</label> <input
					id="email" type="email" name="email" value="${cliente.email}"
					class="form-control" placeholder="exemplo@exemplo.com" required>
			</div>

			<div class="col-md-6">
				<label for="telefone" class="form-label">Telefone</label> <input
					id="telefone" type="tel" name="telefone"
					value="${cliente.telefone}" class="form-control"
					placeholder="(99) 99999-9999" required>
			</div>

			<div class="col-md-6">
				<label for="telefone" class="form-label">CPF</label> <input id="cpf"
					type="text" name="cpf" value="${cliente.cpf}" class="form-control"
					placeholder="000.000.000-00 (Digitar somente os números)" required>
			</div>

			<div class="col-12 text-end">
				<button type="reset" class="btn btn-secondary">Limpar Dados</button>
				<button type="submit" class="btn btn-success">Cadastrar
					Cliente</button>
			</div>
		</form>
	</main>

	<jsp:include page="/paginas/template/footer/footer.jsp" />
</body>
</html>