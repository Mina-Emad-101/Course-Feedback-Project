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
import com.uni.projectforms.Annotations.AdminsOnly;
import com.uni.projectforms.Models.Course;
import com.uni.projectforms.Models.Form;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Models.Dtos.ModelDTO;
import com.uni.projectforms.Models.Dtos.FormBasicDTO;
import com.uni.projectforms.Repositories.FormRepository;
import com.uni.projectforms.Repositories.CourseRepository;
import com.uni.projectforms.Requests.CreateFormRequest;
import com.uni.projectforms.Requests.UpdateFormRequest;
import com.uni.projectforms.Responses.ErrorResponse;

import jakarta.validation.Valid;

/**
 * FormController
 */
@RestController
public class FormController {

	@Autowired
	private FormRepository formRepository;

	@Autowired
	private CourseRepository courseRepository;

	@GetMapping("/forms")
	@AuthGuarded
	public ResponseEntity<Object> getForms() {
		List<ModelDTO> forms = new ArrayList<ModelDTO>();
		this.formRepository.findAll().forEach((Form form) -> forms.add(new FormBasicDTO(form)));
		return ResponseEntity.ok(forms);
	}

	@GetMapping("/forms/{id}")
	@AuthGuarded
	public ResponseEntity<Object> getForm(@PathVariable Long id) {
		Optional<Form> form = this.formRepository.findById(id);

		if (form.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found"));
		}
		return ResponseEntity.ok(new FormBasicDTO(form.get()));
	}

	@PostMapping("/forms")
	@AuthGuarded
	@AdminsOnly
	public ResponseEntity<Object> createForm(@RequestBody @Valid CreateFormRequest formRequest) {
		User user = User.getLoggedInUser().get();
		Course course = this.courseRepository.findById(formRequest.getCourseID()).orElse(null);
		if (course == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Course Not Found"));
		}

		Form form = Form.builder()
		.deadline(formRequest.getDeadline())
		.course(course)
		.creator(user)
		.isActive(true)
		.build();

		Form newForm = this.formRepository.save(form);

		return ResponseEntity.status(HttpStatus.CREATED).body(new FormBasicDTO(newForm));
	}

	@PutMapping("/forms/{id}")
	@AuthGuarded
	@AdminsOnly
	public ResponseEntity<Object> updateForm(@PathVariable Long id, @RequestBody @Valid UpdateFormRequest formRequest) {
		Form originalForm = this.formRepository.findById(id).orElse(null);
		if (originalForm == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Form Not Found"));
		}

		originalForm.setDeadline(formRequest.getDeadline());
		originalForm.setIsActive(formRequest.getIsActive());

		Form updatedForm = this.formRepository.save(originalForm);

		return ResponseEntity.ok(new FormBasicDTO(updatedForm));
	}
}
