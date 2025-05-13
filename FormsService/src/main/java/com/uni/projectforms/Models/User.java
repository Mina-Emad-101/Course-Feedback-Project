package com.uni.projectforms.Models;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
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
 * User
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	private static Optional<User> loggedInUser = Optional.empty();
	private static Optional<String> bearerToken = Optional.empty();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@OneToMany(mappedBy = "instructor")
	private List<Course> courses;

	@OneToMany(mappedBy = "student")
	private List<Response> responses;

	@CreationTimestamp
	private Timestamp created_at;

	public static Optional<User> getLoggedInUser() {
		return User.loggedInUser;
	}

	public static void setLoggedInUser(User user) {
		User.loggedInUser = Optional.of(user);
	}

	public static void resetLoggedInUser() {
		User.loggedInUser = Optional.empty();
	}

	public static Optional<String> getBearerToken() {
		return User.bearerToken;
	}

	public static void setBearerToken(String token) {
		User.bearerToken = Optional.of(token);
	}

	public static void resetBearerToken() {
		User.bearerToken = Optional.empty();
	}

	public Boolean isAdmin() {
		return this.role.getId() == Role.getAdminRoleID();
	}

	public Boolean isStudent() {
		return this.role.getId() == Role.getStudentRoleID();
	}

	public Boolean isInstructor() {
		return this.role.getId() == Role.getInstructorRoleID();
	}

	@Override
	public String toString() {
		return "{ id: " + this.id + ", email: " + this.email + ", name: " + this.name + ", role: " + this.role.toString() + " }";
	}
}
