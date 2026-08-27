package com.itb.inf3cm.pizzariabemmelhor.model.services;

import com.itb.inf3cm.pizzariabemmelhor.exceptions.NotFound;
import com.itb.inf3cm.pizzariabemmelhor.model.entity.Usuario;
import com.itb.inf3cm.pizzariabemmelhor.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findByEmail(String email) {
        try {

            return this.usuarioRepository.findByEmail(email).get();

        }catch (Exception e){
            throw new NotFound("Usuário não encontrado com o email " +  email);
        }
    }

    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

}
