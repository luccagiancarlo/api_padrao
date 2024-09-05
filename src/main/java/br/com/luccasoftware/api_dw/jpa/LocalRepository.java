package br.com.luccasoftware.api_dw.jpa;

import br.com.luccasoftware.api_dw.dto.Cargo;
import br.com.luccasoftware.api_dw.dto.Local;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<Local> findAll(String prefixo) {
        String sql = "SELECT a.id as id_local, a.cidade, a.escola, a.horario as data_uso, \n" +
                " a.periodo, 0, 0, a.campos_adicionais " +
                "FROM " + prefixo + "_local a ORDER BY a.id";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();
        List<Local> locais = new ArrayList<>();

        for (Object[] row : resultList) {
            Local local = new Local();
            local.setConcurso(prefixo);
            local.setId_local(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            local.setCidade(row[1] != null ? row[1].toString() : "");
            local.setEscola(row[2] != null ? row[2].toString() : "");
            local.setData_uso(row[3] != null ? row[3].toString() : "");
            local.setPeriodo(row[4] != null ? row[4].toString() : "");
            local.setId_local_aplicacao_evento(row[5] != null ? Long.parseLong(row[5].toString()) : 0L);
            local.setCep(row[6] != null ? Long.parseLong(row[6].toString()) : 0L);
            local.setCampos_adicionais(row[7] != null ? row[7].toString() : "");

            locais.add(local);
        }

        return locais;
    }

    public List<Local> findAll(int inicio, int fim) {
        List<Local> locais = new ArrayList<>();
        String sqlConcursos = "SELECT a.nome FROM concurso a where EXTRACT(YEAR FROM a.\"dataInicioInscricao\") between "+inicio+" and "+fim+"   ORDER BY a.id";
        Query queryConcursos = entityManager.createNativeQuery(sqlConcursos);
        List<Object> prefixos = queryConcursos.getResultList();

        for (Object prefixo : prefixos) {
            try {
                String sql = "SELECT a.id as id_local, a.cidade, a.escola, a.horario as data_uso, \n" +
                        " a.periodo, a.id_local_aplicacao_evento, a.cep, a.campos_adicionais " +
                        "FROM " + prefixo.toString() + "_local a ORDER BY a.id";

                Query query = entityManager.createNativeQuery(sql);
                List<Object[]> resultList = query.getResultList();


                for (Object[] row : resultList) {
                    Local local = new Local();
                    local.setConcurso(prefixo.toString());
                    local.setId_local(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
                    local.setCidade(row[1] != null ? row[1].toString() : "");
                    local.setEscola(row[2] != null ? row[2].toString() : "");
                    local.setData_uso(row[3] != null ? row[3].toString() : "");
                    local.setPeriodo(row[4] != null ? row[4].toString() : "");
                    local.setId_local_aplicacao_evento(row[5] != null ? Long.parseLong(row[5].toString()) : 0L);
                    local.setCep(row[6] != null ? Long.parseLong(row[6].toString()) : 0L);
                    local.setCampos_adicionais(row[7] != null ? row[7].toString() : "");

                    locais.add(local);
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return locais;
    }


}


