package com.itb.inf3cm.pizzariabemmelhor.model.repository;


import com.itb.inf3cm.pizzariabemmelhor.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
