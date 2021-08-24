package com.phil.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_list")
@NoArgsConstructor
@Data
public class User {

    @Id
    private UUID id;
    private String name;
    private String role;

}
