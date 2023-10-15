package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class Comerciante {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cnpj;
    private String nome;
    private LocalDate dataCriacao;
    private LocalDate dataUltimoAcesso;
    private String razaoSocial;
    @OneToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name = "fk_endereco")
    private Endereco endereco;
}