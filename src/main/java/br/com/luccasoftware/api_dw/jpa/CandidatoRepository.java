package br.com.luccasoftware.api_dw.jpa;

import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidatoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<Candidato> findAllCandidatos(String prefixo) {
        List<Candidato> candidatos = new ArrayList<>();

        String sql = "SELECT a.numero as id_candidato, a.cargo as id_cargo, a.nome, a.sexo, a.nascimento, a.cep, a.cidade, a.estado, a.status, " +
                "a.tipo_def, a.afro, a.solicitou_inscricao_negro " +
                "FROM " + prefixo + "_candidato a ORDER BY a.numero";

        if (!databaseUtils.existeColuna(prefixo + "_candidato", "solicitou_inscricao_negro")) {
            sql = "SELECT a.numero as id_candidato, a.cargo as id_cargo, a.nome, a.sexo, a.nascimento, a.cep, a.cidade, a.estado, a.status, " +
                    "a.tipo_def, false, false " +
                    "FROM " + prefixo + "_candidato a ORDER BY a.numero";
        }

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();

        for (Object[] row : resultList) {
            Candidato candidato = new Candidato();
            candidato.setPrefixo(prefixo);
            candidato.setIdCandidato(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            candidato.setIdCargo(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
            candidato.setNome(row[2] != null ? row[2].toString() : "");
            candidato.setSexo(row[3] != null ? row[3].toString() : "");
            candidato.setNascimento(row[4] != null ? row[4].toString() : "");
            candidato.setCep(row[5] != null ? row[5].toString() : "");
            candidato.setCidade(row[6] != null ? row[6].toString() : "");
            candidato.setEstado(row[7] != null ? row[7].toString() : "");
            candidato.setStatus(row[8] != null ? row[8].toString() : "");
            candidato.setTipoDef(row[9] != null ? row[9].toString() : "unknown");
            candidato.setAfro(row[10] != null ? Boolean.parseBoolean(row[10].toString()) : false);
            candidato.setSolicitouInscricaoNegro(row[11] != null ? Boolean.parseBoolean(row[11].toString()) : false);
            candidatos.add(candidato);
        }

        return candidatos;
    }

    public List<Candidato> findAllCandidatos() {
        List<Candidato> candidatos = new ArrayList<>();
        String sqlConcursos = "SELECT a.nome FROM concurso a ORDER BY a.id";
        Query queryConcursos = entityManager.createNativeQuery(sqlConcursos);
        List<Object> prefixos = queryConcursos.getResultList();

        for (Object prefixo : prefixos) {
            try {
                String prefixoStr = prefixo.toString();
                String sql = "SELECT a.numero as id_candidato, a.cargo as id_cargo, a.nome, a.sexo, a.nascimento, a.cep, a.cidade, a.estado, a.status, " +
                        "a.tipo_def, a.afro, a.solicitou_inscricao_negro " +
                        "FROM " + prefixoStr + "_candidato a ORDER BY a.numero";

                if (!databaseUtils.existeColuna(prefixoStr + "_candidato", "solicitou_inscricao_negro")) {
                    sql = "SELECT a.numero as id_candidato, a.cargo as id_cargo, a.nome, a.sexo, a.nascimento, a.cep, a.cidade, a.estado, a.status, " +
                            "a.tipo_def, false, false " +
                            "FROM " + prefixoStr + "_candidato a ORDER BY a.numero";
                }

                Query query = entityManager.createNativeQuery(sql);
                List<Object[]> resultList = query.getResultList();

                for (Object[] row : resultList) {
                    Candidato candidato = new Candidato();
                    candidato.setPrefixo(prefixoStr);
                    candidato.setIdCandidato(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
                    candidato.setIdCargo(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
                    candidato.setNome(row[2] != null ? row[2].toString() : "");
                    candidato.setSexo(row[3] != null ? row[3].toString() : "");
                    candidato.setNascimento(row[4] != null ? row[4].toString() : "");
                    candidato.setCep(row[5] != null ? row[5].toString() : "");
                    candidato.setCidade(row[6] != null ? row[6].toString() : "");
                    candidato.setEstado(row[7] != null ? row[7].toString() : "");
                    candidato.setStatus(row[8] != null ? row[8].toString() : "");
                    candidato.setTipoDef(row[9] != null ? row[9].toString() : "unknown");
                    candidato.setAfro(row[10] != null ? Boolean.parseBoolean(row[10].toString()) : false);
                    candidato.setSolicitouInscricaoNegro(row[11] != null ? Boolean.parseBoolean(row[11].toString()) : false);
                    candidatos.add(candidato);
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return candidatos;
    }
}

