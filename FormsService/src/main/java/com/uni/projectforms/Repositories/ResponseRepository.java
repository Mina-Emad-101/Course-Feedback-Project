package com.uni.projectforms.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.uni.projectforms.Models.Course;
import com.uni.projectforms.Models.Form;
import com.uni.projectforms.Models.Response;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Responses.CourseRatingsResponse;
import com.uni.projectforms.Responses.FormResultsResponse;
import com.uni.projectforms.Responses.InstructorRatingsResponse;

/**
 * ResponseRepository
 */
public interface ResponseRepository extends CrudRepository<Response, Long> {

	public List<Response> findByStudent(User student);

	@Query("""
			SELECT new com.uni.projectforms.Responses.InstructorRatingsResponse(
			    AVG(r.instructorRating),
				COUNT(r.id),
				r.form.course.instructor
			)
			FROM Response r
			WHERE r.form.course.instructor = :instructor
			""")
	public InstructorRatingsResponse getInstructorRatingsResponse(@Param("instructor") User instructor);

	@Query("""
			SELECT new com.uni.projectforms.Responses.CourseRatingsResponse(
			    AVG(r.courseRating),
				COUNT(r.id)
			)
			FROM Response r
			WHERE r.form.course = :course
			""")
	public CourseRatingsResponse getCourseRatingsResponse(@Param("course") Course course);

	@Query("""
			SELECT new com.uni.projectforms.Responses.FormResultsResponse(
			    AVG(r.courseRating),
				AVG(r.instructorRating),
				COUNT(r.id)
			)
			FROM Response r
			WHERE r.form = :form
			""")
	public FormResultsResponse getFormResultsByForm(@Param("form") Form form);

	@Query("""
			SELECT COUNT(r.id)
			FROM Response r
			WHERE r.form = :form
			AND r.student = :student
			""")
	public Long getStudentResponsesCountByForm(@Param("student") User student, @Param("form") Form form);
}
