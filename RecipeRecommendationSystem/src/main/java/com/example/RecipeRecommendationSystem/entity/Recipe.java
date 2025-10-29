package com.example.RecipeRecommendationSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id")
	private Long recipeId;

	private String title;

	// short description
	private String description;

	// large text for ingredients (comma/newline separated)
	@Lob
	private String ingredients;

	// large text for instructions
	@Lob
	private String instructions;

	private Integer calories;

	private String category;

	// creator reference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private User createdBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		if (this.createdAt == null) {
			this.createdAt = LocalDateTime.now();
		}
	}

}
