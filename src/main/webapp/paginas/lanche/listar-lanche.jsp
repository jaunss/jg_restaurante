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
		<c:if test="${mensagem != null}">
			<div class="alert">${mensagem}</div>
		</c:if>

		<a class="btn btn-success btn-sm" style="height: 37px"
			href="${pageContext.request.contextPath}/Lanche?acao=cadastrarLanche"
			title="Cadastrar Lanche"><i class="bi bi-pencil-square"></i>
			Cadastrar Lanche</a>

		<div class="table-responsive mt-4">
			<table class="table table-striped table-bordered">
				<thead style="background-color: #fd7e14; color: white;">
					<tr>
						<th>Nome</th>
						<th>Descrição do Lanche</th>
						<th>Preço</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="lanche" items="${lanches}">
						<tr>
							<td>${lanche.nome}</td>
							<td>${lanche.descricao_conteudo}</td>
							<td>${lanche.preco}</td>
							<td><a class="btn btn-primary btn-sm"
								href="${pageContext.request.contextPath}/Lanche?acao=editarLanche&codigo=${lanche.codigo}"
								title="Editar Lanche"><i class="bi bi-pencil-square"></i></a> <a
								class="btn btn-danger btn-sm"
								href="${pageContext.request.contextPath}/Lanche?acao=removerLanche&codigo=${lanche.codigo}"
								title="Remover Lanche"><i class="bi bi-trash"></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>

	<jsp:include page="/paginas/template/footer/footer.jsp" />
</body>
</html>