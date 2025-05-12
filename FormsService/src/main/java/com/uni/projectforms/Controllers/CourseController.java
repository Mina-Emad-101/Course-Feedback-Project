package com.uni.projectforms.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uni.projectforms.Annotations.AuthGuarded;
import com.uni.projectforms.Annotations.InstructorsOnly;
import com.uni.projectforms.Models.Course;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Models.Dtos.ModelDTO;
import com.uni.projectforms.Models.Dtos.CourseBasicDTO;
import com.uni.projectforms.Repositories.CourseRepository;
import com.uni.projectforms.Repositories.UserRepository;
import com.uni.projectforms.Requests.CreateCourseRequest;
import com.uni.projectforms.Requests.UpdateCourseRequest;
import com.uni.projectforms.Responses.CourseRatingsResponse;
import com.uni.projectforms.Responses.ErrorResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * CourseController
 */
@RestController
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;

	@GetMapping("/courses")
	@AuthGuarded
	public ResponseEntity<Object> getCourses() {
		List<CourseBasicDTO> courses = new ArrayList<CourseBasicDTO>();
		User loggedInUser = User.getLoggedInUser().get();
		if (loggedInUser.isInstructor()) {
			this.courseRepository.findByInstructor(loggedInUser)
					.forEach((Course course) -> courses.add(new CourseBasicDTO(course)));
		} else {
			this.courseRepository.findAll().forEach((Course course) -> courses.add(new CourseBasicDTO(course)));
		}
		return ResponseEntity.ok(courses);
	}

	@GetMapping("/courses/{id}")
	@AuthGuarded
	public ResponseEntity<Object> getCourse(@PathVariable Long id) {
		Optional<Course> course = this.courseRepository.findById(id);

		if (course.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found"));
		}
		return ResponseEntity.ok(new CourseBasicDTO(course.get()));
	}

	@PostMapping("/courses")
	@AuthGuarded
	@InstructorsOnly
	public ResponseEntity<Object> createCourse(@RequestBody @Valid CreateCourseRequest courseRequest) {
		User user = User.getLoggedInUser().get();

		Course course = Course.builder()
				.name(courseRequest.getName())
				.code(courseRequest.getCode())
				.instructor(user)
				.build();

		Course newCourse = this.courseRepository.save(course);

		return ResponseEntity.status(HttpStatus.CREATED).body(new CourseBasicDTO(newCourse));
	}

	@PutMapping("/courses/{id}")
	@AuthGuarded
	@InstructorsOnly
	public ResponseEntity<Object> updateCourse(@PathVariable Long id,
			@RequestBody @Valid UpdateCourseRequest courseRequest) {
		Course originalCourse = this.courseRepository.findById(id).orElse(null);
		if (originalCourse == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Course Not Found"));
		}

		User user = User.getLoggedInUser().get();

		if (user.getId() != originalCourse.getInstructor().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Course belongs to another instructor"));
		}

		originalCourse.setName(courseRequest.getName());
		originalCourse.setCode(courseRequest.getCode());
		originalCourse.setInstructor(user);

		Course updatedCourse = this.courseRepository.save(originalCourse);

		return ResponseEntity.ok(new CourseBasicDTO(updatedCourse));
	}
}
