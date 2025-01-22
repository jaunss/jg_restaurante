<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Lanche</title>

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

		<form action="${pageContext.request.contextPath}/Lanche" method="post"
			class="row g-3 mt-4">
			<input id="codigoL" type="hidden" name="codigo"
				value="${lanche.codigo}">

			<div class="col-md-6">
				<label for="nomeL" class="form-label">Nome do Lanche</label> <input
					id="nomeL" type="text" name="nome" value="${lanche.nome}"
					class="form-control" placeholder="Digite o nome do lanche"
					required="required">
			</div>

			<div class="col-md-6">
				<label for="descricaoL" class="form-label">Descrição do
					Lanche</label> <input id="descricaoL" type="tel" name="descricao_conteudo"
					value="${lanche.descricao_conteudo}" class="form-control"
					placeholder="Digite a descrição do Lanche" required="required">
			</div>

			<div class="col-md-6">
				<label for="precoL" class="form-label">Preço do Lanche</label> <input
					id="precoL" type="tel" name="preco" value="${lanche.preco}"
					class="form-control" placeholder="Digite o preço do Lanche"
					required="required">
			</div>

			<div class="col-12 text-end">
				<button type="reset" class="btn btn-secondary">
					<i class="bi bi-x-circle"></i> Limpar Dados
				</button>
				<button type="submit" class="btn btn-success">
					<i class="bi bi-save"></i> Salvar Lanche
				</button>
				<a class="btn btn-success btn-sm" style="height: 37px"
					href="${pageContext.request.contextPath}/Lanche?acao=listarLanche"
					title="Voltar"><i class="bi bi-pencil-square"></i> Voltar</a>
			</div>
		</form>
	</main>

	<jsp:include page="/paginas/template/footer/footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>