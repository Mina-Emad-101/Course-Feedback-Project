package com.uni.projectforms.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.uni.projectforms.Models.User;


/**
 * UserRepository
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
