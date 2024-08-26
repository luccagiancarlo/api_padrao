package br.com.luccasoftware.api_dw.jpa;


import br.com.luccasoftware.api_dw.dto.Evento;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<Evento> findAll(String prefixo) {
        List<Evento> eventos = new ArrayList<>();
        String vsql = "select distinct c.id_evento, c.descricao, c.status, c.valor_base, c.valor_lanche,\n " +
                "                c.sobra_lanche, c.id_organizadora, c.tipo_evento, c.id_projeto_icode,\n " +
                "                c.id_item_projeto_evento_icode, c.id_item_projeto_locacao_icode, c.id_item_projeto_lanche_icode\n " +
                "from ensalamento_candidato a,\n " +
                prefixo + "_local b join dblink('dbname=colaborador host=localhost user=institutoaocp password=351016',\n" +
                "                             'select j.id, j.id_evento, k.descricao, k.status,  k.valor_base, k.valor_lanche, k.sobra_lanche,\n" +
                "                                     k.id_organizadora, k.tipo_evento, k.id_projeto_icode, k.id_item_projeto_evento_icode,\n" +
                "                                     k.id_item_projeto_locacao_icode, k.id_item_projeto_lanche_icode\n" +
                "                              from local_aplicacao_evento j, evento k where j.id_evento = k.id')\n" +
                "    AS c(id int, id_evento int, descricao text, status text, valor_base decimal, valor_lanche decimal, sobra_lanche int,\n" +
                "        id_organizadora int, tipo_evento text, id_projeto_icode int, id_item_projeto_evento_icode int, id_item_projeto_locacao_icode int, id_item_projeto_lanche_icode int\n" +
                "        ) on (b.id_local_aplicacao_evento = c.id)\n" +
                " where a.id_local = b.id ";

        Query queryConcursos = entityManager.createNativeQuery(vsql);
        List<Object[]> resultList = queryConcursos.getResultList();
        for (Object[] row : resultList) {
            Evento evento = new Evento();
            evento.setId(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            evento.setDescricao(row[1] != null ? row[1].toString() : "");
            evento.setStatus(row[2] != null ? row[2].toString() : "");
            evento.setValorBase(row[3] != null ? Double.parseDouble(row[3].toString()) : 0D);
            evento.setValorLanche(row[4] != null ? Double.parseDouble(row[4].toString()) : 0D);
            evento.setSobraLanche(row[5] != null ? Double.parseDouble(row[5].toString()) : 0D);
            evento.setIdOrganizadora(row[6] != null ? Long.parseLong(row[6].toString()) : 0L);
            evento.setTipoEvento(row[7] != null ? row[7].toString() : "");
            evento.setIdProjetoIcode(row[8] != null ? Long.parseLong(row[8].toString()) : 0L);
            evento.setIdItemProjetoEventoIcode(row[9] != null ? Long.parseLong(row[9].toString()) : 0L);
            evento.setIdItemProjetoLocacaoIcode(row[10] != null ? Long.parseLong(row[10].toString()) : 0L);
            evento.setIdItemProjetoLancheIcode(row[11] != null ? Long.parseLong(row[11].toString()) : 0L);


            eventos.add(evento);
        }

        return eventos;


    }
}

