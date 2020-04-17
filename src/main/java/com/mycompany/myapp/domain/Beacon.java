package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Beacon.
 */
@Entity
@Table(name = "beacon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Beacon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_name")
    private String contentName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "content")
    private String content;

    @Column(name = "content_description")
    private String contentDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentName() {
        return contentName;
    }

    public Beacon contentName(String contentName) {
        this.contentName = contentName;
        return this;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentType() {
        return contentType;
    }

    public Beacon contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public Beacon content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public Beacon contentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
        return this;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beacon)) {
            return false;
        }
        return id != null && id.equals(((Beacon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Beacon{" +
            "id=" + getId() +
            ", contentName='" + getContentName() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", content='" + getContent() + "'" +
            ", contentDescription='" + getContentDescription() + "'" +
            "}";
    }
}
