// Global variables
let currentUser = null;
const courses = [
    { id: 1, code: "CS101", name: "Introduction to Computer Science", instructor: "Dr. Smith" },
    { id: 2, code: "MATH201", name: "Calculus II", instructor: "Prof. Johnson" },
    { id: 3, code: "ENG102", name: "Academic Writing", instructor: "Dr. Williams" }
];

// Sample feedback data
const feedbackData = {
    "CS101": {
        ratings: [4, 5, 3, 4, 5, 4, 3, 5, 4, 5],
        comments: [
            "Great course, learned a lot!",
            "The instructor was very knowledgeable.",
            "Some assignments were too difficult."
        ]
    },
    "MATH201": {
        ratings: [3, 4, 2, 3, 4, 3, 2, 4, 3, 4],
        comments: [
            "Challenging but rewarding.",
            "Could use more examples in lectures.",
            "Office hours were very helpful."
        ]
    },
    "ENG102": {
        ratings: [5, 4, 5, 5, 4, 5, 4, 5, 5, 4],
        comments: [
            "Best writing course I've taken!",
            "Feedback on assignments was very detailed.",
            "Really improved my writing skills."
        ]
    }
};

// Utility functions
function calculateAverageRating(ratings) {
    if (ratings.length === 0) return 0;
    const sum = ratings.reduce((a, b) => a + b, 0);
    return (sum / ratings.length).toFixed(1);
}

function getRandomColor() {
    const colors = ['#4361ee', '#3f37c9', '#4895ef', '#4cc9f0', '#f72585', '#b5179e', '#7209b7'];
    return colors[Math.floor(Math.random() * colors.length)];
}

// Initialize the app
document.addEventListener('DOMContentLoaded', function() {
    // Check if we're on a dashboard page
    if (document.querySelector('.dashboard')) {
        initializeDashboard();
    }
    
    // Initialize rating stars
    document.querySelectorAll('.rating-stars .star').forEach(star => {
        star.addEventListener('click', function() {
            const stars = this.parentElement.querySelectorAll('.star');
            const rating = parseInt(this.getAttribute('data-value'));
            
            stars.forEach((s, index) => {
                if (index < rating) {
                    s.classList.add('active');
                } else {
                    s.classList.remove('active');
                }
            });
            
            // Update hidden input if exists
            const hiddenInput = this.parentElement.nextElementSibling;
            if (hiddenInput && hiddenInput.type === 'hidden') {
                hiddenInput.value = rating;
            }
        });
    });
});

function initializeDashboard() {
    // Load user data from session (simulated)
    const userType = window.location.pathname.split('/')[1];
    currentUser = {
        id: 1,
        name: userType === 'student' ? 'John Doe' : userType === 'instructor' ? 'Dr. Smith' : 'Admin User',
        email: `${userType}@university.edu`,
        type: userType
    };
    
    document.getElementById('user-name').textContent = currentUser.name;
    document.getElementById('user-email').textContent = currentUser.email;
    
    // Load appropriate content based on user type
    if (currentUser.type === 'student') {
        loadStudentDashboard();
    } else if (currentUser.type === 'instructor') {
        loadInstructorDashboard();
    } else if (currentUser.type === 'admin') {
        loadAdminDashboard();
    }
}

function loadStudentDashboard() {
    // Load courses available for feedback
    const courseList = document.getElementById('course-list');
    if (courseList) {
        courses.forEach(course => {
            const courseCard = document.createElement('div');
            courseCard.className = 'card';
            courseCard.innerHTML = `
                <h3>${course.code} - ${course.name}</h3>
                <p>Instructor: ${course.instructor}</p>
                <button class="btn-primary" onclick="location.href='feedback.html?course=${course.code}'">Provide Feedback</button>
            `;
            courseList.appendChild(courseCard);
        });
    }
}

function loadInstructorDashboard() {
    // Load feedback summary for instructor's courses
    const summarySection = document.getElementById('feedback-summary');
    if (summarySection) {
        // Simulate instructor only seeing their own courses
        const instructorCourses = courses.filter(c => c.instructor === currentUser.name);
        
        if (instructorCourses.length === 0) {
            summarySection.innerHTML = '<p>No courses assigned to you.</p>';
            return;
        }
        
        instructorCourses.forEach(course => {
            const feedback = feedbackData[course.code] || { ratings: [], comments: [] };
            const avgRating = calculateAverageRating(feedback.ratings);
            
            const summaryCard = document.createElement('div');
            summaryCard.className = 'card summary-card';
            summaryCard.innerHTML = `
                <div class="summary-icon">📊</div>
                <div class="summary-info">
                    <h3>${course.code} - ${course.name}</h3>
                    <div class="rating">
                        <span class="average-rating">${avgRating}</span>/5 (${feedback.ratings.length} responses)
                    </div>
                    <div class="progress-bar">
                        <div class="progress" style="width: ${(avgRating / 5) * 100}%"></div>
                    </div>
                    <button class="btn-secondary" onclick="viewFeedbackDetails('${course.code}')">View Details</button>
                </div>
            `;
            summarySection.appendChild(summaryCard);
        });
    }
}

function loadAdminDashboard() {
    // Load all feedback data for admin
    const adminSummary = document.getElementById('admin-summary');
    if (adminSummary) {
        courses.forEach(course => {
            const feedback = feedbackData[course.code] || { ratings: [], comments: [] };
            const avgRating = calculateAverageRating(feedback.ratings);
            
            const summaryCard = document.createElement('div');
            summaryCard.className = 'card summary-card';
            summaryCard.innerHTML = `
                <div class="summary-icon">📝</div>
                <div class="summary-info">
                    <h3>${course.code} - ${course.name}</h3>
                    <p>Instructor: ${course.instructor}</p>
                    <div class="rating">
                        <span class="average-rating">${avgRating}</span>/5 (${feedback.ratings.length} responses)
                    </div>
                    <div class="progress-bar">
                        <div class="progress" style="width: ${(avgRating / 5) * 100}%"></div>
                    </div>
                    <div class="admin-actions">
                        <button class="btn-secondary" onclick="viewFeedbackDetails('${course.code}')">View Feedback</button>
                        <button class="btn-secondary" onclick="exportFeedback('${course.code}')">Export</button>
                    </div>
                </div>
            `;
            adminSummary.appendChild(summaryCard);
        });
    }
}

// Functions for all user types
function viewFeedbackDetails(courseCode) {
    // In a real app, this would fetch detailed feedback for the course
    const course = courses.find(c => c.code === courseCode);
    const feedback = feedbackData[courseCode] || { ratings: [], comments: [] };
    const avgRating = calculateAverageRating(feedback.ratings);
    
    // Create modal with feedback details
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <span class="close-modal">&times;</span>
            <h2>Feedback Details: ${courseCode} - ${course.name}</h2>
            <p>Instructor: ${course.instructor}</p>
            
            <div class="feedback-stats">
                <div class="stat-card">
                    <h3>Average Rating</h3>
                    <div class="stat-value">${avgRating}/5</div>
                </div>
                <div class="stat-card">
                    <h3>Total Responses</h3>
                    <div class="stat-value">${feedback.ratings.length}</div>
                </div>
            </div>
            
            <h3>Rating Distribution</h3>
            <div class="rating-distribution">
                ${[5, 4, 3, 2, 1].map(star => {
                    const count = feedback.ratings.filter(r => r === star).length;
                    const percentage = feedback.ratings.length > 0 ? (count / feedback.ratings.length) * 100 : 0;
                    return `
                        <div class="star-row">
                            <span>${star} ★</span>
                            <div class="bar-container">
                                <div class="bar" style="width: ${percentage}%; background: ${getRandomColor()}"></div>
                            </div>
                            <span>${count} (${percentage.toFixed(1)}%)</span>
                        </div>
                    `;
                }).join('')}
            </div>
            
            <h3>Student Comments</h3>
            <div class="comments-section">
                ${feedback.comments.length > 0 ? 
                    feedback.comments.map(comment => `<div class="comment-card">"${comment}"</div>`).join('') : 
                    '<p>No comments available.</p>'}
            </div>
            
            ${currentUser.type === 'admin' ? `
                <div class="modal-actions">
                    <button class="btn-primary" onclick="exportFeedback('${courseCode}')">Export Feedback</button>
                </div>
            ` : ''}
        </div>
    `;
    
    document.body.appendChild(modal);
    
    // Close modal
    modal.querySelector('.close-modal').addEventListener('click', () => {
        document.body.removeChild(modal);
    });
    
    // Close when clicking outside
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            document.body.removeChild(modal);
        }
    });
}

function exportFeedback(courseCode) {
    // In a real app, this would generate a CSV or PDF report
    console.log(`Exporting feedback for ${courseCode}`);
    alert(`Feedback for ${courseCode} exported successfully!`);
}

// Form submission
function submitFeedbackForm(event) {
    event.preventDefault();
    
    const form = event.target;
    const courseCode = new URLSearchParams(window.location.search).get('course');
    const rating = form.querySelector('input[name="rating"]').value;
    const comments = form.querySelector('textarea[name="comments"]').value;
    
    // In a real app, this would send data to the server
    console.log(`Feedback submitted for ${courseCode}: Rating=${rating}, Comments=${comments}`);
    
    // Show success message
    alert('Thank you for your feedback! Your response has been recorded anonymously.');
    
    // Redirect back to dashboard
    setTimeout(() => {
        window.location.href = 'dashboard.html';
    }, 1500);
}

// Admin functions
function createFeedbackForm(event) {
    event.preventDefault();
    
    const form = event.target;
    const formName = form.querySelector('input[name="formName"]').value;
    const courseCode = form.querySelector('select[name="courseCode"]').value;
    const questions = form.querySelector('textarea[name="questions"]').value;
    
    // In a real app, this would send data to the server
    console.log(`New feedback form created: ${formName} for ${courseCode}`);
    console.log(`Questions: ${questions}`);
    
    // Show success message
    alert('Feedback form created successfully!');
    
    // Reset form
    form.reset();
}