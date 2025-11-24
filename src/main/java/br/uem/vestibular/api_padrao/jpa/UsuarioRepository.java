package br.uem.vestibular.api_padrao.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    // Busca usuário completo por email
    @Query(value = "SELECT * FROM SGV.SGV_USUARIO WHERE EN_EMAIL = ?1", nativeQuery = true)
    Optional<Usuario> findByEmail(String email);

    // Busca senha (sem criptografia) por email
    @Query(value = "SELECT SE_USUARIO FROM SGV.SGV_USUARIO WHERE EN_EMAIL = ?1", nativeQuery = true)
    String findPasswordByEmail(String email);

    // Valida credenciais (comparação direta de senha em texto plano)
    @Query(value = "SELECT COUNT(*) FROM SGV.SGV_USUARIO WHERE EN_EMAIL = ?1 AND SE_USUARIO = ?2", nativeQuery = true)
    int countByEmailAndSenha(String email, String senha);

}
