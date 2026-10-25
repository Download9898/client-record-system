package com.example.client_record_system.clients;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id){
        super("Client with id " + id + " not found");
    }
}
