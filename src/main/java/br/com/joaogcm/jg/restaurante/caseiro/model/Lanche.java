package br.com.joaogcm.jg.restaurante.caseiro.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Lanche {

	private Integer codigo;
	private String nome;
	private String descricao_conteudo;
	private BigDecimal preco;
	private Set<Pedido> pedidos = new HashSet<Pedido>();

	public Lanche() {

	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao_conteudo() {
		return descricao_conteudo;
	}

	public void setDescricao_conteudo(String descricao_conteudo) {
		this.descricao_conteudo = descricao_conteudo;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Set<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Set<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
}