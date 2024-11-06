package br.com.luccasoftware.api_dw.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppColetaFaseRepository extends JpaRepository<AppColetaFase, Integer> {

    // Método para buscar por ID (fornecido automaticamente pelo JpaRepository)
    Optional<AppColetaFase> findById(Integer id);

    // Método para buscar por nome parcial (ignora case sensitivity)
    @Query("SELECT a FROM AppColetaFase a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<AppColetaFase> findByNomeContainingIgnoreCase(@Param("nome") String nome);
}

