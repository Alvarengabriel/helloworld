package com.example.helloworld.controller;

import com.example.helloworld.dto.ClienteResponseDTO;
import com.example.helloworld.dto.PedidoRequestDTO;
import com.example.helloworld.dto.PedidoResponseDTO;
import com.example.helloworld.model.Cliente;
import com.example.helloworld.model.Pedido;
import com.example.helloworld.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        Pedido pedidoSalvo = pedidoService.criarPedido(pedidoDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pedidoSalvo.getId()).toUri();

        return ResponseEntity.created(location).body(toPedidoResponseDTO(pedidoSalvo));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        List<PedidoResponseDTO> dtos = pedidos.stream().map(this::toPedidoResponseDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(toPedidoResponseDTO(pedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizarPedido(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedidoDTO);
        return ResponseEntity.ok(toPedidoResponseDTO(pedidoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    private PedidoResponseDTO toPedidoResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setDescricao(pedido.getDescricao());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setCliente(toClienteResponseDTO(pedido.getCliente()));
        return dto;
    }

    private ClienteResponseDTO toClienteResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        return dto;
    }
}