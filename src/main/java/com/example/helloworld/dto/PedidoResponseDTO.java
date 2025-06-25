package com.example.helloworld.dto;

import java.time.LocalDateTime;

public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime dataPedido;
    private String descricao;
    private Double valorTotal;
    private ClienteResponseDTO cliente; 

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public ClienteResponseDTO getCliente() { return cliente; }
    public void setCliente(ClienteResponseDTO cliente) { this.cliente = cliente; }
}