<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>JG Restaurante</title>

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

		<h1>Escolha o seu lanche!</h1>
		<p>Confira algumas das nossas deliciosas opções de lanches abaixo.</p>

		<div class="row mt-4">
			<div class="col-md-4 mb-4">
				<div class="card">
					<img
						src="https://gastronomiacarioca.zonasul.com.br/wp-content/uploads/2023/10/bauru_ilustrativo_sanduiche_zona_sul.jpg"
						class="card-img-top" alt="Lanche 1">
					<div class="card-body">
						<h5 class="card-title">Lanche Clássico</h5>
						<p class="card-text">Deliciosa combinação de carne suculenta,
							queijo derretido, e molho especial.</p>
					</div>
				</div>
			</div>

			<div class="col-md-4 mb-4">
				<div class="card">
					<img
						src="https://img.freepik.com/fotos-premium/wrap-vegano-feito-de-vegetais-assados-no-pao-indiano-macarrao-inteiro-sem-leite-com-vegetais-na-superficie_72932-1111.jpg"
						class="card-img-top" alt="Lanche 2">
					<div class="card-body">
						<h5 class="card-title">Lanche Vegano</h5>
						<p class="card-text">Lanche saudável com vegetais frescos,
							hambúrguer vegano e molho especial.</p>
					</div>
				</div>
			</div>

			<div class="col-md-4 mb-4">
				<div class="card">
					<img
						src="https://i0.statig.com.br/bancodeimagens/e8/64/ne/e864newip14eyszstiqwvhmnk.jpg"
						class="card-img-top" alt="Lanche 3">
					<div class="card-body">
						<h5 class="card-title">Lanche Premium</h5>
						<p class="card-text">Lanche gourmet com carne de alta
							qualidade, queijo importado e molhos exclusivos.</p>
					</div>
				</div>
			</div>
		</div>
	</main>

	<jsp:include page="/paginas/template/footer/footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>