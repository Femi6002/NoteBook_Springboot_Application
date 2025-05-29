package com.example.notebook.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @Lob
    private String noteContent;

    private String noteTitle;

    private String ownerUsername;
}
