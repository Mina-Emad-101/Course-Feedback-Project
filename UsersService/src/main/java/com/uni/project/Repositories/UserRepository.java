package com.uni.project.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.uni.project.Models.User;


/**
 * UserRepository
 */
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
