package br.com.luccasoftware.api_dw.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ConcursoRepository extends CrudRepository<Concurso, Long> {

    @Query(value = "select a.id, a.\"idContratante\" as id_contratante, a.nome, a.descricao, a.\"dataInicioInscricao\" AS data_inicio_inscricao, a.\"dataFinalInscricao\"  AS data_final_inscricao, a.status, a.numero_edital, a.edital\n" +
            "from concurso a  order by a.\"idContratante\", a.id",
            nativeQuery = true)
    List<Concurso> findAllConcursos();
}

