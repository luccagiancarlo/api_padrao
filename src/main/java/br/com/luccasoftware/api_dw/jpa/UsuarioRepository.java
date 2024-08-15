package br.com.luccasoftware.api_dw.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query(value = "SELECT senha FROM usuario WHERE email = ?1", nativeQuery = true)
    String findPasswordByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM usuario WHERE email = ?1 AND senha = ?2 AND block = false", nativeQuery = true)
    int countByEmailAndSenhaAndNotBlock(String email, String senha);
}
