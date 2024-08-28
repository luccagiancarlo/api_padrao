package br.com.luccasoftware.api_dw.jpa;

import br.com.luccasoftware.api_dw.dto.Local;
import br.com.luccasoftware.api_dw.dto.ResultadoCargo;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResultadoCargoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<ResultadoCargo> findAll(String prefixo) {
        String sql = "select b.cargo, count(b.numero)from "+prefixo+"_res a, "+prefixo+"_candidato b where a.numero = b.numero " +
                " group by b.cargo";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();
        List<ResultadoCargo> resultados = new ArrayList<>();

        for (Object[] row : resultList) {
            ResultadoCargo res = new ResultadoCargo();
            res.setConcurso(prefixo);
            res.setId_cargo(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            res.setTt_candidatos(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
            res.setTt_aprovados(qde_candidatos(prefixo, row[0].toString(),"APROVADO%"));
            res.setTt_eliminados(qde_candidatos(prefixo, row[0].toString(),"ELIMINADO%"));
            res.setTt_ausentes(qde_candidatos(prefixo, row[0].toString(),"AUSENTE%"));
            res.setTt_presentes(res.getTt_candidatos() - res.getTt_ausentes());

            resultados.add(res);
        }

        return resultados;
    }

    public List<ResultadoCargo> findAll(int inicio) {
        List<ResultadoCargo> resultados = new ArrayList<>();
        String sqlConcursos = "SELECT a.nome FROM concurso a where EXTRACT(YEAR FROM a.\"dataInicioInscricao\") >="+inicio+"   ORDER BY a.id";
        Query queryConcursos = entityManager.createNativeQuery(sqlConcursos);
        List<Object> prefixos = queryConcursos.getResultList();

        for (Object prefixo : prefixos) {
            try {
                String sql = "select b.cargo, count(b.numero)from "+prefixo+"_res a, "+prefixo+"_candidato b where a.numero = b.numero " +
                        " group by b.cargo";

                Query query = entityManager.createNativeQuery(sql);
                List<Object[]> resultList = query.getResultList();

                for (Object[] row : resultList) {
                    ResultadoCargo res = new ResultadoCargo();
                    res.setConcurso(prefixo.toString());
                    res.setId_cargo(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
                    res.setTt_candidatos(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
                    res.setTt_aprovados(qde_candidatos(prefixo.toString(), row[0].toString(),"APROVADO%"));
                    res.setTt_eliminados(qde_candidatos(prefixo.toString(), row[0].toString(),"ELIMINADO%"));
                    res.setTt_ausentes(qde_candidatos(prefixo.toString(), row[0].toString(),"AUSENTE%"));
                    res.setTt_presentes(res.getTt_candidatos() - res.getTt_ausentes());

                    resultados.add(res);
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return resultados;
    }

    private int qde_candidatos (String prefixo, String id_cargo, String situacao) {
        int r = 0;
        String vsql = "select  count(b.numero)from "+prefixo+"_res a, "+prefixo+"_candidato b where a.numero = b.numero and b.cargo="+id_cargo+" and a.resultado like '"+situacao+"%'";
        Query query = entityManager.createNativeQuery(vsql);
        List<Object> q = query.getResultList();
        for (Object o : q) {
            try {
                r = Integer.parseInt(o.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return r;
    }


}


