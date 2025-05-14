// Student-specific JavaScript functions

/**
 * Initialize the student dashboard
 */
function initStudentDashboard() {
    const user = checkAuth();
    if (!user) return;
    
    // Load pending feedback forms
    loadPendingFeedbackForms();
    
    // Load student courses
    loadStudentCourses();
}

/**
 * Load pending feedback forms for a student
 * @param {number} studentId - Student ID
 */
function loadPendingFeedbackForms() {
    getStudentFeedbackForms()
        .then(response => {
            if (!response.errorMessage) {
                displayPendingFeedbackForms(response);
            } else {
                document.getElementById('pendingFormsTable').innerHTML = `
                    <tr>
                        <td colspan="5" class="text-center py-4 text-danger">
                            <i class="bi bi-exclamation-triangle me-2"></i>
                            Failed to load feedback forms. Please try again later.
                        </td>
                    </tr>
                `;
            }
        })
        .catch(error => {
            console.error('Error loading feedback forms:', error);
            document.getElementById('pendingFormsTable').innerHTML = `
                <tr>
                    <td colspan="5" class="text-center py-4 text-danger">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Failed to load feedback forms. Please try again later.
                    </td>
                </tr>
            `;
        });
}

/**
 * Display pending feedback forms in the table
 * @param {Array} forms - Array of form objects
 */
function displayPendingFeedbackForms(forms) {
    const tableBody = document.getElementById('pendingFormsTable');
    const pendingCount = document.getElementById('pendingCount');
    
    // Filter only pending forms
    const pendingForms = forms.filter(form => form.status === 'pending');
    
    // Update pending count
    pendingCount.textContent = pendingForms.length;
    
    if (pendingForms.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="5" class="text-center py-4">
                    <i class="bi bi-check-circle display-4 text-muted"></i>
                    <p class="mt-3 mb-0">No pending feedback forms. Great job!</p>
                </td>
            </tr>
        `;
        return;
    }
    
    let html = '';
    pendingForms.forEach(form => {
        const dueDate = new Date(form.dueDate);
        const today = new Date();
        const daysLeft = Math.ceil((dueDate - today) / (1000 * 60 * 60 * 24));
        
        let dueDateClass = 'text-muted';
        if (daysLeft <= 3) {
            dueDateClass = 'text-danger';
        } else if (daysLeft <= 7) {
            dueDateClass = 'text-warning';
        }
        
        html += `
            <tr>
                <td>
                    <div class="d-flex align-items-center">
                        <i class="bi bi-file-earmark-text me-2 text-muted"></i>
                        <div>
                            <div class="fw-bold">${form.courseName}</div>
                            <div class="small text-muted">${form.courseCode}</div>
                        </div>
                    </div>
                </td>
                <td>${form.instructorName}</td>
                <td class="${dueDateClass}">${formatDate(form.dueDate)}</td>
                <td><span class="badge bg-primary">Pending</span></td>
                <td>
                    <a href="feedback.html?formId=${form.id}" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-pencil-square me-1"></i> Submit
                    </a>
                </td>
            </tr>
        `;
    });
    
    tableBody.innerHTML = html;
}

/**
 * Load courses for a student
 * @param {number} studentId - Student ID
 */
function loadStudentCourses() {
    getCourses()
        .then(response => {
            if (!response.errorMessage) {
                displayStudentCourses(response);
            } else {
                document.getElementById('coursesContainer').innerHTML = `
                    <div class="col-12 text-center py-4 text-danger">
                        <i class="bi bi-exclamation-triangle display-4"></i>
                        <p class="mt-3 mb-0">Failed to load courses. Please try again later.</p>
                    </div>
                `;
            }
        })
        .catch(error => {
            console.error('Error loading courses:', error);
            document.getElementById('coursesContainer').innerHTML = `
                <div class="col-12 text-center py-4 text-danger">
                    <i class="bi bi-exclamation-triangle display-4"></i>
                    <p class="mt-3 mb-0">Failed to load courses. Please try again later.</p>
                </div>
            `;
        });
}

/**
 * Display student courses
 * @param {Array} courses - Array of course objects
 */
function displayStudentCourses(courses) {
    const container = document.getElementById('coursesContainer');
    
    if (courses.length === 0) {
        container.innerHTML = `
            <div class="col-12 text-center py-4">
                <i class="bi bi-book display-4 text-muted"></i>
                <p class="mt-3 mb-0">No courses found for this semester.</p>
            </div>
        `;
        return;
    }
    
    let html = '';
    courses.forEach(course => {
        html += `
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card m-3 h-100">
                    <div class="card-body">
                        <h5 class="card-title">${course.code}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">${course.name}</h6>
                        <div class="mb-3">
                            ${generateStarRating(course.rating)}
                        </div>
                        <p class="card-text small">Instructor: ${course.instructorName}</p>
                    </div>
                    <div class="card-footer bg-transparent text-end">
                        <button class="btn btn-sm btn-outline-primary view-course-btn" data-course-id="${course.id}">
                            <i class="bi bi-info-circle me-1"></i> Details
                        </button>
                    </div>
                </div>
            </div>
        `;
    });
    
    container.innerHTML = html;
    
    // Add event listeners to view course buttons
    document.querySelectorAll('.view-course-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const courseId = this.getAttribute('data-course-id');
            showCourseDetails(courseId, courses);
        });
    });
}

/**
 * Show course details in modal
 * @param {number} courseId - Course ID
 * @param {Array} courses - Array of course objects
 */
function showCourseDetails(courseId, courses) {
    // Find the course in the array
    const course = courses.find(c => c.id == courseId);
    
    if (course) {
        // Update modal content
        document.getElementById('modalCourseName').textContent = course.name;
        document.getElementById('modalCourseCode').textContent = course.code;
        document.getElementById('modalCourseDepartment').textContent = course.department;
        document.getElementById('modalCourseSemester').textContent = course.semester;
        document.getElementById('modalCourseRating').innerHTML = generateStarRating(course.rating);
        document.getElementById('modalCourseFeedbacks').textContent = `Based on ${course.feedbackCount} student feedbacks`;
        document.getElementById('modalCourseInstructor').textContent = course.instructorName;
        document.getElementById('modalCourseDescription').textContent = course.description;
        
        // Show modal
        const modal = new bootstrap.Modal(document.getElementById('courseDetailsModal'));
        modal.show();
    } else {
        showError('Course details not found.');
    }
}

/**
 * Initialize the feedback form
 * @param {number} formId - Form ID
 */
function initFeedbackForm(formId) {
    getFeedbackForm(formId)
        .then(form => {
            if (form) {
                // Display form details
                displayFeedbackFormDetails(form);
                
                // Display form questions
                displayFeedbackQuestions(form.questions);
            } else {
                showError('Failed to load feedback form.');
                setTimeout(() => {
                    window.location.href = 'dashboard.html';
                }, 2000);
            }
        })
        .catch(error => {
            console.error('Error loading feedback form:', error);
            showError('An error occurred while loading the feedback form.');
            setTimeout(() => {
                window.location.href = 'dashboard.html';
            }, 2000);
        });
}

/**
 * Display feedback form details
 * @param {Object} form - Form object
 */
function displayFeedbackFormDetails(form) {
    document.getElementById('courseName').textContent = form.courseName;
    document.getElementById('courseCode').textContent = form.courseCode;
    document.getElementById('instructorName').textContent = `Instructor: ${form.instructorName}`;
    document.getElementById('courseSemester').textContent = `Semester: Fall 2023`;
    document.getElementById('dueDate').textContent = `Due by: ${formatDate(form.dueDate)}`;
}

/**
 * Display feedback form questions
 * @param {Array} questions - Array of question objects
 */
function displayFeedbackQuestions(questions) {
    const container = document.getElementById('questionsContainer');
    container.innerHTML = '';
    
    questions.forEach((question, index) => {
        const questionEl = document.createElement('div');
        questionEl.className = 'mb-4';
        
        // Create question based on type
        switch (question.type) {
            case 'rating':
                questionEl.innerHTML = `
                    <label class="form-label fw-bold">${index + 1}. ${question.text} ${question.required ? '<span class="text-danger">*</span>' : ''}</label>
                    <div class="star-rating-container mb-3">
                        ${generateStarRating(0, true, `question_${question.formName}`)}
                    </div>
                    <div class="invalid-feedback">Please provide a rating.</div>
                `;
                break;
                
            case 'text':
                questionEl.innerHTML = `
                    <label for="question_${question.formName}" class="form-label fw-bold">${index + 1}. ${question.text} ${question.required ? '<span class="text-danger">*</span>' : ''}</label>
                    <textarea class="form-control" id="question_${question.formName}" rows="3" ${question.required ? 'required' : ''}></textarea>
                    <div class="invalid-feedback">Please provide your feedback.</div>
                `;
                break;
                
            case 'multiple-choice':
                let options = '';
                question.options.forEach((option, optIndex) => {
                    options += `
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="question_${question.formName}" id="question_${question.formName}_option_${optIndex}" value="${option}" ${question.required ? 'required' : ''}>
                            <label class="form-check-label" for="question_${question.formName}_option_${optIndex}">
                                ${option}
                            </label>
                        </div>
                    `;
                });
                
                questionEl.innerHTML = `
                    <fieldset>
                        <legend class="form-label fw-bold">${index + 1}. ${question.text} ${question.required ? '<span class="text-danger">*</span>' : ''}</legend>
                        ${options}
                        <div class="invalid-feedback">Please select an option.</div>
                    </fieldset>
                `;
                break;
        }
        
        container.appendChild(questionEl);
    });
    
    // Add custom styling for interactive rating stars
    document.querySelectorAll('.star-rating-container').forEach(container => {
        const radioInputs = container.querySelectorAll('input[type="radio"]');
        const labels = container.querySelectorAll('label');
        
        // Add event listeners for hovering and clicking stars
        labels.forEach((label, index) => {
            // Highlight stars on hover
            label.addEventListener('mouseenter', () => {
                for (let i = 0; i <= index; i++) {
                    labels[i].classList.add('text-warning');
                }
            });
            
            // Remove highlight when leaving container
            container.addEventListener('mouseleave', () => {
                labels.forEach(label => {
                    if (!label.control.checked) {
                        label.classList.remove('text-warning');
                    }
                });
            });
            
            // Permanent highlight on click
            radioInputs[index].addEventListener('change', () => {
                labels.forEach((l, i) => {
                    if (i <= index) {
                        l.classList.add('text-warning');
                    } else {
                        l.classList.remove('text-warning');
                    }
                });
            });
        });
    });
}

/**
 * Submit feedback form
 * @param {number} formId - Form ID
 */
function submitFeedbackForm(formId) {
    const form = document.getElementById('feedbackForm');
    
    // Collect answers
    const answers = {};
    document.querySelectorAll('[id^="question_"]').forEach(element => {
        const questionId = element.id.replace('question_', '');
        
        if (element.type === 'radio') {
            if (element.checked) {
                answers[questionId] = element.value;
            }
        } else if (element.type === 'textarea') {
            answers[questionId] = element.value;
        }
    });
    
    // Add answers from rating stars
    document.querySelectorAll('.star-rating-container').forEach(container => {
        const radioName = container.querySelector('input[type="radio"]').name;
        const questionId = radioName.replace('question_', '');
        const checkedRadio = container.querySelector('input[type="radio"]:checked');
        
        if (checkedRadio) {
            answers[questionId] = checkedRadio.value;
        }
    });
    
    // Submit the feedback
    submitFeedback(formId, answers)
        .then(response => {
            if (!response.errorMessage && !response.errors) {
                // Show confirmation modal
                const modal = new bootstrap.Modal(document.getElementById('confirmationModal'));
                modal.show();
            } else {
                showError(response.errorMessage || response.errors.join(" | ") || 'Failed to submit feedback. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error submitting feedback:', error);
            showError('An error occurred while submitting feedback. Please try again later.');
        });
}
