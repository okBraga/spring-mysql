package br.com.braga.springmysql.controller;

import br.com.braga.springmysql.model.Contact;
import br.com.braga.springmysql.repository.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

        private ContactRepository repository;

    ContactController(ContactRepository contactRepository) {
        this.repository = contactRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable long id){
        return repository.findById(id).map(retorno -> ResponseEntity.ok().body(retorno)).
                orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contact save(@RequestBody Contact contact){
        return repository.save(contact);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Contact contact){
        return repository.findById(id)
                .map(retorno -> {retorno.setName(contact.getName());
                retorno.setEmail(contact.getEmail());
                retorno.setPhone(contact.getPhone());
                Contact updated = repository.save(retorno);
                return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(retorno -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
