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

		<form action="${pageContext.request.contextPath}/Endereco"
			method="post" class="row g-3 mt-4">
			<input id="codigoE" type="hidden" name="codigo"
				value="${endereco.codigo}">

			<div class="col-md-6">
				<label for="cepE" class="form-label">CEP</label> <input id="cepE"
					type="text" name="cep" value="${endereco.cep}" class="form-control"
					placeholder="Digite seu CEP (Exemplo: 00.000-000 - Somente os números)"
					maxlength="8">
				<button type="button" class="btn btn-primary"
					onclick="buscarEnderecoPorCep();">Buscar CEP</button>
			</div>

			<div class="col-md-6">
				<label for="logradouroE" class="form-label">Logradouro</label> <input
					id="logradouroE" type="text" name="logradouro"
					value="${endereco.logradouro}" class="form-control"
					placeholder="Digite seu Logradouro" maxlength="100">
			</div>

			<div class="col-md-6">
				<label for="complementoE" class="form-label">Complemento</label> <input
					id="complementoE" type="text" name="complemento"
					value="${endereco.complemento}" class="form-control"
					placeholder="Digite seu Complemento" maxlength="50">
			</div>

			<div class="col-md-6">
				<label for="localidadeE" class="form-label">Localidade
					(Cidade)</label> <input id="localidadeE" type="text" name="localidade"
					value="${endereco.localidade}" class="form-control"
					placeholder="Digite sua Localidade/Cidade (Exemplo: Sao Paulo)"
					maxlength="100">
			</div>

			<div class="col-md-6">
				<label for="ufE" class="form-label">UF (Estado)</label> <input
					id="ufE" type="text" name="uf" value="${endereco.uf}"
					class="form-control"
					placeholder="Digite seu UF/Estado (Exemplo: SP)" maxlength="2">
			</div>

			<div class="col-md-6">
				<label for="bairroE" class="form-label">Bairro</label> <input
					id="bairroE" type="text" name="bairro" value="${endereco.bairro}"
					class="form-control" placeholder="Digite seu Bairro" maxlength="50">
			</div>

			<div class="col-12 text-end">
				<button type="reset" class="btn btn-secondary">
					<i class="bi bi-x-circle"></i> Limpar Dados
				</button>
				<button type="submit" class="btn btn-success">
					<i class="bi bi-save"></i> Salvar Endereço
				</button>
				<a class="btn btn-success btn-sm" style="height: 37px"
					href="${pageContext.request.contextPath}/Endereco?acao=listarEndereco"
					title="Voltar"><i class="bi bi-pencil-square"></i> Voltar</a>
			</div>
		</form>
	</main>

	<footer class="p-3 bg-success text-white text-center py-3">
		<p>&copy; 2025 - JG Restaurante. Todos os direitos reservados.</p>
	</footer>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#telefoneC').mask('(99) 99999-9999');
			$('#cpfC').mask('999.999.999-99');
			$('#cepC').mask('99999999');

			$('#ufC').keypress(function(event) {
				var charCode = event.which || event.keyCode;
				// Verifica se a tecla pressionada é uma letra (A-Z ou a-z)
				if (!/[a-zA-Z]/.test(String.fromCharCode(charCode))) {
					event.preventDefault(); // Bloqueia números e caracteres especiais
				}
			});
		});
		
		function buscarEnderecoPorCep() {
		    var cep = document.getElementById('cepC').value;
		    
		    // Verifica se o CEP está preenchido e tem o formato correto (8 caracteres numéricos)
		    if (cep && cep.length === 8 && /^[0-9]{8}$/.test(cep)) {
		        // Requisição para a API ViaCep
		        fetch(`https://viacep.com.br/ws/` + cep + `/json/`)
		            .then(response => {
		                if (!response.ok) {
		                    throw new Error('Não foi possível acessar a API do ViaCep');
		                }
		                return response.json();
		            })
		            .then(data => {
		                // Preenche os campos com os dados da resposta
		                if (data.erro) {
		                    alert('CEP não encontrado.');
		                } else {
		                    document.getElementById('logradouroC').value = data.logradouro || '';
		                    document.getElementById('complementoC').value = data.complemento || '';
		                    document.getElementById('localidadeC').value = data.localidade || '';
		                    document.getElementById('ufC').value = data.uf || '';
		                    document.getElementById('bairroC').value = data.bairro || '';
		                    
		                    // Habilita os campos de endereço
		                    document.getElementById('logradouroC').disabled = false;
		                    document.getElementById('complementoC').disabled = false;
		                    document.getElementById('localidadeC').disabled = false;
		                    document.getElementById('ufC').disabled = false;
		                    document.getElementById('bairroC').disabled = false;
		                }
		            })
		            .catch(error => {
		                console.error('Erro:', error);
		                alert('Erro ao buscar dados do CEP.');
		            });
		    } else {
		        alert('Por favor, insira um CEP válido.');
		    }
		}
	</script>

	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>