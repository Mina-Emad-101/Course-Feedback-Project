package com.uni.project.Models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uni.project.Repositories.RoleRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
	enum RoleType {
		ADMIN,
		INSTRUCTOR,
		STUDENT,
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany(mappedBy = "role")
	private List<User> users;

	public static Long getAdminRoleID() {
		return (long)(RoleType.ADMIN.ordinal() + 1);
	}

	public static Long getInstructorRoleID() {
		return (long)(RoleType.INSTRUCTOR.ordinal() + 1);
	}

	public static Long getStudentRoleID() {
		return (long)(RoleType.STUDENT.ordinal() + 1);
	}
}
