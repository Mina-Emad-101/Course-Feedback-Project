package com.uni.projectforms.Models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Course
 */
@Entity
@Table(name = "feedback_forms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Form {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate deadline;

	@Column(name = "created_at", nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User creator;

	@OneToMany(mappedBy = "form", fetch = FetchType.EAGER)
	private List<Response> responses;
}
