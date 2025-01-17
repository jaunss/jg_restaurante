package br.com.joaogcm.jg.restaurante.caseiro.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Pedido {

	private Integer codigo;
	private LocalDateTime dataPedido;
	private Cliente cliente;
	private BigDecimal subTotal;
	private BigDecimal total;
	private Set<Lanche> lanches = new HashSet<Lanche>();

	public Pedido() {

	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Set<Lanche> getLanches() {
		return lanches;
	}

	public void setLanches(Set<Lanche> lanches) {
		this.lanches = lanches;
	}
}