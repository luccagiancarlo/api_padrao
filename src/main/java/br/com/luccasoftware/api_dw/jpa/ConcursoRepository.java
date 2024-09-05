package br.com.luccasoftware.api_dw.jpa;

import br.com.luccasoftware.api_dw.dto.Concurso;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class ConcursoRepository  {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<Concurso> findAllConcursos(int inicio, int fim) {
        List<Concurso> concursos = new ArrayList<>();
        String sqlConcursos = "select a.id as id_concurso, a.\"idContratante\" as id_contratante, a.nome, a.descricao, a.\"dataInicioInscricao\", a.\"dataFinalInscricao\", a.status, a.numero_edital FROM concurso a where EXTRACT(YEAR FROM a.\"dataInicioInscricao\") between "+inicio+" and "+fim+"   ORDER BY a.id";
        Query queryConcursos = entityManager.createNativeQuery(sqlConcursos);
        List<Object[]> resultList = queryConcursos.getResultList();
        for (Object[] row : resultList) {
            Concurso concurso = new Concurso();
            concurso.setId(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            concurso.setIdContratante(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
            concurso.setNome(row[2] != null ? row[2].toString() : "");
            concurso.setDescricao(row[3] != null ? row[3].toString() : "");
            concurso.setDataInicioInscricao(row[4] != null ? row[4].toString() : "");
            concurso.setDataFinalInscricao(row[5] != null ? row[5].toString() : "");
            concurso.setStatus(row[6] != null ? row[6].toString() : "");
            concurso.setNumeroEdital(row[7] != null ? row[7].toString() : "");
            concurso.setEdital(row[2] != null ? row[2].toString() : "");
            concursos.add(concurso);
        }

        return concursos;


    }
}

