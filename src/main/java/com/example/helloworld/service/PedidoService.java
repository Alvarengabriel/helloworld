package com.example.helloworld.service;

import com.example.helloworld.dto.PedidoRequestDTO;
import com.example.helloworld.model.Cliente;
import com.example.helloworld.model.Pedido;
import com.example.helloworld.repository.ClienteRepository;
import com.example.helloworld.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Pedido criarPedido(PedidoRequestDTO pedidoDTO) {
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + pedidoDTO.getClienteId() + " não encontrado."));

        Pedido novoPedido = new Pedido();
        novoPedido.setDataPedido(pedidoDTO.getDataPedido());
        novoPedido.setDescricao(pedidoDTO.getDescricao());
        novoPedido.setValorTotal(pedidoDTO.getValorTotal());
        novoPedido.setCliente(cliente);

        return pedidoRepository.save(novoPedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + id + " não encontrado."));
    }

    public Pedido atualizarPedido(Long id, PedidoRequestDTO pedidoDTO) {
        Pedido pedidoExistente = buscarPorId(id); 

        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + pedidoDTO.getClienteId() + " não encontrado."));

        pedidoExistente.setDescricao(pedidoDTO.getDescricao());
        pedidoExistente.setValorTotal(pedidoDTO.getValorTotal());
        pedidoExistente.setCliente(cliente);
        return pedidoRepository.save(pedidoExistente);
    }

    public void deletarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido com ID " + id + " não encontrado.");
        }
        pedidoRepository.deleteById(id);
    }
}