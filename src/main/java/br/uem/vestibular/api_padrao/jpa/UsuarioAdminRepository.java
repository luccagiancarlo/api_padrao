package br.uem.vestibular.api_padrao.jpa;

import br.uem.vestibular.api_padrao.dto.UsuarioAdmin;
import br.uem.vestibular.api_padrao.utils.DatabaseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioAdminRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseUtils databaseUtils;

    public UsuarioAdmin buscarEmail(String email) {
        String sql = "SELECT CD_USUARIO, EN_EMAIL, NM_USUARIO, CD_SETOR, DE_CARGO, TP_USUARIO " +
                     "FROM SGV.SGV_USUARIO " +
                     "WHERE EN_EMAIL = :email";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("email", email);
        List<Object[]> resultList = query.getResultList();
        UsuarioAdmin usu = new UsuarioAdmin();

        if (!resultList.isEmpty()) {
            Object[] row = resultList.get(0);
            usu.setCdUsuario(row[0] != null ? Integer.parseInt(row[0].toString()) : null);
            usu.setEmail(row[1] != null ? row[1].toString() : "");
            usu.setNome(row[2] != null ? row[2].toString() : "");
            usu.setCdSetor(row[3] != null ? row[3].toString() : "");
            usu.setDeCargo(row[4] != null ? row[4].toString() : "");
            usu.setTpUsuario(row[5] != null ? row[5].toString() : "");
        }

        return usu;
    }

    public UsuarioAdmin buscarPorCodigo(Integer cdUsuario) {
        String sql = "SELECT CD_USUARIO, EN_EMAIL, NM_USUARIO, CD_SETOR, DE_CARGO, TP_USUARIO " +
                     "FROM SGV.SGV_USUARIO " +
                     "WHERE CD_USUARIO = :cdUsuario";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("cdUsuario", cdUsuario);
        List<Object[]> resultList = query.getResultList();
        UsuarioAdmin usu = new UsuarioAdmin();

        if (!resultList.isEmpty()) {
            Object[] row = resultList.get(0);
            usu.setCdUsuario(row[0] != null ? Integer.parseInt(row[0].toString()) : null);
            usu.setEmail(row[1] != null ? row[1].toString() : "");
            usu.setNome(row[2] != null ? row[2].toString() : "");
            usu.setCdSetor(row[3] != null ? row[3].toString() : "");
            usu.setDeCargo(row[4] != null ? row[4].toString() : "");
            usu.setTpUsuario(row[5] != null ? row[5].toString() : "");
        }

        return usu;
    }

}


