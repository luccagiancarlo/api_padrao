package br.com.luccasoftware.api_dw.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtils {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean existeColuna(String tabela, String coluna) {
        String sql = "SELECT count(column_name) " +
                "FROM information_schema.columns " +
                "WHERE table_name = '" + tabela + "' AND column_name = '" + coluna + "'";
        Query query = entityManager.createNativeQuery(sql);

        Object result = query.getSingleResult();
        int count = Integer.parseInt(result.toString());
        return count > 0;
    }
}
