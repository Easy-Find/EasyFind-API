package com.be.two.c.apibetwoc.service;
import com.be.two.c.apibetwoc.controller.comerciante.dto.ComercianteAtualizarDTO;
import com.be.two.c.apibetwoc.controller.comerciante.dto.ComercianteCriacaoDto;
import com.be.two.c.apibetwoc.controller.comerciante.mapper.ComercianteMapper;
import com.be.two.c.apibetwoc.controller.comerciante.dto.ResponseComercianteDto;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.model.TipoUsuario;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.ComercianteRepository;
import com.be.two.c.apibetwoc.service.endereco.EnderecoService;
import com.be.two.c.apibetwoc.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComercianteService {

    private final ComercianteRepository comercianteRepository;
    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;

    public Comerciante cadastrar(ComercianteCriacaoDto comercianteCriacaoDto){
        Usuario usuario = usuarioService.cadastrar(comercianteCriacaoDto.getUsuarioCriacaoDTO(), TipoUsuario.COMERCIANTE);
        Endereco endereco = enderecoService.cadastrar(comercianteCriacaoDto.getCep(),null);
        Comerciante comerciante = ComercianteMapper.of(comercianteCriacaoDto);
        comerciante.setUsuario(usuario);
        comerciante.setEndereco(endereco);
        return comercianteRepository.save(comerciante);
    }
    public Comerciante editar(ComercianteAtualizarDTO dto, Long id){
        Usuario usuario = usuarioService.editar(dto.getEmail(), id);
        Comerciante c = buscarPorId(usuario.getComerciante().getId());
        usuario.setTipoUsuario(TipoUsuario.COMERCIANTE);
        Endereco endereco = enderecoService.editar(dto,id);
        Comerciante comerciante = ComercianteMapper.of(dto);
        comerciante.setUsuario(usuario);
        comerciante.setEndereco(endereco);
        comerciante.setIsAtivo(true);
        comerciante.setDataCriacao(c.getDataCriacao());
        comerciante.setId(id);

        return comercianteRepository.save(comerciante);
    }
    public List<ResponseComercianteDto> listar(){
        return comercianteRepository
                .findAll()
                .stream()
                .map(ComercianteMapper :: of)
                .toList();
    }
    public Comerciante buscarPorId(Long id){
        Comerciante comerciante = comercianteRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Comerciante não encontrado")
        );

       return comerciante;
    }
    public void excluir(Long id){
        if(!comercianteRepository.existsById(id)){
            throw new EntidadeNaoExisteException("O comerciante procurado não existe.");
        }
        Comerciante comerciante = comercianteRepository.getReferenceById(id);
        comerciante.setIsAtivo(false);
        comercianteRepository.save(comerciante);
    }
}
