package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.pedido.PedidoCriacaoDto;
import com.be.two.c.apibetwoc.dto.pedido.PedidoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoAceitoRepository;
import com.be.two.c.apibetwoc.repository.PedidoRepository;
import com.be.two.c.apibetwoc.util.ListaObj;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final SimpMessagingTemplate messagingTemplate;
    private final PedidoRepository pedidoRepository;
    private final MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;
    public void alterarStatusPedido(Long idPedido, String novoStatus) {
        Pedido pedido = pedidoRepository
                .findById(idPedido)
                .orElseThrow(
                        () -> new EntidadeNaoExisteException("O pedido informado não existe"));

        pedido.setStatusDescricao(novoStatus);
        StatusPedidoMessage mensagem = new StatusPedidoMessage();
        mensagem.setIdPedido(idPedido);
        mensagem.setNovoStatus(novoStatus);
        messagingTemplate.convertAndSend("/topic/statusPedido", mensagem);
    }

    public Pedido cadastrar(@Valid PedidoCriacaoDto pedidoCriacaoDto){
        Pedido pedido = PedidoMapper.of(pedidoCriacaoDto);
        MetodoPagamentoAceito metodoPagamentoAceito = metodoPagamentoAceitoRepository.findById(pedidoCriacaoDto.idMetodoPagamento())
                .orElseThrow(() -> new EntidadeNaoExisteException("Método de pagamento informado não existe"));
        pedido.setMetodoPagamentoAceito(metodoPagamentoAceito);
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(Long idPedido) {
        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
        if (pedido.isPresent()) {
            return pedido.get();
        }
        throw new EntidadeNaoExisteException("Pedido não encontrado");
    }

    public List<Pedido> listarPorConsumidor(Long idConsumidor) {
        return pedidoRepository.searchByConsumidor(idConsumidor);
    }

    public ListaObj<Pedido> listarPorEstabelecimento(Long idEstabelecimento) {
        List<Pedido> pedidos = pedidoRepository.searchByEstabelecimento(idEstabelecimento);
        ListaObj<Pedido> listaPedidos = new ListaObj<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            listaPedidos.adiciona(pedido);
        }

        return ordenacao(listaPedidos) ;

    }

    public List<Pedido> listarPorEstabelecimentoEStatus(Long idEstabelecimento, String status) {
        return pedidoRepository.searchByEstabelecimentoEStatus(idEstabelecimento, status);
    }

    public ListaObj<Pedido> ordenacao(ListaObj<Pedido> listaPedidos) {
        int tamanho = listaPedidos.getTamanho();

        for (int i = 0; i < tamanho - 1; i++) {
            int indiceMaximo = i;

            for (int j = i + 1; j < tamanho; j++) {
                if (listaPedidos.getElemento(j).getDataHoraPedido().isAfter(listaPedidos.getElemento(indiceMaximo).getDataHoraPedido())) {
                    indiceMaximo = j;
                }
            }

            if (indiceMaximo != i) {
                Pedido temp = listaPedidos.getElemento(i);
                listaPedidos.adiciona(listaPedidos.getElemento(indiceMaximo));
                listaPedidos.adiciona(temp);
            }
        }

        return listaPedidos;
    }

    public void deletar(Long id) {
    Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoExisteException("Pedido não encontrado"));
    pedido.setStatusDescricao("CANCELADO");
    }
}