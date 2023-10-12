package com.be.two.c.apibetwoc.dto;

import com.be.two.c.apibetwoc.model.Secao;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroProdutoDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String codigoSku;
    @Positive
    private Double preco;
    @NotBlank
    private String descricao;
    @NotNull
    private Double precoOferta;
    @NotBlank
    private String codigoBarras;
    @NotBlank
    private String categoria;
    @NotNull
    private boolean isAtivo;
    @NotNull
    private boolean isPromocaoAtiva;
    @NotNull
    private Long secao;
    @NotNull
    private Long tag;
}