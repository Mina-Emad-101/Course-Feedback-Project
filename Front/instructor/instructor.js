// Instructor-specific JavaScript functions

/**
 * Initialize the instructor dashboard
 */
function initInstructorDashboard() {
    const user = checkAuth();
    if (!user) return;
    
    // Get instructor statistics
    getInstructorStatistics(user.id)
        .then(response => {
            if (!response.errorMessage) {
                const stats = response;
                
                // Update instructor overview
                document.getElementById('instructorName').textContent = user.username;
                document.getElementById('overallRating').innerHTML = generateStarRating(stats.overallRating);
                document.getElementById('totalFeedbacks').textContent = `Based on ${stats.totalFeedbacks} student feedbacks`;
            } else {
                showError('Failed to load instructor statistics.');
            }
        })
        .catch(error => {
            console.error('Error loading instructor statistics:', error);
            showError('An error occurred while loading statistics. Please try again later.');
        });
    
    // Load instructor courses
    loadInstructorCourses(user.id);
}

/**
 * Load instructor courses
 * @param {number} instructorId - Instructor ID
 */
function loadInstructorCourses(instructorId) {
    getCourses()
        .then(response => {
            if (!response.errorMessage) {
                displayInstructorCourses(response);
            } else {
                document.getElementById('coursesTable').innerHTML = `
                    <tr>
                        <td colspan="6" class="text-center py-4 text-danger">
                            <i class="bi bi-exclamation-triangle me-2"></i>
                            Failed to load courses. Please try again later.
                        </td>
                    </tr>
                `;
            }
        })
        .catch(error => {
            console.error('Error loading instructor courses:', error);
            document.getElementById('coursesTable').innerHTML = `
                <tr>
                    <td colspan="6" class="text-center py-4 text-danger">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Failed to load courses. Please try again later.
                    </td>
                </tr>
            `;
        });
}

/**
 * Display instructor courses in the table
 * @param {Array} courses - Array of course objects
 */
function displayInstructorCourses(courses) {
    const tableBody = document.getElementById('coursesTable');
    
    if (courses.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center py-4">
                    <i class="bi bi-mortarboard display-4 text-muted"></i>
                    <p class="mt-3 mb-0">No courses found.</p>
                </td>
            </tr>
        `;
        return;
    }
    
    let html = '';
    courses.forEach(course => {
        html += `
            <tr>
                <td>
                    <div class="d-flex align-items-center">
                        <i class="bi bi-book me-2 text-muted"></i>
                        <div>
                            <div class="fw-bold">${course.name}</div>
                            <div class="small text-muted">${course.code}</div>
                        </div>
                    </div>
                </td>
                <td>${course.department}</td>
                <td>${course.semester}</td>
                <td>${generateStarRating(course.rating)}</td>
                <td>${course.feedbackCount} responses</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary view-course-btn" data-course-id="${course.id}">
                        <i class="bi bi-eye me-1"></i> View
                    </button>
                </td>
            </tr>
        `;
    });
    
    tableBody.innerHTML = html;
    
    // Add event listeners to view course buttons
    document.querySelectorAll('.view-course-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const courseId = this.getAttribute('data-course-id');
            showCourseDetails(courseId);
        });
    });
}

/**
 * Show course details in modal
 * @param {number} courseId - Course ID
 */
function showCourseDetails(courseId) {
    // Get course statistics
    getCourseStatistics(courseId)
        .then(response => {
            if (!response.errorMessage) {
                const stats = response;
                
                // Update modal content
                document.getElementById('modalCourseName').textContent = stats.courseName;
                document.getElementById('modalCourseCode').textContent = stats.courseCode;
                document.getElementById('modalCourseSemester').textContent = `Semester: ${stats.semester}`;
                document.getElementById('modalCourseRating').innerHTML = generateStarRating(stats.overallRating);
                document.getElementById('modalCourseFeedbacks').textContent = `Based on ${stats.totalFeedbacks} student feedbacks`;
                // Show modal
                const modal = new bootstrap.Modal(document.getElementById('courseDetailsModal'));
                modal.show();
            } else {
                showError('Failed to load course statistics.');
            }
        })
        .catch(error => {
            console.error('Error loading course statistics:', error);
            showError('An error occurred while loading course statistics.');
        });
}

/**
 * Export courses data to CSV
 */
function exportCoursesData() {
    const user = checkAuth();
    if (!user) return;
    
    getCourses()
        .then(response => {
            if (!response.errorMessage) {
                // Format courses data for CSV
                const coursesData = response.map(course => ({
                    'Course Name': course.name,
                    'Course Code': course.code,
                    'Department': course.department,
                    'Semester': course.semester,
                    'Rating': course.rating,
                    'Feedback Count': course.feedbackCount
                }));
                
                exportToCSV(coursesData, 'instructor_courses.csv');
                showSuccess('Courses data exported successfully.');
            } else {
                showError('Failed to export courses data.');
            }
        })
        .catch(error => {
            console.error('Error exporting courses data:', error);
            showError('An error occurred while exporting courses data.');
        });
}

/**
 * Export course report
 * @param {number} courseId - Course ID
 */
function exportCourseReport(courseId) {
    getCourseStatistics(courseId)
        .then(response => {
            if (!response.errorMessages) {
                const stats = statistics;
                
                // Format data for CSV
                const courseData = {
                    'Course Name': stats.courseName,
                    'Course Code': stats.courseCode,
                    'Semester': stats.semester,
                    'Overall Rating': stats.overallRating,
                    'Total Feedbacks': stats.totalFeedbacks
                };
                
                const courseDataArray = [courseData];
                
                // Export to CSV
                exportToCSV(courseDataArray, `course_report_${stats.courseCode}.csv`);
                
                showSuccess('Course report exported successfully.');
                
                // Close modal
                const modal = bootstrap.Modal.getInstance(document.getElementById('courseDetailsModal'));
                modal.hide();
            } else {
                showError('Failed to export course report.');
            }
        })
        .catch(error => {
            console.error('Error exporting course report:', error);
            showError('An error occurred while exporting course report.');
        });
}

/**
 * Initialize the statistics page
 */
function initStatisticsPage() {
    const user = checkAuth();
    if (!user) return;
    
    // Load course filter options
    loadCourseFilterOptions();
}

/**
 * Load course filter options for statistics page
 * @param {number} instructorId - Instructor ID
 */
function loadCourseFilterOptions() {
    getCourses()
        .then(response => {
            if (!response.errorMessage) {
				console.log(response);
                const courseFilter = document.getElementById('courseFilter');
                response.forEach(course => {
                    const option = document.createElement('option');
                    option.value = course.id;
                    option.textContent = `${course.code} - ${course.name}`;
                    courseFilter.appendChild(option);
                });
            }
        })
        .catch(error => {
            console.error('Error loading course filter options:', error);
        });
}

/**
 * Create course comparison chart
 * @param {Array} courseRatings - Course ratings data
 */
function createCourseComparisonChart(courseRatings) {
    const labels = courseRatings.map(course => course.courseCode);
    const data = courseRatings.map(course => course.rating);
    
    createChart('courseComparisonChart', 'bar', {
        labels: labels,
        datasets: [{
            label: 'Course Rating',
            data: data,
            backgroundColor: 'rgba(25, 135, 84, 0.7)',
            borderColor: 'rgba(25, 135, 84, 1)',
            borderWidth: 1
        }]
    }, {
        scales: {
            y: {
                min: 0,
                max: 5,
                title: {
                    display: true,
                    text: 'Rating (1-5)'
                }
            }
        }
    });
}
