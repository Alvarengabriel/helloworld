package com.example.helloworld.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class PedidoRequestDTO {

    private LocalDateTime dataPedido = LocalDateTime.now();

    @NotBlank(message = "A descrição não pode estar em branco.")
    private String descricao;

    @NotNull(message = "O valor total não pode ser nulo.")
    @Positive(message = "O valor total deve ser positivo.")
    private Double valorTotal;

    @NotNull(message = "O ID do cliente não pode ser nulo.")
    private Long clienteId;

    // Getters e Setters
    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}