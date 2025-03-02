package com.iit.banking.model.entity;

import java.util.Objects;

import com.iit.banking.model.base.Person;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
@Table(name = "users")
public class User extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    public Long getId() {
        return id;
    }

    public User() {
    }

    public User(String name, String email, String password, Account account) {
        super(name, email, password);
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
