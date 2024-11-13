package com.example.employee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="emp")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Bhargavii

    @NotNull(message = "name should not be not null")
    @Size(min =2,max=50,message="name characters should in the middle of 2 to 50" )
    private String name;

    @NotBlank(message = "Email is mandatory..")
    @Email(message = "Please provide a valid mail..")
    private String mail;

}
