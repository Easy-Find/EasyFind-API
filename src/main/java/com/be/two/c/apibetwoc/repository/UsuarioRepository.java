package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndComercianteEstabelecimentoId(Long idUsuario, Long idEstabelecimento);
}
