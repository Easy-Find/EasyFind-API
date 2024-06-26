package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @Setter
    private boolean isAtivo;
    @Setter
    private boolean isPromocaoAtiva;
    private Integer qtdVendido;
    private Double taxaCompra;
    @ManyToOne
    @JoinColumn(name = "fk_secao")
    private Secao secao;

    public boolean getIsAtivo(){
        return this.isAtivo;
    }


    public boolean getIsPromocaoAtiva(){
        return this.isPromocaoAtiva;
    }

    @OneToMany
    @JoinColumn(name = "fk_estabelecimento")
    private List<MetodoPagamentoAceito> metodoPagamentoAceito;

    @OneToMany(mappedBy = "produto")
    private List<ProdutoTag> tags;

    @OneToMany(mappedBy = "produto")
    private List<Imagem> imagens;

    @OneToMany(mappedBy = "produto")
    private List<Avaliacao> avaliacoes;

    @OneToMany(mappedBy = "produto")
    private List<Carrinho> carrinhos;

    @OneToMany(mappedBy = "produto")
    private List<ItemVenda> itemVendas;

}
