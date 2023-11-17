package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.agenda.AgendaMapper;
import com.be.two.c.apibetwoc.dto.estabelecimento.dto.AtualizarEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.estabelecimento.dto.CadastroEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.CoordenadaDto;
import com.be.two.c.apibetwoc.dto.estabelecimento.mapper.EstabelecimentoMapper;
import com.be.two.c.apibetwoc.dto.estabelecimento.dto.ResponseEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.secao.SecaoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.repository.AgendaRepository;
import com.be.two.c.apibetwoc.repository.ComercianteRepository;
import com.be.two.c.apibetwoc.repository.EstabelecimentoRepository;
import com.be.two.c.apibetwoc.repository.SecaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ComercianteRepository comercianteRepository;
    private final MetodoPagamentoAceitoService metodoPagamentoAceitoService;
    private final AgendaRepository agendaRepository;
    private final SecaoRepository secaoRepository;

    public Estabelecimento listarPorId(Long id){
        return estabelecimentoRepository
                .findById(id)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum estabelecimento com esse id"));
    }

    public ResponseEstabelecimentoDto listarPorId2(Long id){
        Estabelecimento estabelecimento = estabelecimentoRepository
                .findById(id)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum estabelecimento com esse id"));

        List<Agenda> agenda = agendaRepository.findByEstabelecimentoId(id);
        List<MetodoPagamentoAceito> metodos = metodoPagamentoAceitoService.findByEstabelecimentoId(id);

        return EstabelecimentoMapper.of(estabelecimento, agenda, metodos);
    }

    public List<Estabelecimento> listarTodos(){
        return estabelecimentoRepository.findAll();
    }

    public List<Estabelecimento> listarPorSegmento(String segmento){
        return estabelecimentoRepository.findBySegmento(segmento);
    }

    @Transactional
    public Estabelecimento cadastroEstabelecimento(CadastroEstabelecimentoDto estabelecimento){
        Comerciante comerciante = comercianteRepository
                .findById(estabelecimento.getIdComerciante())
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum comerciante com esse id"));

        Estabelecimento estabelecimentoCriado = estabelecimentoRepository.save(EstabelecimentoMapper.of(estabelecimento, comerciante));

        metodoPagamentoAceitoService.cadastrarMetodosPagamentos(estabelecimentoCriado, estabelecimento.getIdMetodoPagamento());

        agendaRepository.saveAll(AgendaMapper.of(estabelecimento.getAgenda(), estabelecimentoCriado));

        secaoRepository.saveAll(SecaoMapper.of(estabelecimento.getSecao(), estabelecimentoCriado));

        return estabelecimentoCriado;
    }

    public Estabelecimento atualizarEstabelecimento(AtualizarEstabelecimentoDto estabelecimentoDto, Long id){
        Estabelecimento estabelecimento = listarPorId(id);
        Estabelecimento estabelecimentoAtualizado = EstabelecimentoMapper.of(estabelecimentoDto, estabelecimento);
        estabelecimentoAtualizado.setId(id);
        return estabelecimentoRepository.save(estabelecimentoAtualizado) ;
    }

    public void deletar(Long id){
        if (!estabelecimentoRepository.existsById(id)){
            throw new EntidadeNaoExisteException("O estabelecimento procurado não existe.");
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.getReferenceById(id);
        estabelecimento.setIsAtivo(false);
        estabelecimentoRepository.save(estabelecimento);
    }


    public Long calcularRotaPessoa(CoordenadaDto coordenadaDto) {
        Pessoa pessoa = new Pessoa();

        return pessoa.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

    public Long calcularRotaBicicleta(CoordenadaDto coordenadaDto) {
        Bicicleta bicleta = new Bicicleta();

        return bicleta.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

    public Long calcularRotaCarro(CoordenadaDto coordenadaDto) {
        Carro carro = new Carro();

        return carro.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

}
