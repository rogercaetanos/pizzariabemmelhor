package com.itb.inf3cm.pizzariabemmelhor.model.repository;

import com.itb.inf3cm.pizzariabemmelhor.model.entity.Cliente;
import com.itb.inf3cm.pizzariabemmelhor.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    @Query(value = "SELECT * FROM usuario u WHERE u.id=?1 AND u.cod_status='1' AND tipo_usuario='CLIENTE'", nativeQuery = true)
    Optional<Cliente> findById(Long id);


    @Query(value = "SELECT p FROM Pedido p JOIN FETCH p.cliente c WHERE c.id= :id")
    public List<Pedido> findAllPedidosByClienteId(@Param("id") Long clienteId);

}
