package com.example.client_record_system.clients;

import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toDomain(ClientEntity entity){
        return new Client(
                entity.getId(),
                entity.getFullName(),
                entity.getGender(),
                entity.getStatus(),
                entity.getCreateDttm(),
                entity.getModifyDttm()
        );
    }

    public ClientEntity toEntity(Client client){
        ClientEntity entity = new ClientEntity();
        entity.setFullName(client.fullName().trim());
        entity.setGender(client.gender());
        return entity;
    }

    public void updateEntity(ClientEntity entity, Client client){
        if(client.fullName() != null){
            entity.setFullName(client.fullName().trim());
        }
        if(client.gender() != null){
            entity.setGender(client.gender());
        }
        if(client.status() != null){
            entity.setStatus(client.status());
        }
    }
}
