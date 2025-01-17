<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cliente</title>
<!-- Link do Bootstrap 5 -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/bootstrap-5.3.0.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/bootstrap-icons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/style.css">
</head>
<body>
	<!-- Barra de navegação -->
	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"><img class="id_jg_restaurante"
				src="<%=request.getContextPath()%>/assets/images/jg_restaurante.png"></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Alternar navegação">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/index.jsp">Início</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/Pedido?acao=listarPedido"><i
							class="bi bi-bag"></i> Pedido</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/Lanche?acao=listarLanche"><i
							class="bi bi-egg-fried"></i> Lanche</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/Cliente?acao=listarCliente"><i
							class="bi bi-people"></i> Cliente</a></li>
					<li class="nav-item"><a class="nav-link active"
						href="${pageContext.request.contextPath}/Carrinho?acao=listarCarrinho"><i
							class="bi bi-cart"></i> Carrinho</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/Autenticacao?acao=autenticar"><i
							class="bi bi-box-arrow-in-right"></i> Login</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Conteúdo principal -->
	<main class="container mt-5">
		<c:if test="${mensagem != null}">
			<div
				class="alert ${tipoMensagem == 'sucesso' ? 'alert-success' : tipoMensagem == 'falha' ? 'alert-danger' : ''}"
				role="alert">${mensagem}</div>
		</c:if>
		<form class="row g-3 mt-4">
			<!-- Nome -->
			<div class="col-md-6">
				<label for="nome" class="form-label">Seu Nome</label> <input
					type="text" class="form-control" id="nome"
					placeholder="Digite seu nome" required>
			</div>

			<!-- Telefone -->
			<div class="col-md-6">
				<label for="telefone" class="form-label">Telefone</label> <input
					type="tel" class="form-control" id="telefone"
					placeholder="(99) 99999-9999" required>
			</div>

			<!-- Escolha do Lanche -->
			<div class="col-md-6">
				<label for="lanche" class="form-label">Escolha seu Lanche</label> <select
					class="form-select" id="lanche" required>
					<option selected disabled>Selecione...</option>
					<option value="classico">Lanche Clássico</option>
					<option value="vegano">Lanche Vegano</option>
					<option value="premium">Lanche Premium</option>
				</select>
			</div>

			<!-- Quantidade -->
			<div class="col-md-3">
				<label for="quantidade" class="form-label">Quantidade</label> <input
					type="number" class="form-control" id="quantidade" min="1"
					value="1" required>
			</div>

			<!-- Observações -->
			<div class="col-12">
				<label for="observacoes" class="form-label">Observações</label>
				<textarea class="form-control" id="observacoes" rows="3"
					placeholder="Alguma observação sobre o pedido?"></textarea>
			</div>

			<!-- Botões -->
			<div class="col-12 text-end">
				<button type="reset" class="btn btn-secondary">Limpar</button>
				<button type="submit" class="btn btn-success">Fazer Pedido</button>
			</div>
		</form>
	</main>

	<footer class="p-3 bg-success text-white text-center py-3">
		<p>&copy; 2025 - JG Restaurante. Todos os direitos reservados.</p>
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>