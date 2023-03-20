package com.nisum.test.apigmc.domain.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity<T> {

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false)
    protected T createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false)
    protected Date created;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY")
    protected T modifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED", nullable = false)
    protected Date modified;

    public T getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(T createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public T getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(T modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "AuditEntity{" +
                "createdBy=" + createdBy +
                ", created=" + created +
                ", modifiedBy=" + modifiedBy +
                ", modified=" + modified +
                '}';
    }
}
