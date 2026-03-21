package com.itb.inf3cm.pizzariabemmelhor.model.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@DiscriminatorValue("ADMIN")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Admin extends Usuario {

    private String nivelAcesso;
}
