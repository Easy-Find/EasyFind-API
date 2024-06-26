package com.be.two.c.apibetwoc.service.usuario;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioCriacaoDTO;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioLoginDTO;
import com.be.two.c.apibetwoc.controller.usuario.mapper.UsuarioMapper;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioTokenDTO;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.infra.security.jwt.GerenciadorTokenJwt;
import com.be.two.c.apibetwoc.model.TipoUsuario;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.UsuarioRepository;
import com.be.two.c.apibetwoc.service.usuario.exception.UsuarioConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Usuario cadastrar(UsuarioCriacaoDTO usuarioCriacaoDTO, TipoUsuario tipoUsuario){
        if(repository.existsByEmail(usuarioCriacaoDTO.email())) throw new UsuarioConflictException("Email já cadastrado no sistema");
        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDTO);
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setTipoUsuario(tipoUsuario);
        return repository.save(novoUsuario);
    }
    public UsuarioTokenDTO autenticar(UsuarioLoginDTO usuarioLoginDTO){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDTO.getEmail(), usuarioLoginDTO.getSenha());
        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                repository.findByEmail(usuarioLoginDTO.getEmail()).orElseThrow(
                        () -> new EntidadeNaoExisteException("Usuário não encontrado"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }
    public Usuario editar(String email, Long id){
        Usuario existeUsuario = repository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Usuário não existe")
        );
        existeUsuario.setEmail(email);
        existeUsuario.setId(id);
        return repository.save(existeUsuario);
    }

    public Usuario buscarPorId(Long id){
        return repository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Usuário não existe")
        );
    }
}
