package com.example.client_record_system.clients;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Table(name = "client_account")
@Entity
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "gender", length = 1)
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ClientStatus status;

    @Column(name = "create_dttm", nullable = false, updatable = false)
    private OffsetDateTime createDttm;

    @Column(name = "modify_dttm", nullable = false)
    private OffsetDateTime modifyDttm;


    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createDttm = now;
        this.modifyDttm = now;
    }

    @PreUpdate
    void onUpdate() {
        this.modifyDttm = OffsetDateTime.now();
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setStatus(ClientStatus status) {
        this.status = status;
    }
    public void setCreateDttm(OffsetDateTime createDttm) {
        this.createDttm = createDttm;
    }
    public void setModifyDttm(OffsetDateTime modifyDttm) {
        this.modifyDttm = modifyDttm;
    }

    public String getFullName() {
        return fullName;
    }
    public String getGender() {
        return gender;
    }
    public ClientStatus getStatus() {
        return status;
    }
    public OffsetDateTime getCreateDttm() {
        return createDttm;
    }
    public OffsetDateTime getModifyDttm() {
        return modifyDttm;
    }

    public ClientEntity(Long id, String fullName, String gender, ClientStatus status, OffsetDateTime createDttm, OffsetDateTime modifyDttm) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.status = status;
        this.createDttm = createDttm;
        this.modifyDttm = modifyDttm;
    }

    public ClientEntity(){

    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
