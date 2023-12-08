package com.example.test.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_Table")
public class UserTable {

    @Id
    @Column(name = "Email")
    private String email;

    @Column(name = "Name")
    private String name;

    @Column(name = "User_Name")
    private String userName;

    @CreationTimestamp
    @Column(name = "Created_On")
    private Date createdOn;

    @UpdateTimestamp
    @Column(name = "Updated_On")
    private Date updatedOn;

}
