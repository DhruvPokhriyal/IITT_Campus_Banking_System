package com.iit.banking.model.entity;

import java.util.Objects;

import com.iit.banking.model.base.Person;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "admin_sequence", sequenceName = "admin_sequence", allocationSize = 1)
public class Admin extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_sequence")
    private Long id;

    public Admin() {
    }

    public Admin(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Admin admin = (Admin) o;
        return id.equals(admin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

}
