<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Endereço</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/bootstrap-5.3.0.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/Home?acao=home"><img
				class="id_jg_restaurante"
				src="${pageContext.request.contextPath}/assets/images/jg_restaurante.png"></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Alternar navegação">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<c:forEach var="menu" items="${menus}">
						<li class="nav-item"><a
							class="nav-link ${acao == menu.acao ? 'active' : ''}"
							href="${pageContext.request.contextPath}${menu.url}?acao=${menu.acao}"><i
								class="${menu.icone}"></i> ${menu.nome}</a></li>
					</c:forEach>

					<c:choose>
						<c:when test="${sessionScope.clienteComCadastro == null}">
							<li class="nav-item"><a
								class="nav-link ${acao == 'autenticarCliente' ? 'active' : ''}"
								href="${pageContext.request.contextPath}/Autenticacao?acao=autenticarCliente"><i
									class="bi bi-box-arrow-in-right"></i> Autenticar</a></li>
						</c:when>

						<c:otherwise>
							<li class="nav-item"><a
								class="nav-link ${acao == 'deslogarCliente' ? 'active' : ''}"
								href="${pageContext.request.contextPath}/Autenticacao?acao=deslogarCliente"><i
									class="bi bi-box-arrow-right"></i> Sair</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>

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

		<!-- Se o perfil do cliente for do tipo 2, ou seja, Administrador, o mesmo verá o link de cadastro do cliente! -->
		<c:choose>
			<c:when
				test="${sessionScope.clienteComCadastro != null && sessionScope.clienteComCadastro.perfil != null && sessionScope.clienteComCadastro.perfil.codigo == 2}">
				<a class="btn btn-success btn-sm" style="height: 37px"
					href="${pageContext.request.contextPath}/Endereco?acao=cadastrarEndereco"
					title="Cadastrar Endereço"><i class="bi bi-pencil-square"></i>
					Cadastrar Endereço</a>
			</c:when>
		</c:choose>

		<div class="table-responsive mt-4">
			<table class="table table-striped table-bordered">
				<thead style="background-color: #fd7e14; color: white;">
					<tr>
						<th>CEP</th>
						<th>Logradouro</th>
						<th>Complemento</th>
						<th>Cidade (Localidade)</th>
						<th>Estado (UF)</th>
						<th>Bairro</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<!-- Se o perfil do cliente for do tipo 1, ou seja, Consumidor, o mesmo verá somente o resultado do seu cadastro! -->
					<c:choose>
						<c:when
							test="${sessionScope.clienteComCadastro != null && sessionScope.clienteComCadastro.perfil != null && sessionScope.clienteComCadastro.perfil.codigo == 1}">
							<tr>
								<td>${endereco.cep}</td>
								<td>${endereco.logradouro}</td>
								<td>${endereco.complemento}</td>
								<td>${endereco.localidade}</td>
								<td>${endereco.uf}</td>
								<td>${endereco.bairro}</td>
								<td><a class="btn btn-primary btn-sm"
									href="${pageContext.request.contextPath}/Endereco?acao=editarEndereco&codigo=${endereco.codigo}"
									title="Editar Endereço"><i class="bi bi-pencil-square"></i></a>
									<a class="btn btn-danger btn-sm"
									href="${pageContext.request.contextPath}/Endereco?acao=removerEndereco&codigo=${endereco.codigo}"
									title="Remover Endereço"><i class="bi bi-trash"></i></a></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach var="endereco" items="${enderecos}">
								<tr>
									<td>${endereco.cep}</td>
									<td>${endereco.logradouro}</td>
									<td>${endereco.complemento}</td>
									<td>${endereco.localidade}</td>
									<td>${endereco.uf}</td>
									<td>${endereco.bairro}</td>
									<td><a class="btn btn-primary btn-sm"
										href="${pageContext.request.contextPath}/Endereco?acao=editarEndereco&codigo=${endereco.codigo}"
										title="Editar Endereço"><i class="bi bi-pencil-square"></i></a>
										<a class="btn btn-danger btn-sm"
										href="${pageContext.request.contextPath}/Endereco?acao=removerEndereco&codigo=${endereco.codigo}"
										title="Remover Endereço"><i class="bi bi-trash"></i></a></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</main>

	<footer class="p-3 bg-success text-white text-center py-3">
		<p>&copy; 2025 - JG Restaurante. Todos os direitos reservados.</p>
	</footer>

	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>