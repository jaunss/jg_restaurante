<nav class="navbar navbar-expand-lg">
	<div class="container-fluid">
		<a class="navbar-brand" href="#"><img class="id_jg_restaurante"
			src="${pageContext.request.contextPath}/assets/images/jg_restaurante.png"></a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Alternar navegação">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ms-auto">
				<li class="nav-item"><a
					class="nav-link ${acao == 'home' ? 'active' : ''}"
					href="${pageContext.request.contextPath}/Home?acao=home"><i
						class="bi bi-house"></i> Início</a></li>
				<li class="nav-item"><a
					class="nav-link ${acao == 'listarPedido' ? 'active' : ''}"
					href="${pageContext.request.contextPath}/Pedido?acao=listarPedido"><i
						class="bi bi-bag"></i> Pedido</a></li>
				<li class="nav-item"><a
					class="nav-link ${acao == 'listarLanche' ? 'active' : ''}"
					href="${pageContext.request.contextPath}/Lanche?acao=listarLanche"><i
						class="bi bi-egg-fried"></i> Lanche</a></li>
				<li class="nav-item"><a
					class="nav-link ${acao == 'listarCliente' ? 'active' : ''}"
					href="${pageContext.request.contextPath}/Cliente?acao=listarCliente"><i
						class="bi bi-people"></i> Cliente</a></li>
				<li class="nav-item"><a
					class="nav-link ${acao == 'listarCarrinho' ? 'active' : ''}"
					href="${pageContext.request.contextPath}/Carrinho?acao=listarCarrinho"><i
						class="bi bi-cart"></i> Carrinho</a></li>
				<li class="nav-item"><a
					class="nav-link ${acao == 'autenticar' ? 'active' : ''}"
					href="${pageContext.request.contextPath}/Autenticacao?acao=autenticar"><i
						class="bi bi-box-arrow-in-right"></i> Login</a></li>
			</ul>
		</div>
	</div>
</nav>