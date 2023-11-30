package com.project.questapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "p_like")
public class Like {
    @Id
    Long id;
    Long postId;
    Long userId;
}
