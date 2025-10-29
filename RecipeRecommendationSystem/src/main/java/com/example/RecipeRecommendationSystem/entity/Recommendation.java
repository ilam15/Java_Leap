package com.example.RecipeRecommendationSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Recommendation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recommendation_id")
	private Long recommendationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@Column(name = "match_score")
	private Double matchScore;

}
