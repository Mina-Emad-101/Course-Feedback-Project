package com.uni.projectforms.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uni.projectforms.Annotations.AuthGuarded;
import com.uni.projectforms.Annotations.StudentsOnly;
import com.uni.projectforms.Models.Course;
import com.uni.projectforms.Models.Response;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Models.Dtos.ModelDTO;
import com.uni.projectforms.Models.Dtos.ResponseBasicDTO;
import com.uni.projectforms.Repositories.CourseRepository;
import com.uni.projectforms.Repositories.ResponseRepository;
import com.uni.projectforms.Repositories.UserRepository;
import com.uni.projectforms.Requests.UpdateResponseRequest;
import com.uni.projectforms.Responses.CourseRatingsResponse;
import com.uni.projectforms.Responses.ErrorResponse;
import com.uni.projectforms.Responses.InstructorRatingsResponse;

import jakarta.validation.Valid;

/**
 * ResponseController
 */
@RestController
public class ResponseController {

	@Autowired
	private ResponseRepository responseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseRepository courseRepository;

	@GetMapping("/responses")
	@AuthGuarded
	public ResponseEntity<Object> getResponses() {
		List<ModelDTO> responses = new ArrayList<ModelDTO>();
		User loggedInUser = User.getLoggedInUser().get();
		if (loggedInUser.isStudent()) {
			this.responseRepository.findByStudent(loggedInUser).forEach((Response response) -> responses.add(new ResponseBasicDTO(response)));
		} else {
			this.responseRepository.findAll().forEach((Response response) -> responses.add(new ResponseBasicDTO(response)));
		}
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/responses/{id}")
	@AuthGuarded
	public ResponseEntity<Object> getResponse(@PathVariable Long id) {
		Optional<Response> response = this.responseRepository.findById(id);

		if (response.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found"));
		}
		User loggedInUser = User.getLoggedInUser().get();
		if (loggedInUser.isStudent() && loggedInUser.getId() != response.get().getStudent().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Response belongs to another Student"));
		}
		return ResponseEntity.ok(new ResponseBasicDTO(response.get()));
	}

	@PutMapping("/responses/{id}")
	@AuthGuarded
	@StudentsOnly
	public ResponseEntity<Object> updateResponse(@PathVariable Long id, @RequestBody @Valid UpdateResponseRequest responseRequest) {
		Response originalResponse = this.responseRepository.findById(id).orElse(null);
		if (originalResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Response Not Found"));
		}

		User loggedInUser = User.getLoggedInUser().get();
		if (loggedInUser.isStudent() && loggedInUser.getId() != originalResponse.getStudent().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Response belongs to another Student"));
		}

		originalResponse.setComment(responseRequest.getComment());
		originalResponse.setCourseRating(responseRequest.getCourseRating());
		originalResponse.setInstructorRating(responseRequest.getInstructorRating());

		Response updatedResponse = this.responseRepository.save(originalResponse);

		return ResponseEntity.ok(new ResponseBasicDTO(updatedResponse));
	}

	@GetMapping("/responses/ratings/instructors/{id}")
	@AuthGuarded
	public ResponseEntity<Object> getInstructorRatings(@PathVariable Long id) {
		User user = this.userRepository.findById(id).orElse(null);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Instructor Not Found"));
		}
		System.out.println(user.toString());
		if (!user.isInstructor()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("ID belongs to Non-Instructor"));
		}

		InstructorRatingsResponse response = this.responseRepository.getInstructorRatingsResponse(user);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/responses/ratings/courses/{id}")
	@AuthGuarded
	public ResponseEntity<Object> getCourseRatings(@PathVariable Long id) {
		Optional<Course> course = this.courseRepository.findById(id);

		if (course.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found"));
		}

		CourseRatingsResponse response = this.responseRepository.getCourseRatingsResponse(course.get());

		return ResponseEntity.ok(response);
	}
}
