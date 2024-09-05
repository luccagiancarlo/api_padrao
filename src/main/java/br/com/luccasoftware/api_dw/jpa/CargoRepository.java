package br.com.luccasoftware.api_dw.jpa;

import br.com.luccasoftware.api_dw.dto.Cargo;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CargoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<Cargo> findAllCargos(String prefixo) {
        String sql = "SELECT a.id, a.numero, a.nome, a.sigla, a.cidade, a.vagas, a.vagaspne, a.vagas_afro " +
                "FROM " + prefixo + "_cargo a ORDER BY a.id";

        if (!databaseUtils.existeColuna(prefixo + "_cargo", "vagas_afro")) {
            sql = "SELECT a.id, a.numero, a.nome, a.sigla, a.cidade, a.vagas, a.vagaspne, 0 as vagas_afro " +
                    "FROM " + prefixo + "_cargo a ORDER BY a.id";
        }

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();
        List<Cargo> cargos = new ArrayList<>();

        for (Object[] row : resultList) {
            Cargo cargo = new Cargo();
            cargo.setPrefixo(prefixo);
            cargo.setId_cargo(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            cargo.setNumero(row[1] != null ? row[1].toString() : "");
            cargo.setNome(row[2] != null ? row[2].toString() : "");
            cargo.setSigla(row[3] != null ? row[3].toString() : "");
            cargo.setCidade(row[4] != null ? row[4].toString() : "");
            cargo.setVagas(row[5] != null ? Integer.parseInt(row[5].toString()) : 0);
            cargo.setVagaspne(row[6] != null ? Integer.parseInt(row[6].toString()) : 0);
            cargo.setVagasAfro(row[7] != null ? Integer.parseInt(row[7].toString()) : 0);

            cargos.add(cargo);
        }

        return cargos;
    }

    public List<Cargo> findAllCargos(int inicio, int fim) {
        List<Cargo> cargos = new ArrayList<>();
        String sqlConcursos = "SELECT a.nome FROM concurso a where EXTRACT(YEAR FROM a.\"dataInicioInscricao\") between "+inicio+" and "+fim+"   ORDER BY a.id";
        Query queryConcursos = entityManager.createNativeQuery(sqlConcursos);
        List<Object> prefixos = queryConcursos.getResultList();

        for (Object prefixo : prefixos) {
            try {
                String prefixoStr = prefixo.toString();
                String sql = "SELECT a.id, a.numero, a.nome, a.sigla, a.cidade, a.vagas, a.vagaspne, a.vagas_afro " +
                        "FROM " + prefixoStr + "_cargo a ORDER BY a.id";

                if (!databaseUtils.existeColuna(prefixoStr + "_cargo", "vagas_afro")) {
                    sql = "SELECT a.id, a.numero, a.nome, a.sigla, a.cidade, a.vagas, a.vagaspne, 0 as vagas_afro " +
                            "FROM " + prefixoStr + "_cargo a ORDER BY a.id";
                }

                Query query = entityManager.createNativeQuery(sql);
                List<Object[]> resultList = query.getResultList();

                for (Object[] row : resultList) {
                    Cargo cargo = new Cargo();
                    cargo.setPrefixo(prefixoStr);
                    cargo.setId_cargo(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
                    cargo.setNumero(row[1] != null ? row[1].toString() : "");
                    cargo.setNome(row[2] != null ? row[2].toString() : "");
                    cargo.setSigla(row[3] != null ? row[3].toString() : "");
                    cargo.setCidade(row[4] != null ? row[4].toString() : "");
                    cargo.setVagas(row[5] != null ? Integer.parseInt(row[5].toString()) : 0);
                    cargo.setVagaspne(row[6] != null ? Integer.parseInt(row[6].toString()) : 0);
                    cargo.setVagasAfro(row[7] != null ? Integer.parseInt(row[7].toString()) : 0);
                    int tt_ins = qde_inscritos(prefixoStr, row[0].toString());
                    int tt_ins_homol = qde_inscritos_homol(prefixoStr, row[0].toString());
                    int tt_afro = qde_inscritos_afro(prefixoStr, row[0].toString());
                    int tt_pcd = qde_inscritos_pcd(prefixoStr, row[0].toString());
                    int tt_isentos = qde_inscritos_isentos(prefixoStr, row[0].toString());

                    cargo.setTt_ins(tt_ins);
                    cargo.setTt_ins_homologados(tt_ins_homol);
                    cargo.setTt_ins_afro(tt_afro);
                    cargo.setTt_ins_pcd(tt_pcd);
                    cargo.setTt_ins_isentos(tt_isentos);



                    cargos.add(cargo);
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        return cargos;
    }

    private int qde_inscritos (String prefixo, String id_cargo) {
        int r = 0;
        String vsql = "select count(numero) from " + prefixo + "_candidato a where a.cargo="+id_cargo+" and (a.status='Pagamento confirmado' or a.status='Aguardando pagamento')";
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

    private int qde_inscritos_homol (String prefixo, String id_cargo) {
        int r = 0;
        String vsql = "select count(numero) from " + prefixo + "_candidato a where a.cargo="+id_cargo+" and a.status='Pagamento confirmado' ";
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

    private int qde_inscritos_afro (String prefixo, String id_cargo) {
        int r = 0;
        String vsql = "select count(numero) from " + prefixo + "_candidato a where a.cargo="+id_cargo+" and a.status='Pagamento confirmado' and a.afro='sim'";
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

    private int qde_inscritos_pcd (String prefixo, String id_cargo) {
        int r = 0;
        String vsql = "select count(numero) from " + prefixo + "_candidato a where a.cargo="+id_cargo+" and a.status='Pagamento confirmado' and a.tipo_def !='NENHUMA' and a.enviou_laudo";
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

    private int qde_inscritos_isentos (String prefixo, String id_cargo) {
        int r = 0;
        String vsql = "select count(numero) from " + prefixo + "_candidato a where a.cargo="+id_cargo+" and a.status='Pagamento confirmado' and a.isento";
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


