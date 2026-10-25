package com.example.client_record_system.clients;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public Client getById(
            @PathVariable("id") Long id
    ){
        return clientService.findById(id);
    }

    @GetMapping()
    public List<Client> getAll(){
        return clientService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(
            @Valid @RequestBody Client clientToCreate
    ){
        return clientService.create(clientToCreate);
    }

    @PutMapping("/{id}")
    public Client update(
            @PathVariable Long id,
            @Valid @RequestBody Client clientToUpdate
    ){
        return clientService.updateClient(id,clientToUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ){
        clientService.deleteClient(id);
    }
}
