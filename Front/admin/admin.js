// Admin-specific JavaScript functions

/**
 * Initialize the admin dashboard
 */
function initAdminDashboard() {
    // Load recent forms
    loadRecentForms();
}

/**
 * Load recent feedback forms
 */
function loadRecentForms() {
    const user = checkAuth();
    if (!user) return;
    
    getAdminForms()
        .then(response => {
            if (!response.errorMessage) {
                // Get the 5 most recent forms
                const recentForms = response.slice(0, 5);
                
                // Update the table
                const tableBody = document.getElementById('recentFormsTable');
                
                if (recentForms.length === 0) {
                    tableBody.innerHTML = `
                        <tr>
                            <td colspan="5" class="text-center py-4">No forms created yet</td>
                        </tr>
                    `;
                    return;
                }
                
                let html = '';
                recentForms.forEach(form => {
                    const statusClass = form.status === 'active' ? 'success' : 'secondary';
                    
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
                            <td>${formatDate(form.dueDate)}</td>
                            <td><span class="badge bg-${statusClass}">${form.status}</span></td>
                            <td>
                                <div class="d-flex align-items-center">
                                    <span class="small text-nowrap">${form.responseCount}</span>
                                </div>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-outline-primary view-form-btn" data-form-id="${form.id}">
                                    <i class="bi bi-eye"></i>
                                </button>
                            </td>
                        </tr>
                    `;
                });
                
                tableBody.innerHTML = html;
                
                // Add event listeners to view buttons
                document.querySelectorAll('.view-form-btn').forEach(btn => {
                    btn.addEventListener('click', function() {
                        const formId = this.getAttribute('data-form-id');
                        window.location.href = `monitor.html?formId=${formId}`;
                    });
                });
            }
        })
        .catch(error => {
            console.error('Error loading recent forms:', error);
            document.getElementById('recentFormsTable').innerHTML = `
                <tr>
                    <td colspan="5" class="text-center py-4 text-danger">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Failed to load forms. Please try again later.
                    </td>
                </tr>
            `;
        });
}

/**
 * Initialize the course selection dropdown
 */
function initCourseSelection() {
    const user = checkAuth();
    if (!user) return;
    
    getCourses()
        .then(response => {
            if (!response.errorMessage) {
                const courseSelect = document.getElementById('courseSelect');
                response.forEach(course => {
                    const option = document.createElement('option');
                    option.value = course.id;
                    option.textContent = `${course.code} - ${course.name}`;
                    courseSelect.appendChild(option);
                });
            }
        })
        .catch(error => {
            console.error('Error loading courses:', error);
            showError('Failed to load courses. Please try again later.');
        });
}

/**
 * Handle form creation submission
 */
function createFeedbackFormSubmit() {
    const user = checkAuth();
    if (!user) return;
    
    // Gather form data
    const formData = {
        courseID: document.getElementById('courseSelect').value,
        deadline: document.getElementById('dueDate').value,
    };
    // Submit the form
    createFeedbackForm(formData)
        .then(response => {
            if (!response.errorMessage && !response.errors) {
                showSuccess("Success");
                
                // Clear form after successful submission
                setTimeout(() => {
                    window.location.href = 'monitor.html';
                }, 1500);
            } else {
                showError(response.errorMessage || response.errors.join(" | ") || 'Failed to create form. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error creating form:', error);
            showError('An error occurred while creating the form. Please try again later.');
        });
}

/**
 * Initialize the monitor page
 */
function initMonitorPage() {
    const user = checkAuth();
    if (!user) return;
    
    // Check if there's a specific form to view from URL
    const urlParams = new URLSearchParams(window.location.search);
    const formId = urlParams.get('formId');
    
    if (formId) {
        // TODO: If a specific form ID is provided, open its details modal
        // This would require additional API functions to get specific form details
    }
    
    // Load all forms
    loadForms();
}

/**
 * Load all feedback forms
 */
function loadForms() {
    const user = checkAuth();
    if (!user) return;
    
    document.getElementById('formsTable').innerHTML = `
        <tr>
            <td colspan="7" class="text-center py-4">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <p class="mt-2 mb-0">Loading forms...</p>
            </td>
        </tr>
    `;
    
    getAdminForms()
        .then(response => {
            if (!response.errorMessage) {
                displayForms(response);
            } else {
                document.getElementById('formsTable').innerHTML = `
                    <tr>
                        <td colspan="7" class="text-center py-4 text-danger">
                            <i class="bi bi-exclamation-triangle me-2"></i>
                            Failed to load forms. Please try again later.
                        </td>
                    </tr>
                `;
            }
        })
        .catch(error => {
            console.error('Error loading forms:', error);
            document.getElementById('formsTable').innerHTML = `
                <tr>
                    <td colspan="7" class="text-center py-4 text-danger">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Failed to load forms. Please try again later.
                    </td>
                </tr>
            `;
        });
}

/**
 * Display forms in the table
 * @param {Array} forms - Array of form objects
 */
function displayForms(forms) {
    const tableBody = document.getElementById('formsTable');
    
    if (forms.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center py-4">
                    <i class="bi bi-inbox display-4 text-muted"></i>
                    <p class="mt-3 mb-0">No forms found. Try changing filters or <a href="create-form.html">create a new form</a>.</p>
                </td>
            </tr>
        `;
        return;
    }
    
    let html = '';
    forms.forEach(form => {
        const statusClass = form.status === 'active' ? 'success' : 'secondary';
        
        html += `
            <tr>
                <td>
                    <div class="fw-bold">${form.title}</div>
                </td>
                <td>
                    <div>${form.courseName}</div>
                    <div class="small text-muted">${form.courseCode}</div>
                </td>
                <td>${formatDate(form.createdDate)}</td>
                <td>${formatDate(form.dueDate)}</td>
                <td><span class="badge bg-${statusClass}">${form.status}</span></td>
                <td>
                    <div class="d-flex align-items-center">
                        <span class="small text-nowrap">${form.responseCount}</span>
                    </div>
                </td>
                <td>
                    <div class="btn-group">
                        <button type="button" class="btn btn-sm btn-outline-primary view-details-btn" data-form-id="${form.id}">
                            <i class="bi bi-eye"></i>
                        </button>
                        <button type="button" class="btn btn-sm btn-outline-secondary" onclick="exportFormData(${form.id})">
                            <i class="bi bi-download"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });
    
    tableBody.innerHTML = html;
    
    // Add event listeners to view details buttons
    document.querySelectorAll('.view-details-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const formId = this.getAttribute('data-form-id');
            showFormDetails(formId, forms.find(f => f.id == formId));
        });
    });
}

/**
 * Export forms data to CSV
 */
function exportFormsData() {
    const user = checkAuth();
    if (!user) return;
    
    getAdminForms()
        .then(response => {
            if (!response.errorMessage) {
                // Format forms data for CSV
                const formsData = response.map(form => ({
                    Course: form.course.name,
                    CourseCode: form.course.code,
                    Created: formatDate(form.createdAt),
                    DueDate: formatDate(form.deadline),
                    Status: form.isActive ? "active" : "closed",
                    Responses: form.responseCount,
                }));
                
                exportToCSV(formsData, 'feedback_forms.csv');
                showSuccess('Forms data exported successfully.');
            } else {
                showError('Failed to export forms data.');
            }
        })
        .catch(error => {
            console.error('Error exporting forms data:', error);
            showError('An error occurred while exporting forms data.');
        });
}

/**
 * Export data for a specific form
 * @param {number} formId - Form ID
 */
function exportFormData(formId) {
    // In a real app, this would fetch detailed form data from the API
    // For this demo, we'll just show a success message
    showSuccess(`Form #${formId} data exported successfully.`);
}
/**
 * Show form details in a modal
 * @param {number} formId - Form ID
 * @param {Object} formData - Form data object (if available)
 */
function showFormDetails(formId, formData) {
    // If formData is provided, use it, otherwise we'd need to fetch it
    if (formData) {
        // Populate modal with form data
        document.getElementById('modalFormTitle').textContent = formData.title;
        document.getElementById('modalFormCourse').textContent = `${formData.courseCode} - ${formData.courseName}`;
        document.getElementById('modalFormCreated').textContent = formatDate(formData.createdDate);
        document.getElementById('modalFormStatus').textContent = formData.status;
        document.getElementById('modalFormDueDate').textContent = formatDate(formData.dueDate);
        
        // Set form ID for export button
        document.getElementById('modalExportBtn').setAttribute('data-form-id', formId);
        
        
        // Show modal
        const modal = new bootstrap.Modal(document.getElementById('formDetailsModal'));
        modal.show();
    } else {
        // In a real app, we would fetch the form details from the API
        // For this demo, we'll just show a placeholder message
        showError('Form details not available.');
    }
}
/**
 * Export a report for a specific form
 * @param {number} formId - Form ID
 */
function exportFormReport(formId) {
    // In a real app, this would fetch detailed form data from the API
    // For this demo, we'll just show a success message
    showSuccess(`Form #${formId} report exported successfully.`);
    
    // Close the modal
    const modal = bootstrap.Modal.getInstance(document.getElementById('formDetailsModal'));
    modal.hide();
}
