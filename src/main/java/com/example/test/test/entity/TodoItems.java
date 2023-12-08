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
@Table(name = "Todo_Items")
public class TodoItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Created_By")
    private UserTable user;

    @ManyToOne
    @JoinColumn(name = "Todo_Status")
    private TodoStatus todoStatus;

    @Column(name = "Todo_Heading")
    private String todoHeading;

    @Column(name = "Todo_Desc")
    private String todoDesc;

    @CreationTimestamp
    @Column(name = "Created_On")
    private Date createdOn;

    @UpdateTimestamp
    @Column(name = "Updated_On")
    private Date updatedOn;



}
