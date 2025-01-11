package br.com.luccasoftware.api_dw.jpa;

import br.com.luccasoftware.api_dw.dto.Cargo;
import br.com.luccasoftware.api_dw.dto.Comercial;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ComercialRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<Comercial> buscarDados(int inicio, int fim) {

        String sqlConcursos = "select " +
                /* 00  */ " b.nome as prefixo, " +
                /* 01  */ " b.id as id_concurso, " +
                /* 02  */ " 'Instituto AOCP' as organizadora, " +
                /* 03  */ " a.sigla, " +
                /* 04  */ " a.nome as orgao, " +
                /* 05  */ " b.status, " +
                /* 06  */ " EXTRACT(YEAR FROM b.\"dataInicioInscricao\") as ano_inscricao,  " +
                /* 07  */ " a.cidade, " +
                /* 08  */ " a.estado " +
                " from contratante a, concurso b " +
                " where a.id = b.\"idContratante\"" +
                " and EXTRACT(YEAR FROM b.\"dataInicioInscricao\") between "+inicio+" and "+fim+" ORDER BY b.id";

        Query queryConcursos = entityManager.createNativeQuery(sqlConcursos);
        List<Object[]> concursos = queryConcursos.getResultList();
        List<Comercial> listaComercial = new ArrayList<>();

        DadosExtras extras = new DadosExtras();
        List<Object[]> dados = extras.dadosExtras();

        for (Object[] row : concursos) {

            Comercial com = new Comercial();
            try {
                com.setPrefixo(row[0].toString());
                com.setId_concurso(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
                com.setOrganizadora(row[2] != null ? row[2].toString() : "");
                com.setSigla(row[3] != null ? row[3].toString() : "");
                com.setOrgao(row[4] != null ? row[4].toString() : "");
                com.setStatus(row[5] != null ? row[5].toString() : "");
                com.setAno_inicio_ins(row[6] != null ? Integer.parseInt(row[6].toString()) : 0);
                com.setAno_inicio_po(row[6] != null ? Integer.parseInt(row[6].toString()) : 0);
                com.setCidade(row[7] != null ? row[7].toString() : "");
                com.setEstado(row[8] != null ? row[8].toString() : "");
                com.setEsfera(extras.buscarDados(dados, Integer.parseInt(row[1].toString()), 1));
                com.setTipo_concurso(extras.buscarDados(dados, Integer.parseInt(row[1].toString()), 2));
                com.setArea_concurso(extras.buscarDados(dados, Integer.parseInt(row[1].toString()), 3));
                com.setTt_ins(qde_inscritos(com.getPrefixo()));
                com.setTt_ins_homologados(qde_inscritos_homol(com.getPrefixo()));
                com.setTt_vagas(qde_vagas(com.getPrefixo()));
                com.setTt_cargos(qde_cargos(com.getPrefixo()));
                com.setTt_cidades_aplicacao(qde_cidade_provas(com.getPrefixo()));
                com.setTt_escolas_utilizadas(qde_escolas(com.getPrefixo()));
            } catch (Exception e) {
                com.setPrefixo("ERRO");
                com.setOrganizadora(e.getMessage());

            }




            listaComercial.add(com);

        }



        return listaComercial;
    }

    public List<Comercial> buscarDados(String prefixo) {

        String sqlConcursos = "select " +
                /* 00  */ " b.nome as prefixo, " +
                /* 01  */ " b.id as id_concurso, " +
                /* 02  */ " 'Instituto AOCP' as organizadora, " +
                /* 03  */ " a.sigla, " +
                /* 04  */ " a.nome as orgao, " +
                /* 05  */ " b.status, " +
                /* 06  */ " EXTRACT(YEAR FROM b.\"dataInicioInscricao\") as ano_inscricao,  " +
                /* 07  */ " a.cidade, " +
                /* 08  */ " a.estado " +
                " from contratante a, concurso b " +
                " where a.id = b.\"idContratante\"" +
                " and b.nome ='"+prefixo+"' ORDER BY b.id";

        Query queryConcursos = entityManager.createNativeQuery(sqlConcursos);
        List<Object[]> concursos = queryConcursos.getResultList();
        List<Comercial> listaComercial = new ArrayList<>();

        DadosExtras extras = new DadosExtras();
        List<Object[]> dados = extras.dadosExtras();

        for (Object[] row : concursos) {

            Comercial com = new Comercial();
            com.setPrefixo(row[0].toString());
            com.setId_concurso(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
            com.setOrganizadora(row[2] != null ? row[2].toString() : "");
            com.setSigla(row[3] != null ? row[3].toString() : "");
            com.setOrgao(row[4] != null ? row[4].toString() : "");
            com.setStatus(row[5] != null ? row[5].toString() : "");
            com.setAno_inicio_ins(row[6] != null ? Integer.parseInt(row[6].toString()) : 0);
            com.setAno_inicio_po(row[6] != null ? Integer.parseInt(row[6].toString()) : 0);
            com.setCidade(row[7] != null ? row[7].toString() : "");
            com.setEstado(row[8] != null ? row[8].toString() : "");
            com.setEsfera(extras.buscarDados(dados,Integer.parseInt(row[1].toString()),1));
            com.setTipo_concurso(extras.buscarDados(dados,Integer.parseInt(row[1].toString()),2));
            com.setArea_concurso(extras.buscarDados(dados,Integer.parseInt(row[1].toString()),3));
            com.setTt_ins(qde_inscritos(com.getPrefixo()));
            com.setTt_ins_homologados(qde_inscritos_homol(com.getPrefixo()));
            com.setTt_vagas(qde_vagas(com.getPrefixo()));
            com.setTt_cargos(qde_cargos(com.getPrefixo()));
            com.setTt_cidades_aplicacao(qde_cidade_provas(com.getPrefixo()));
            com.setTt_escolas_utilizadas(qde_escolas(com.getPrefixo()));


            listaComercial.add(com);

        }



        return listaComercial;
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

    private int qde_cargos (String prefixo) {
        int r = 0;
        String vsql = "select count(id) from " + prefixo + "_cargo a";
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

    private int qde_vagas (String prefixo) {
        int r = 0;
        String vsql = "select sum(vagas) from " + prefixo + "_cargo a";
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

    private int qde_cidade_provas (String prefixo) {
        int r = 0;
        String vsql = "select count(distinct a.cidade) from " + prefixo + "_local a";
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

    private int qde_escolas (String prefixo) {
        int r = 0;
        String vsql = "select count(distinct a.escola) from " + prefixo + "_local a";
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

    private int qde_inscritos (String prefixo) {
        int r = 0;
        String vsql = "select count(numero) from " + prefixo + "_candidato a where  (a.status='Pagamento confirmado' or a.status='Aguardando pagamento')";
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

    private int qde_inscritos_homol (String prefixo) {
        int r = 0;
        String vsql = "select count(numero) from " + prefixo + "_candidato a where  a.status='Pagamento confirmado' ";
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


