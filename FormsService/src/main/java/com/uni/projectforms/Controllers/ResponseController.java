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
import com.uni.projectforms.Annotations.StudentsOnly;
import com.uni.projectforms.Models.Form;
import com.uni.projectforms.Models.Response;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Models.Dtos.ModelDTO;
import com.uni.projectforms.Models.Dtos.ResponseBasicDTO;
import com.uni.projectforms.Repositories.FormRepository;
import com.uni.projectforms.Repositories.ResponseRepository;
import com.uni.projectforms.Requests.CreateResponseRequest;
import com.uni.projectforms.Requests.UpdateResponseRequest;
import com.uni.projectforms.Responses.ErrorResponse;

import jakarta.validation.Valid;

/**
 * ResponseController
 */
@RestController
public class ResponseController {

	@Autowired
	private ResponseRepository responseRepository;

	@Autowired
	private FormRepository formRepository;

	@GetMapping("/responses")
	@AuthGuarded
	public ResponseEntity<Object> getResponses() {
		List<ModelDTO> responses = new ArrayList<ModelDTO>();
		User loggedInUser = User.getLoggedInUser().get();
		if (loggedInUser.isStudent()) {
			this.responseRepository.findByFiller(loggedInUser).forEach((Response response) -> responses.add(new ResponseBasicDTO(response)));
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
		if (loggedInUser.isStudent() && loggedInUser.getId() != response.get().getFiller().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Response belongs to another Student"));
		}
		return ResponseEntity.ok(new ResponseBasicDTO(response.get()));
	}

	@PostMapping("/forms/{formID}/respond")
	@AuthGuarded
	@StudentsOnly
	public ResponseEntity<Object> createResponse(@PathVariable Long formID, @RequestBody @Valid CreateResponseRequest responseRequest) {
		User user = User.getLoggedInUser().get();
		Form form = this.formRepository.findById(formID).orElse(null);
		if (form == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Form Not Found"));
		}

		Response response = Response.builder()
		.filler(user)
		.form(form)
		.comment(responseRequest.getComment())
		.courseRating(responseRequest.getCourseRating())
		.instructorRating(responseRequest.getInstructorRating())
		.build();

		Response newResponse = this.responseRepository.save(response);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseBasicDTO(newResponse));
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
		if (loggedInUser.isStudent() && loggedInUser.getId() != originalResponse.getFiller().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Response belongs to another Student"));
		}

		originalResponse.setComment(responseRequest.getComment());
		originalResponse.setCourseRating(responseRequest.getCourseRating());
		originalResponse.setInstructorRating(responseRequest.getInstructorRating());

		Response updatedResponse = this.responseRepository.save(originalResponse);

		return ResponseEntity.ok(new ResponseBasicDTO(updatedResponse));
	}
}
