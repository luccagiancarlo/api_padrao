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
        String prefixo = "";
        for (Object o : l) {
            prefixo = o.toString();


            vsql = "select distinct b.id_local, d.cidade, d.escola\n" +
                    " from ensalamento a, ensalamento_candidato b, " + prefixo + "_candidato c, " + prefixo + "_local d\n" +
                    " where a.id = b.id_ensalamento and b.cpf = c.cpf and b.id_cargo = c.cargo and b.id_local = d.id\n" +
                    " and a.id_evento=" + id_evento +
                    " order by d.cidade, d.escola ";

            //System.out.println(vsql);

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

        return locals;


    }


}

