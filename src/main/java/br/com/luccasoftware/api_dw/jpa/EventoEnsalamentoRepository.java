package br.com.luccasoftware.api_dw.jpa;


import br.com.luccasoftware.api_dw.dto.Evento;
import br.com.luccasoftware.api_dw.dto.EventoEnsalamento;
import br.com.luccasoftware.api_dw.dto.EventoEnsalamentoCandidato;
import br.com.luccasoftware.api_dw.dto.EventoEnsalamentoLocal;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventoEnsalamentoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<EventoEnsalamento> findAll(String prefixo) {
        List<EventoEnsalamento> eventos = new ArrayList<>();
        String vsql = "select a.id_evento, a.descricao as concurso, b.id_local, b.id_cargo, count(b.inscricao) as tt_ensalado\n" +
                "from ensalamento a, ensalamento_candidato b where a.id = b.id_ensalamento and a.descricao = '"+prefixo+"' \n" +
                "group by a.id_evento, a.descricao, b.id_local, b.id_cargo" ;

        //System.out.println(vsql);

        Query q = entityManager.createNativeQuery(vsql);
        List<Object[]> resultList = q.getResultList();
        for (Object[] row : resultList) {
            EventoEnsalamento evento = new EventoEnsalamento();
            evento.setId_evento(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            evento.setConcurso(row[1] != null ? row[1].toString() : "");
            evento.setId_local(row[2] != null ? Long.parseLong(row[2].toString()) : 0L);
            evento.setId_cargo(row[3] != null ? Long.parseLong(row[3].toString()) : 0L);
            evento.setTt_ensalado(row[4] != null ? Long.parseLong(row[4].toString()) : 0L);

            eventos.add(evento);
        }

        return eventos;


    }

    public List<EventoEnsalamento> findAll() {
        List<EventoEnsalamento> eventos = new ArrayList<>();
        String vsql = "select a.id_evento, a.descricao as concurso, b.id_local, b.id_cargo, count(b.inscricao) as tt_ensalado\n" +
                "from ensalamento a, ensalamento_candidato b where a.id = b.id_ensalamento  \n" +
                "group by a.id_evento, a.descricao, b.id_local, b.id_cargo" ;

        //System.out.println(vsql);

        Query q = entityManager.createNativeQuery(vsql);
        List<Object[]> resultList = q.getResultList();
        for (Object[] row : resultList) {
            EventoEnsalamento evento = new EventoEnsalamento();
            evento.setId_evento(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            evento.setConcurso(row[1] != null ? row[1].toString() : "");
            evento.setId_local(row[2] != null ? Long.parseLong(row[2].toString()) : 0L);
            evento.setId_cargo(row[3] != null ? Long.parseLong(row[3].toString()) : 0L);
            evento.setTt_ensalado(row[4] != null ? Long.parseLong(row[4].toString()) : 0L);

            eventos.add(evento);
        }

        return eventos;


    }

    public List<EventoEnsalamentoCandidato> findAll(long id_evento, String id_local) {
        List<EventoEnsalamentoCandidato> can = new ArrayList<>();

        String prefixo = "";
        String idLocal = "";

        if (id_local.contains("-")) {
            String[] partes = id_local.split("-");

            prefixo = partes[0];
            idLocal = partes[1];

            String vsql = "select a.id_evento, a.descricao, b.inscricao, c.cargo, c.numero, c.cpf, trim(c.nome) as nome,  b.nome_cargo, b.periodo, b.id_local, d.escola, d.cidade\n" +
                    " from ensalamento a, ensalamento_candidato b, " + prefixo + "_candidato c, " + prefixo + "_local d\n" +
                    " where a.id = b.id_ensalamento and b.cpf = c.cpf and b.id_cargo = c.cargo and b.id_local = d.id\n" +
                    " and a.id_evento=" + id_evento + " and b.id_local=" + idLocal +
                    " order by trim(c.nome)";

            //System.out.println(vsql);

            Query q = entityManager.createNativeQuery(vsql);
            List<Object[]> resultList = q.getResultList();

                for (Object[] row : resultList) {
                    EventoEnsalamentoCandidato ent = new EventoEnsalamentoCandidato();

                    ent.setId_evento(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
                    ent.setDescricao(row[1] != null ? row[1].toString() : "");
                    ent.setInscricao(row[2] != null ? row[2].toString() : "");
                    ent.setId_cargo(row[3] != null ? Long.parseLong(row[3].toString()) : 0L);
                    ent.setNumero(row[4] != null ? Long.parseLong(row[4].toString()) : 0L);
                    ent.setCpf(row[5] != null ? row[5].toString() : "");
                    ent.setNome(row[6] != null ? row[6].toString() : "");
                    ent.setCargo(row[7] != null ? row[7].toString() : "");
                    ent.setPeriodo(row[8] != null ? row[8].toString() : "");
                    ent.setId_local(row[9] != null ? Long.parseLong(row[9].toString()) : 0L);
                    ent.setEscola(row[10] != null ? row[10].toString() : "");
                    ent.setCidade(row[11] != null ? row[11].toString() : "");

                    can.add(ent);

                }


        } else {

            String vsql = "select distinct b.edital from ensalamento a inner join ensalamento_candidato b on (a.id = b.id_ensalamento) where a.id_evento=" + id_evento;
            Query qConcurso = entityManager.createNativeQuery(vsql);

            List<Object> l = qConcurso.getResultList();
            prefixo = "";
            for (Object o : l) {
                prefixo = o.toString();


                 vsql = "select a.id_evento, a.descricao, b.inscricao, c.cargo, c.numero, c.cpf, trim(c.nome) as nome,  b.nome_cargo, b.periodo, b.id_local, d.escola, d.cidade\n" +
                        " from ensalamento a, ensalamento_candidato b, " + prefixo + "_candidato c, " + prefixo + "_local d\n" +
                        " where a.id = b.id_ensalamento and b.cpf = c.cpf and b.id_cargo = c.cargo and b.id_local = d.id\n" +
                        " and a.id_evento=" + id_evento +
                        " order by trim(c.nome)";

                //System.out.println(vsql);

                Query q = entityManager.createNativeQuery(vsql);
                List<Object[]> resultList = q.getResultList();
                for (Object[] row : resultList) {
                    EventoEnsalamentoCandidato ent = new EventoEnsalamentoCandidato();

                    ent.setId_evento(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
                    ent.setDescricao(row[1] != null ? row[1].toString() : "");
                    ent.setInscricao(row[2] != null ? row[2].toString() : "");
                    ent.setId_cargo(row[3] != null ? Long.parseLong(row[3].toString()) : 0L);
                    ent.setNumero(row[4] != null ? Long.parseLong(row[4].toString()) : 0L);
                    ent.setCpf(row[5] != null ? row[5].toString() : "");
                    ent.setNome(row[6] != null ? row[6].toString() : "");
                    ent.setCargo(row[7] != null ? row[7].toString() : "");
                    ent.setPeriodo(row[8] != null ? row[8].toString() : "");
                    ent.setId_local(row[9] != null ? Long.parseLong(row[9].toString()) : 0L);
                    ent.setEscola(row[10] != null ? row[10].toString() : "");
                    ent.setCidade(row[11] != null ? row[11].toString() : "");

                    can.add(ent);

                }
            }

        }

        if (can.size() == 0) {

            String vsql = "SELECT DISTINCT (dados_inscricao[1]) AS primeiro_valor\n" +
                    "FROM convocacao_negros_candidato \n" +
                    "WHERE id_convocacao =" + id_evento;
            Query qConcurso = entityManager.createNativeQuery(vsql);

            List<Object> l = qConcurso.getResultList();

            for (Object o : l) {
                prefixo = o.toString();

                vsql = "SELECT substring(cargos,1,length(cargos)-2) as cargos, substring(inscricoes,1,length(inscricoes)-2) as inscricoes, nome,cpf, cidade, local, sala, data_formatada, id FROM (\n" +
                        "SELECT c.nome, l.id, \n" +
                        "c.cpf,\n" +
                        "  CASE WHEN c.dados_inscricao [1] = '" + prefixo + "'\n" +
                        "    THEN c.dados_inscricao [3] || ' / ' ELSE '' END ||\n" +
                        "  CASE WHEN c.dados_inscricao [4] = '" + prefixo + "'\n" +
                        "    THEN c.dados_inscricao [6] || ' / ' ELSE '' END ||\n" +
                        "  CASE WHEN c.dados_inscricao [7] =  '" + prefixo + "'\n" +
                        "    THEN c.dados_inscricao [9] || ' / ' ELSE '' END ||\n" +
                        "  CASE WHEN c.dados_inscricao [10] =  '" + prefixo + "'\n" +
                        "    THEN c.dados_inscricao [12] || ' / ' ELSE '' END AS cargos,\n" +
                        "\n" +
                        "  CASE WHEN c.dados_inscricao [1] =  '" + prefixo + "'\n" +
                        "    THEN c.dados_inscricao [2] || ' / ' ELSE '' END ||\n" +
                        "  CASE WHEN c.dados_inscricao [4] =  '" + prefixo + "'\n" +
                        "    THEN c.dados_inscricao [5] || ' / ' ELSE '' END ||\n" +
                        "  CASE WHEN c.dados_inscricao [7] =  '" + prefixo + "'\n" +
                        "    THEN c.dados_inscricao [8] || ' / ' ELSE '' END ||\n" +
                        "  CASE WHEN c.dados_inscricao [10] =  '" + prefixo + "'\n" +
                        "    THEN c.dados_inscricao [11] || ' / ' ELSE '' END AS inscricoes,\n" +
                        "\n" +
                        "  cd.cidade,\n" +
                        "  l.nome AS local,\n" +
                        "  s.sala,\n" +
                        "  to_char(d.data_hora, 'dd/MM/yyyy HH24:mi') AS data_formatada\n" +
                        "FROM convocacao_negros_candidato c\n" +
                        "  INNER JOIN convocacao_negros_sala_data sd ON sd.id = c.id_sala_horario\n" +
                        "  INNER JOIN convocacao_negros_data d ON d.id = sd.id_data\n" +
                        "  INNER JOIN convocacao_negros_sala s ON s.id = sd.id_sala\n" +
                        "  INNER JOIN convocacao_negros_local l ON l.id = s.id_local\n" +
                        "  INNER JOIN convocacao_negros_cidade cd ON cd.id = l.id_cidade\n" +
                        " WHERE c.id_convocacao = " + id_evento + " AND l.id=" + idLocal +
                        " AND c.dados_inscricao @> ARRAY ['" + prefixo + "'] :: VARCHAR []\n" +
                        " ORDER BY cd.cidade, l.nome, c.sexo DESC, c.nome) as foo";


                Query q2 = entityManager.createNativeQuery(vsql);
                List<Object[]> r = q2.getResultList();
                if (r.size() > 0) {
                    for (Object[] row : r) {
                        EventoEnsalamentoCandidato ent = new EventoEnsalamentoCandidato();

                        ent.setId_evento(id_evento);
                        ent.setDescricao("Convocacao Geral " + id_evento);
                        ent.setInscricao(row[3] != null ? row[3].toString() : "");
                        ent.setId_cargo(0L);
                        ent.setNumero(0L);
                        ent.setCpf(row[3] != null ? row[3].toString() : "");
                        ent.setNome(row[2] != null ? row[2].toString() : "");
                        ent.setCargo(row[0] != null ? row[0].toString() : "");
                        ent.setPeriodo("");
                        ent.setId_local(row[8] != null ? Long.parseLong(row[8].toString()) : 0L);
                        ent.setEscola(row[5] != null ? row[5].toString() : "");
                        ent.setCidade(row[4] != null ? row[4].toString() : "");

                        can.add(ent);

                    }
                }
            }


        }

        return can;


    }

    public List<EventoEnsalamentoCandidato> buscarTodosCandidatos(long id_evento) {
        List<EventoEnsalamentoCandidato> can = new ArrayList<>();

        String vsql = "select a.id, a.descricao from ensalamento a where a.id_evento=" + id_evento;
        Query qConcurso = entityManager.createNativeQuery(vsql);

        List<Object[]> l = qConcurso.getResultList();
        String prefixo = "";
        for (Object[] o : l) {
            prefixo = o[1].toString() ;
        }


        vsql = "select a.id_evento, a.descricao, b.inscricao, c.cargo, c.numero, c.cpf, trim(c.nome) as nome,  b.nome_cargo, b.periodo, b.id_local, d.escola, d.cidade\n" +
                " from ensalamento a, ensalamento_candidato b, "+prefixo+"_candidato c, "+prefixo+"_local d\n" +
                " where a.id = b.id_ensalamento and b.cpf = c.cpf and b.id_cargo = c.cargo and b.id_local = d.id\n" +
                " and a.id_evento="+id_evento+
                " order by trim(c.nome)" ;

        //System.out.println(vsql);

        Query q = entityManager.createNativeQuery(vsql);
        List<Object[]> resultList = q.getResultList();
        for (Object[] row : resultList) {
            EventoEnsalamentoCandidato ent = new EventoEnsalamentoCandidato();

            ent.setId_evento(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            ent.setDescricao(row[1] != null ? row[1].toString() : "");
            ent.setInscricao(row[2] != null ? row[2].toString() : "");
            ent.setId_cargo(row[3] != null ? Long.parseLong(row[3].toString()) : 0L);
            ent.setNumero(row[4] != null ? Long.parseLong(row[4].toString()) : 0L);
            ent.setCpf(row[5] != null ? row[5].toString() : "");
            ent.setNome(row[6] != null ? row[6].toString() : "");
            ent.setCargo(row[7] != null ? row[7].toString() : "");
            ent.setPeriodo(row[8] != null ? row[8].toString() : "");
            ent.setId_local(row[9] != null ? Long.parseLong(row[9].toString()) : 0L);
            ent.setEscola(row[10] != null ? row[10].toString() : "");
            ent.setCidade(row[11] != null ? row[11].toString() : "");

            can.add(ent);

        }

        return can;


    }

    public List<EventoEnsalamentoLocal> findAll(long id_evento) {
        List<EventoEnsalamentoLocal> locals = new ArrayList<>();

        String vsql = "select distinct b.edital from ensalamento a inner join ensalamento_candidato b on (a.id = b.id_ensalamento) where a.id_evento=" + id_evento;
        Query qConcurso = entityManager.createNativeQuery(vsql);

        List<Object> l = qConcurso.getResultList();

        if (l.size() > 0) {
            String prefixo = "";
            for (Object o : l) {
                prefixo = o.toString();


                vsql = "select distinct b.id_local, d.cidade, d.escola\n" +
                        " from ensalamento a, ensalamento_candidato b, " + prefixo + "_candidato c, " + prefixo + "_local d\n" +
                        " where a.id = b.id_ensalamento and b.cpf = c.cpf and b.id_cargo = c.cargo and b.id_local = d.id\n" +
                        " and a.id_evento=" + id_evento +
                        " order by d.cidade, d.escola ";

                System.out.println(vsql);

                Query q = entityManager.createNativeQuery(vsql);
                List<Object[]> resultList = q.getResultList();
                for (Object[] row : resultList) {
                    EventoEnsalamentoLocal ent = new EventoEnsalamentoLocal();
                    ent.setId_local(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
                    ent.setCidade(row[1] != null ? row[1].toString() : "");
                    ent.setEscola(row[2] != null ? row[2].toString() : "");
                    ent.setEdital(prefixo);


                    locals.add(ent);
                }
            }
        } else {

            vsql = "select trim(both '{}' from concurso[1]) as concurso from convocacao_negros a where a.id=" + id_evento;
            qConcurso = entityManager.createNativeQuery(vsql);
            String concurso = (String) qConcurso.getSingleResult();

            vsql = "SELECT distinct cidade, local, id FROM (\n" +
                    "SELECT c.nome,\n" +
                    "c.cpf,\n" +
                    "  CASE WHEN c.dados_inscricao [1] = '"+concurso+"'\n" +
                    "    THEN c.dados_inscricao [3] || ' / ' ELSE '' END ||\n" +
                    "  CASE WHEN c.dados_inscricao [4] = '"+concurso+"'\n" +
                    "    THEN c.dados_inscricao [6] || ' / ' ELSE '' END ||\n" +
                    "  CASE WHEN c.dados_inscricao [7] =  '"+concurso+"'\n" +
                    "    THEN c.dados_inscricao [9] || ' / ' ELSE '' END ||\n" +
                    "  CASE WHEN c.dados_inscricao [10] =  '"+concurso+"'\n" +
                    "    THEN c.dados_inscricao [12] || ' / ' ELSE '' END AS cargos,\n" +
                    "\n" +
                    "  CASE WHEN c.dados_inscricao [1] =  '"+concurso+"'\n" +
                    "    THEN c.dados_inscricao [2] || ' / ' ELSE '' END ||\n" +
                    "  CASE WHEN c.dados_inscricao [4] =  '"+concurso+"'\n" +
                    "    THEN c.dados_inscricao [5] || ' / ' ELSE '' END ||\n" +
                    "  CASE WHEN c.dados_inscricao [7] =  '"+concurso+"'\n" +
                    "    THEN c.dados_inscricao [8] || ' / ' ELSE '' END ||\n" +
                    "  CASE WHEN c.dados_inscricao [10] =  '"+concurso+"'\n" +
                    "    THEN c.dados_inscricao [11] || ' / ' ELSE '' END AS inscricoes,\n" +
                    "\n" +
                    "  cd.cidade,\n" +
                    "  l.nome AS local,\n" +
                    "  l.id AS id,\n" +
                    "  s.sala,\n" +
                    "  to_char(d.data_hora, 'dd/MM/yyyy HH24:mi') AS data_formatada\n" +
                    "FROM convocacao_negros_candidato c\n" +
                    "  INNER JOIN convocacao_negros_sala_data sd ON sd.id = c.id_sala_horario\n" +
                    "  INNER JOIN convocacao_negros_data d ON d.id = sd.id_data\n" +
                    "  INNER JOIN convocacao_negros_sala s ON s.id = sd.id_sala\n" +
                    "  INNER JOIN convocacao_negros_local l ON l.id = s.id_local\n" +
                    "  INNER JOIN convocacao_negros_cidade cd ON cd.id = l.id_cidade\n" +
                    "WHERE c.id_convocacao = "+id_evento+" AND c.dados_inscricao @> ARRAY ['"+concurso+"'] :: VARCHAR []\n" +
                    "ORDER BY cd.cidade, l.nome, c.sexo DESC, c.nome) as foo";

            System.out.println(vsql);

            Query q = entityManager.createNativeQuery(vsql);
            List<Object[]> resultList = q.getResultList();
            long i = 1;
            for (Object[] row : resultList) {

                EventoEnsalamentoLocal ent = new EventoEnsalamentoLocal();
                ent.setId_local(row[2] != null ? Long.parseLong(row[2].toString()) : i);
                ent.setCidade(row[0] != null ? row[0].toString() : "");
                ent.setEscola(row[1] != null ? row[1].toString() : "");
                ent.setEdital(concurso);

                i++;

                locals.add(ent);
            }



        }

        return locals;


    }


}

