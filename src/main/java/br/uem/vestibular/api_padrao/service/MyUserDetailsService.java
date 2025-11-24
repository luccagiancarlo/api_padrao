package br.uem.vestibular.api_padrao.service;

import br.uem.vestibular.api_padrao.jpa.Usuario;
import br.uem.vestibular.api_padrao.jpa.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário do banco de dados pelo email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + email);
        }

        Usuario usuario = usuarioOpt.get();

        // IMPORTANTE: Como a senha NÃO está criptografada no banco,
        // usamos {noop} prefix para indicar ao Spring Security que não há encoding
        // Isso permite comparação direta de texto plano
        return new User(
            usuario.getEnEmail(),
            "{noop}" + usuario.getSeUsuario(),  // {noop} = no operation (sem criptografia)
            new ArrayList<>()  // Authorities/Roles (vazio por enquanto)
        );
    }
}
