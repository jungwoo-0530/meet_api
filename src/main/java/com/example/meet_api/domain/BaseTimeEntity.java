package com.example.meet_api.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {

    private ZonedDateTime createDate;
    private ZonedDateTime updateDate;

    @PrePersist
    public void prePersist(){
        this.createDate = ZonedDateTime.now();
        this.updateDate = ZonedDateTime.now();
    }

    @PreUpdate
    public void postUpdate() {
        this.updateDate = ZonedDateTime.now();
    }

}
