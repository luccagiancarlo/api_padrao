package br.com.luccasoftware.api_dw.jpa;



import br.com.luccasoftware.api_dw.dto.LocalEvento;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalEventoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public List<LocalEvento> findAll() {
        List<LocalEvento> locaisEventos = new ArrayList<>();
        String vsql = "select distinct c.id, c.id_evento, c.nome, c.logradouro, c.bairro,\n " +
                "                c.numero, c.complemento, c.cep, c.cidade,\n " +
                "                c.uf, c.ensalados, c.id_data_periodo_evento, c.quantidade_salas, c.capacidade \n " +
                "from  dblink('dbname=colaborador host=localhost user=institutoaocp password=351016',\n" +
                "                             'SELECT a.id, a.id_evento, a.nome, a.logradouro, a.bairro, a.numero, a.complemento, a.cep, \n" +
                " b.nome as cidade, c.sigla as uf,\n" +
                " a.ensalados, \n" +
                " a.id_data_periodo_evento, \n" +
                " a.quantidade_salas, a.capacidade\n" +
                " FROM local_aplicacao_evento a, cidade b, estado c\n" +
                " WHERE a.id_cidade = b.codigo and a.id_estado = c.codigo')\n" +
                "    AS c(id int, id_evento int, nome text, logradouro text, bairro text, numero text, complemento text,\n" +
                "        cep text, cidade text, uf text, ensalados int, id_data_periodo_evento int, quantidade_salas int, capacidade int\n" +
                "        ) " ;



        Query query = entityManager.createNativeQuery(vsql);
        List<Object[]> resultList = query.getResultList();
        for (Object[] row : resultList) {
            LocalEvento dto = new LocalEvento();
            dto.setId(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            dto.setId_evento(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
            dto.setNome(row[2] != null ? row[2].toString() : "");
            dto.setLogradouro(row[3] != null ? row[3].toString() : "");
            dto.setBairro(row[4] != null ? row[4].toString() : "");
            dto.setNumero(row[5] != null ? row[5].toString() : "");
            dto.setComplemento(row[6] != null ? row[6].toString() : "");
            dto.setCep(row[7] != null ? row[7].toString() : "");
            dto.setCidade(row[8] != null ? row[8].toString() : "");
            dto.setUf(row[9] != null ? row[9].toString() : "");
            dto.setEnsalados(row[10] != null ? Long.parseLong(row[10].toString()) : 0L);
            dto.setId_data_periodo_evento(row[11] != null ? Long.parseLong(row[11].toString()) : 0L);
            dto.setQuantidade_salas(row[12] != null ? Long.parseLong(row[12].toString()) : 0L);
            dto.setCapacidade(row[13] != null ? Long.parseLong(row[13].toString()) : 0L);




            locaisEventos.add(dto);
        }

        return locaisEventos;


    }

    public List<LocalEvento> findAll(long id_evento) {
        List<LocalEvento> locaisEventos = new ArrayList<>();
        String vsql = "select distinct c.id, c.id_evento, c.nome, c.logradouro, c.bairro,\n " +
                "                c.numero, c.complemento, c.cep, c.cidade,\n " +
                "                c.uf, c.ensalados, c.id_data_periodo_evento, c.quantidade_salas, c.capacidade \n " +
                "from  dblink('dbname=colaborador host=localhost user=institutoaocp password=351016',\n" +
                "                             'SELECT a.id, a.id_evento, a.nome, a.logradouro, a.bairro, a.numero, a.complemento, a.cep, \n" +
                " b.nome as cidade, c.sigla as uf,\n" +
                " a.ensalados, \n" +
                " a.id_data_periodo_evento, \n" +
                " a.quantidade_salas, a.capacidade\n" +
                " FROM local_aplicacao_evento a, cidade b, estado c\n" +
                " WHERE a.id_cidade = b.codigo and a.id_estado = c.codigo')\n" +
                "    AS c(id int, id_evento int, nome text, logradouro text, bairro text, numero text, complemento text,\n" +
                "        cep text, cidade text, uf text, ensalados int, id_data_periodo_evento int, quantidade_salas int, capacidade int\n" +
                "        ) where c.id_evento=" + id_evento ;

        System.out.println( vsql );

        Query query = entityManager.createNativeQuery(vsql);
        List<Object[]> resultList = query.getResultList();
        for (Object[] row : resultList) {
            LocalEvento dto = new LocalEvento();
            dto.setId(row[0] != null ? Long.parseLong(row[0].toString()) : 0L);
            dto.setId_evento(row[1] != null ? Long.parseLong(row[1].toString()) : 0L);
            dto.setNome(row[2] != null ? row[2].toString() : "");
            dto.setLogradouro(row[3] != null ? row[3].toString() : "");
            dto.setBairro(row[4] != null ? row[4].toString() : "");
            dto.setNumero(row[5] != null ? row[5].toString() : "");
            dto.setComplemento(row[6] != null ? row[6].toString() : "");
            dto.setCep(row[7] != null ? row[7].toString() : "");
            dto.setCidade(row[8] != null ? row[8].toString() : "");
            dto.setUf(row[9] != null ? row[9].toString() : "");
            dto.setEnsalados(row[10] != null ? Long.parseLong(row[10].toString()) : 0L);
            dto.setId_data_periodo_evento(row[11] != null ? Long.parseLong(row[11].toString()) : 0L);
            dto.setQuantidade_salas(row[12] != null ? Long.parseLong(row[12].toString()) : 0L);
            dto.setCapacidade(row[13] != null ? Long.parseLong(row[13].toString()) : 0L);




            locaisEventos.add(dto);
        }

        return locaisEventos;


    }


}

