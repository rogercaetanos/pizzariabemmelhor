package com.itb.inf3cm.pizzariabemmelhor.model.services;


import com.itb.inf3cm.pizzariabemmelhor.exceptions.NotFound;
import com.itb.inf3cm.pizzariabemmelhor.model.entity.Cliente;
import com.itb.inf3cm.pizzariabemmelhor.model.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public Cliente findById(Long id) {

        if(!clienteRepository.findById(id).isPresent()){
            throw (new NotFound("Cliente não encontrado com o id " + id));
        }
        return clienteRepository.findById(id).get();

    }

}
