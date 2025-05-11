package com.uni.projectforms.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uni.projectforms.Models.Response;
import com.uni.projectforms.Models.User;

/**
 * ResponseRepository
 */
public interface ResponseRepository extends CrudRepository<Response, Long> {

	public List<Response> findByFiller(User filler);
	
}
