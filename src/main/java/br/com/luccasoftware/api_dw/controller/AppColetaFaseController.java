package br.com.luccasoftware.api_dw.controller;

import br.com.luccasoftware.api_dw.jpa.AppColetaFase;
import br.com.luccasoftware.api_dw.jpa.AppColetaFaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api_dw/v1/coleta-fase")
public class AppColetaFaseController {

    @Autowired
    private AppColetaFaseRepository appColetaFaseRepository;

    // 1. Buscar por ID (Autenticado)
    @GetMapping("/{id}")
    public ResponseEntity<AppColetaFase> getById(
            @PathVariable Integer id,
            @RequestHeader(value = "Authorization") String authorizationHeader) {
        Optional<AppColetaFase> fase = appColetaFaseRepository.findById(id);
        return fase.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 2. Buscar por nome parcial (ignora case) (Autenticado)
    @GetMapping("/buscar")
    public ResponseEntity<List<AppColetaFase>> getByNome(
            @RequestParam String nome,
            @RequestHeader(value = "Authorization") String authorizationHeader) {
        List<AppColetaFase> fases = appColetaFaseRepository.findByNomeContainingIgnoreCase(nome);
        return ResponseEntity.ok(fases);
    }

    // 3. Incluir nova fase (Autenticado)
    @PostMapping
    public ResponseEntity<AppColetaFase> create(
            @RequestBody AppColetaFase novaFase,
            @RequestHeader(value = "Authorization") String authorizationHeader) {
        AppColetaFase savedFase = appColetaFaseRepository.save(novaFase);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFase);
    }

    // 4. Alterar fase existente (Autenticado)
    @PutMapping("/{id}")
    public ResponseEntity<AppColetaFase> update(
            @PathVariable Integer id,
            @RequestBody AppColetaFase faseAtualizada,
            @RequestHeader(value = "Authorization") String authorizationHeader) {
        Optional<AppColetaFase> faseExistente = appColetaFaseRepository.findById(id);
        if (faseExistente.isPresent()) {
            AppColetaFase fase = faseExistente.get();
            fase.setNome(faseAtualizada.getNome());
            AppColetaFase updatedFase = appColetaFaseRepository.save(fase);
            return ResponseEntity.ok(updatedFase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Excluir fase pelo ID (Autenticado)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id,
            @RequestHeader(value = "Authorization") String authorizationHeader) {
        if (appColetaFaseRepository.existsById(id)) {
            appColetaFaseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
