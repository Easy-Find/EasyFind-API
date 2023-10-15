package com.be.two.c.apibetwoc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.util.List;
@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String codigoSku;
    private Double preco;
    private String descricao;
    private Double precoOferta;
    private String codigoBarras;
    private String categoria;
    private Boolean isAtivo;
    private Boolean isPromocaoAtiva;
    private Integer qtdVendido;
    @ManyToOne
    @JoinColumn(name = "fk_secao")
    private Secao secao;

    public Produto(Long id, String nome, String codigoSku, Double preco, String descricao, Double precoOferta, String codigoBarras, String categoria, Boolean isAtivo, Boolean isPromocaoAtiva, Secao secao) {
        this.id = id;
        this.nome = nome;
        this.codigoSku = codigoSku;
        this.preco = preco;
        this.descricao = descricao;
        this.precoOferta = precoOferta;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.isAtivo = isAtivo;
        this.isPromocaoAtiva = isPromocaoAtiva;
        this.secao = secao;
    }
}