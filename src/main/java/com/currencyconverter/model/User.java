package com.currencyconverter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User extends EntityBase<Long> {

    @Size(max = 50, message = "The username cannot be longer than 50 characters.")
    @Column(nullable = false)
    private String username;

    @Size(max = 10, message = "User password cannot be longer than 10 characters.")
    @Column(nullable = false)
    private String password;

    public User() {

    }

    private User(Builder builder) {
        super.setId(builder.getId());
        username = builder.username;
        password = builder.password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder extends EntityBase<Long> {

        private String username;
        private String password;

        public Builder id(Long id) {
            super.setId(id);
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
