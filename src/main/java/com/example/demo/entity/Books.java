package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_list")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Books {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Name will be the name of the book and not the author
    private String name;
    private String author;
}
