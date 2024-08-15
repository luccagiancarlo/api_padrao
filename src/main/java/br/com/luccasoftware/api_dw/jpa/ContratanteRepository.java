package br.com.luccasoftware.api_dw.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratanteRepository extends CrudRepository<Contratante, Long> {

    @Query(value = "SELECT a.id, a.nome, a.sigla, a.estado, a.cidade FROM contratante a ORDER BY a.id", nativeQuery = true)
    List<Contratante> findAllContratantes();
}

