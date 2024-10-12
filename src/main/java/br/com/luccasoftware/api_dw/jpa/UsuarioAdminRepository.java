package br.com.luccasoftware.api_dw.jpa;

import br.com.luccasoftware.api_dw.dto.Local;
import br.com.luccasoftware.api_dw.dto.UsuarioAdmin;
import br.com.luccasoftware.api_dw.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioAdminRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public UsuarioAdmin buscarEmail(String email) {
        String sql = "select a.login, a.email, a.nome from usuario a where a.email = '"+email+"' and block=false ";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();
        UsuarioAdmin usu = new UsuarioAdmin();

        for (Object[] row : resultList) {
           usu.setLogin(row[0] != null ? row[0].toString() : "");
           usu.setEmail(row[1] != null ? row[1].toString() : "");
           usu.setNome(row[2] != null ? row[2].toString() : "");
        }

        return usu;
    }

    public UsuarioAdmin buscarLogin(String login) {
        String sql = "select a.login, a.email, a.nome from usuario a where a.login = '"+login+"' and block=false ";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();
        UsuarioAdmin usu = new UsuarioAdmin();

        for (Object[] row : resultList) {
            usu.setLogin(row[0] != null ? row[0].toString() : "");
            usu.setEmail(row[1] != null ? row[1].toString() : "");
            usu.setNome(row[2] != null ? row[2].toString() : "");
        }

        return usu;
    }




}


