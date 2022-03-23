package com.currencyconverter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@MappedSuperclass
public abstract class EntityBase<ID> implements Serializable {

    @Id
    @Column(unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @NotNull
    @Column(name = "date", nullable = false, updatable = false)
    private LocalDateTime date;

    @NotNull
    @JsonIgnore
    @Column(nullable = false)
    private Byte excluded;

    EntityBase() {
        excluded = 0;
        date = LocalDateTime.now(ZoneOffset.UTC);
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Byte getExcluded() {
        return excluded;
    }

    public void setExcluded(Byte excluded) {
        this.excluded = excluded;
    }

    public void merge(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityBase<?> that = (EntityBase<?>) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return excluded != null ? excluded.equals(that.excluded) : that.excluded == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (excluded != null ? excluded.hashCode() : 0);
        return result;
    }
}
