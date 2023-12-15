package com.mvp.P6mvpapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "theme")
@EqualsAndHashCode(exclude = {"users", "articles"})
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
@JsonIdentityReference(alwaysAsId = false)
@JsonIgnoreProperties({"articles"})
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;


    @ManyToMany(mappedBy = "themes")
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "theme")
    private Set<Article> articles = new HashSet<>();

    
}
