package com.iit.banking.dto;

import com.iit.banking.model.entity.Admin;

public class AdminDTO {
    private Long id;
    private String name;
    private String email;

    public AdminDTO(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
