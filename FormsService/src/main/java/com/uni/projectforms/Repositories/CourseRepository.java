package com.uni.projectforms.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.uni.projectforms.Models.Course;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Responses.CourseRatingsResponse;

import java.util.List;

/**
 * CourseRepository
 */
public interface CourseRepository extends CrudRepository<Course, Long> {

	public List<Course> findByInstructor(User instructor);
}
