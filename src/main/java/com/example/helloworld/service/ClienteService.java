package com.example.helloworld.service;

import com.example.helloworld.dto.ClienteRequestDTO;
import com.example.helloworld.model.Cliente;
import com.example.helloworld.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente criarCliente(ClienteRequestDTO clienteDTO) {
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(clienteDTO.getNome());
        novoCliente.setEmail(clienteDTO.getEmail());
        // A lista de pedidos começa vazia por padrão ao criar um novo cliente
        return clienteRepository.save(novoCliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado."));
    }

    public Cliente atualizarCliente(Long id, ClienteRequestDTO clienteDTO) {
        Cliente clienteExistente = buscarPorId(id); // Reutiliza a busca e o tratamento de erro
        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setEmail(clienteDTO.getEmail());
        return clienteRepository.save(clienteExistente);
    }

    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado.");
        }
        clienteRepository.deleteById(id);
    }
}