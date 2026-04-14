package com.itb.inf3cm.pizzariabemmelhor.model.repository;

import com.itb.inf3cm.pizzariabemmelhor.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
