package com.technicaltest.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * this is the entity class representing a user
 *
 * @author boubacar
 */
@Entity
@Table(name = "USERS_TBL")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private int userId;

    @Column(name = "USERNAME", unique = true,
            nullable = false, length = 50)
    private String name;
    private Date birthDate;
    private String country;
    private String phoneNumber;
    private String gender;
}
