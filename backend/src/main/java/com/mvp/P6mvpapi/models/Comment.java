package com.mvp.P6mvpapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;


    @ManyToOne
    @JsonBackReference("article-comment")
    private Article article;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date timestamp;

    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-comment")
    private User user;

    private String username;
}
