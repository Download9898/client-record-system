package com.example.client_record_system.clients;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClientService {

    public final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDomain)
                .orElseThrow(()-> new ClientNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDomain)
                .toList();
    }

    @Transactional
    public Client create(Client clientToCreate) {
        if(clientToCreate.fullName() == null || clientToCreate.fullName().isBlank()){
            throw  new IllegalArgumentException("fullName: must not be blank");
        }

        ClientEntity entity = clientMapper.toEntity(clientToCreate);
        entity.setStatus(ClientStatus.ACTIVE);

        return clientMapper.toDomain(clientRepository.save(entity));
    }

    @Transactional
    public Client updateClient(
            Long id,
            Client clientToUpdate
    ) {
        ClientEntity entity = clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException(id));

        clientMapper.updateEntity(entity,clientToUpdate);
        return clientMapper.toDomain(entity);
    }

    @Transactional
    public void deleteClient(Long id) {
        if(!clientRepository.existsById(id)){
            throw new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }


}
