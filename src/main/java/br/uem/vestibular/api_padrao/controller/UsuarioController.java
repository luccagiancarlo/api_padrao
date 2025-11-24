package br.uem.vestibular.api_padrao.controller;

import br.uem.vestibular.api_padrao.dto.UsuarioRequest;
import br.uem.vestibular.api_padrao.jpa.Usuario;
import br.uem.vestibular.api_padrao.jpa.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api_dw/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Valida o token Bearer e a autenticação
     */
    private void validarAutenticacao(String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }
    }

    /**
     * GET - Buscar usuário por código
     * Endpoint: GET /api_dw/v1/usuarios/codigo/{cdUsuario}
     */
    @GetMapping("/codigo/{cdUsuario}")
    public ResponseEntity<?> buscarPorCodigo(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable Integer cdUsuario) {

        try {
            validarAutenticacao(authorizationHeader);

            Optional<Usuario> usuario = usuarioRepository.findById(cdUsuario);

            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Usuário não encontrado com código: " + cdUsuario);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * GET - Buscar usuário por email
     * Endpoint: GET /api_dw/v1/usuarios/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable String email) {

        try {
            validarAutenticacao(authorizationHeader);

            Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Usuário não encontrado com email: " + email);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * GET - Listar todos os usuários
     * Endpoint: GET /api_dw/v1/usuarios
     */
    @GetMapping
    public ResponseEntity<?> listarTodos(
            @RequestHeader(value = "Authorization") String authorizationHeader) {

        try {
            validarAutenticacao(authorizationHeader);

            Iterable<Usuario> usuarios = usuarioRepository.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao listar usuários: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * POST - Criar novo usuário
     * Endpoint: POST /api_dw/v1/usuarios
     */
    @PostMapping
    public ResponseEntity<?> criar(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody UsuarioRequest request) {

        try {
            validarAutenticacao(authorizationHeader);

            // Verifica se já existe usuário com o mesmo código
            if (request.getCdUsuario() != null && usuarioRepository.existsById(request.getCdUsuario())) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Já existe um usuário com o código: " + request.getCdUsuario());
                return ResponseEntity.status(400).body(response);
            }

            // Verifica se já existe usuário com o mesmo email
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(request.getEnEmail());
            if (usuarioExistente.isPresent()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Já existe um usuário com o email: " + request.getEnEmail());
                return ResponseEntity.status(400).body(response);
            }

            // Cria novo usuário
            Usuario novoUsuario = new Usuario();
            novoUsuario.setCdUsuario(request.getCdUsuario());
            novoUsuario.setEnEmail(request.getEnEmail());
            novoUsuario.setNmUsuario(request.getNmUsuario());
            novoUsuario.setSeUsuario(request.getSeUsuario()); // ⚠️ Senha em texto plano
            novoUsuario.setCdGestor(request.getCdGestor());
            novoUsuario.setCdSetor(request.getCdSetor());
            novoUsuario.setTpUsuario(request.getTpUsuario());
            novoUsuario.setFlInventario(request.getFlInventario());
            novoUsuario.setFlRespsetor(request.getFlRespsetor());
            novoUsuario.setNuMatricula(request.getNuMatricula());
            novoUsuario.setDePortaria(request.getDePortaria());
            novoUsuario.setDeCargo(request.getDeCargo());
            novoUsuario.setTpDas(request.getTpDas());
            novoUsuario.setTpComissao(request.getTpComissao());

            Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Usuário criado com sucesso!");
            response.put("usuario", usuarioSalvo);
            return ResponseEntity.status(201).body(response);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao criar usuário: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * PUT - Atualizar usuário existente
     * Endpoint: PUT /api_dw/v1/usuarios/{cdUsuario}
     */
    @PutMapping("/{cdUsuario}")
    public ResponseEntity<?> atualizar(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable Integer cdUsuario,
            @RequestBody UsuarioRequest request) {

        try {
            validarAutenticacao(authorizationHeader);

            Optional<Usuario> usuarioExistente = usuarioRepository.findById(cdUsuario);

            if (usuarioExistente.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Usuário não encontrado com código: " + cdUsuario);
                return ResponseEntity.status(404).body(response);
            }

            Usuario usuario = usuarioExistente.get();

            // Atualiza apenas os campos fornecidos (não nulos)
            if (request.getEnEmail() != null) {
                usuario.setEnEmail(request.getEnEmail());
            }
            if (request.getNmUsuario() != null) {
                usuario.setNmUsuario(request.getNmUsuario());
            }
            if (request.getSeUsuario() != null) {
                usuario.setSeUsuario(request.getSeUsuario()); // ⚠️ Senha em texto plano
            }
            if (request.getCdGestor() != null) {
                usuario.setCdGestor(request.getCdGestor());
            }
            if (request.getCdSetor() != null) {
                usuario.setCdSetor(request.getCdSetor());
            }
            if (request.getTpUsuario() != null) {
                usuario.setTpUsuario(request.getTpUsuario());
            }
            if (request.getFlInventario() != null) {
                usuario.setFlInventario(request.getFlInventario());
            }
            if (request.getFlRespsetor() != null) {
                usuario.setFlRespsetor(request.getFlRespsetor());
            }
            if (request.getNuMatricula() != null) {
                usuario.setNuMatricula(request.getNuMatricula());
            }
            if (request.getDePortaria() != null) {
                usuario.setDePortaria(request.getDePortaria());
            }
            if (request.getDeCargo() != null) {
                usuario.setDeCargo(request.getDeCargo());
            }
            if (request.getTpDas() != null) {
                usuario.setTpDas(request.getTpDas());
            }
            if (request.getTpComissao() != null) {
                usuario.setTpComissao(request.getTpComissao());
            }

            Usuario usuarioAtualizado = usuarioRepository.save(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Usuário atualizado com sucesso!");
            response.put("usuario", usuarioAtualizado);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao atualizar usuário: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * DELETE - Remover usuário
     * Endpoint: DELETE /api_dw/v1/usuarios/{cdUsuario}
     */
    @DeleteMapping("/{cdUsuario}")
    public ResponseEntity<?> deletar(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable Integer cdUsuario) {

        try {
            validarAutenticacao(authorizationHeader);

            Optional<Usuario> usuario = usuarioRepository.findById(cdUsuario);

            if (usuario.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Usuário não encontrado com código: " + cdUsuario);
                return ResponseEntity.status(404).body(response);
            }

            usuarioRepository.deleteById(cdUsuario);

            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Usuário deletado com sucesso!");
            response.put("cdUsuario", cdUsuario.toString());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao deletar usuário: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
