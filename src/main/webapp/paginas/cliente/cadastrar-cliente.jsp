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

		<form action="${pageContext.request.contextPath}/Cliente"
			method="post" class="row g-3 mt-4">
			<input id="codigoC" type="hidden" name="codigo"
				value="${cliente.codigo}">

			<div class="col-md-6">
				<label for="nomeC" class="form-label">Nome do Cliente</label> <input
					id="nomeC" type="text" name="nome" value="${cliente.nome}"
					class="form-control" placeholder="Digite seu Nome" maxlength="100"
					required="required">
			</div>

			<div class="col-md-6">
				<label for="emailC" class="form-label">Email do Cliente</label> <input
					id="emailC" type="email" name="email" value="${cliente.email}"
					class="form-control"
					placeholder="Digite seu Email (Exemplo: exemplo@exemplo.com)"
					maxlength="100" required="required">
			</div>

			<div class="col-md-6">
				<label for="telefoneC" class="form-label">Telefone do
					Cliente</label> <input id="telefoneC" type="tel" name="telefone"
					value="${cliente.telefone}" class="form-control"
					placeholder="Digite seu Telefone (Exemplo: (99) 99999-9999)"
					maxlength="15" required="required">
			</div>

			<div class="col-md-6">
				<label for="cpfC" class="form-label">CPF do Cliente</label> <input
					id="cpfC" type="text" name="cpf" value="${cliente.cpf}"
					class="form-control"
					placeholder="Digite seu CPF (Exemplo: 000.000.000-00)"
					maxlength="14" required="required">
			</div>

			<div class="col-md-6">
				<label for="senhaC" class="form-label">Senha do Cliente</label> <input
					id="senhaC" type="password" name="senha" value="${cliente.senha}"
					class="form-control" placeholder="Digite sua Senha" maxlength="50"
					required="required">
			</div>

			<!-- Se o perfil do cliente for do tipo 2, ou seja, Administrador, o mesmo verá a listagem do perfil! -->
			<c:choose>
				<c:when
					test="${sessionScope.clienteComCadastro != null && sessionScope.clienteComCadastro.perfil != null && sessionScope.clienteComCadastro.perfil.codigo == 2}">
					<div class="col-md-6">
						<label for="perfilC" class="form-label">Perfil do Cliente</label>
						<select id="perfilC" class="form-select" name="perfil.codigo">
							<option value="" selected="selected" />
							<c:forEach var="perfil" items="${perfis}">
								<option value="${perfil.codigo}"
									<c:if test="${perfil.codigo == cliente.perfil.codigo}">selected</c:if>>${perfil.nome}</option>
							</c:forEach>
						</select>
					</div>
				</c:when>
			</c:choose>

			<div class="col-md-6">
				<label for="estadoC" class="form-label">Estado</label> <select
					id="estadoC" class="form-select" name="estado.codigo">
					<option value="" selected="selected">Selecione um estado</option>
					<c:forEach var="estado" items="${estados}">
						<option value="${estado.codigo}">${estado.nome}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-6">
				<label for="cidadeC" class="form-label">Cidade</label> <select
					id="cidadeC" class="form-select" name="cidade.codigo">
					<option value="" selected="selected">Selecione uma cidade</option>
				</select>
			</div>

			<div class="col-12 text-end">
				<button type="reset" class="btn btn-secondary">
					<i class="bi bi-x-circle"></i> Limpar Dados
				</button>
				<button type="submit" class="btn btn-success">
					<i class="bi bi-save"></i> Salvar Cliente
				</button>
				<a class="btn btn-success btn-sm" style="height: 37px"
					href="${pageContext.request.contextPath}/Autenticacao?acao=autenticarCliente"
					title="Voltar"><i class="bi bi-pencil-square"></i> Voltar</a>
			</div>
		</form>
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