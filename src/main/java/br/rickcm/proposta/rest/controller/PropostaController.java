package br.rickcm.proposta.rest.controller;

import br.rickcm.proposta.model.Proposta;
import br.rickcm.proposta.repository.PropostaRepository;
import br.rickcm.proposta.rest.dto.NovaPropostaRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private PropostaRepository repository;

    public PropostaController(PropostaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request,
                                   UriComponentsBuilder uriBuilder){
        Proposta novaProposta = request.toModel();
        repository.save(novaProposta);
        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    /*
     * Endpoint criado para testes de retorno.
     * TODO refatorar futuramente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id){
        Optional<Proposta> possivelProposta = repository.findById(id);
        if (possivelProposta.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(possivelProposta.get().toString());
    }
}
