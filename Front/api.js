// API functions for the feedback system

// Using mock data only (no actual API calls)
const USE_MOCK_DATA = false;
const USERS_API_URL = "http://127.0.0.1:8080";
const FORMS_API_URL = "http://127.0.0.1:8081";
const Method = {
  GET: "GET",
  POST: "POST",
  PUT: "PUT",
  DELETE: "DELETE",
};

/**
 * Handle API errors
 * @param {Error} error - Error object
 * @returns {Object} Error response object
 */
function handleApiError(error) {
  console.error("API Error:", error);
  return {
    success: false,
    message: "An error occurred. Please try again later.",
  };
}

/**
 * @param {String} method
 * @param {String} path
 * @param {Object|null} body
 */
async function usersServiceFetch(method, path, body) {
  const response = await fetch(`${USERS_API_URL}${path}`, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("user"))?.token}`,
    },
    body: body ? JSON.stringify(body) : null,
  });
  const result = await response.json();
  console.log(result);
  return result;
}

/**
 * @param {String} method
 * @param {String} path
 * @param {Object|null} body
 */
async function formsServiceFetch(method, path, body) {
  const response = await fetch(`${FORMS_API_URL}${path}`, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Authorization: `Bearer ${JSON.parse(localStorage.getItem("user"))?.token}`,
    },
    body: body ? JSON.stringify(body) : null,
  });
  const result = await response.json();
  console.log(result);
  return result;
}

/**
 * Login user
 * @param {string} username - User username
 * @param {string} password - User password
 * @param {string} role - User role
 * @returns {Promise<Object>} Login response
 */
async function login(email, password) {
  try {
    // Try to use the real API
    try {
      const response = await fetch(`${USERS_API_URL}/auth/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });
      const result = await response.json();
      return result;
    } catch (apiError) {
      console.error("API call failed:", apiError);
      // If API fails, use mock data with a notification
      document.getElementById("apiErrorNotification").style.display = "block";
      return {
        success: false,
      };
    }
  } catch (error) {
    return handleApiError(error);
  }
}

/**
 * Get courses for a user
 * @param {number} userId - User ID
 * @param {string} role - User role
 * @returns {Promise<Object>} Courses response
 */
async function getCourses() {
  try {
    const response = await formsServiceFetch(Method.GET, "/courses", null);
    const courses = [];

    for (let i = 0; i < response.length; i++) {
      courses.push({
        id: response[i].id,
        code: response[i].code,
        name: response[i].name,
        department: "Computer Science",
        semester: "Fall 2023",
        instructor: response[i].instructor.id,
        instructorName: response[i].instructor.name,
        rating: response[i].averageRating, // Random rating between 2.0 and 5.0
        feedbackCount: response[i].responseCount,
        description: `This is a description for Course ${i}. It covers various topics in computer science.`,
      });
    }

    return courses;
  } catch (error) {
    return handleApiError(error);
  }
}

/**
 * Get feedback forms assigned to a student
 * @param {number} studentId - Student ID
 * @returns {Promise<Object>} Feedback forms response
 */
async function getStudentFeedbackForms() {
  try {
    const response = await formsServiceFetch(Method.GET, "/forms", null);
    console.log(response.length);

    const forms = [];

    for (let i = 0; i < response.length; i++) {
      forms.push({
        id: response[i].id,
        courseId: response[i].course.id,
        courseCode: response[i].course.code,
        courseName: response[i].course.name,
        dueDate: response[i].deadline,
        status: response[i].isActive ? "pending" : "completed",
        instructorName: response[i].course.instructor.name,
        questions: [
          {
            id: 1,
            type: "rating",
            text: "How would you rate the overall quality of this course?",
            formName: "courseRating",
            required: true,
          },
          {
            id: 2,
            type: "rating",
            text: "How would you rate the instructor's teaching effectiveness?",
            formName: "instructorRating",
            required: true,
          },
          {
            id: 3,
            type: "text",
            text: "What suggestions do you have for improving the course?",
            formName: "comment",
            required: false,
          },
        ],
      });
    }
    return forms;
  } catch (error) {
    return handleApiError(error);
  }
}

/**
 * Get a specific feedback form
 * @param {number} formId - Form ID
 * @returns {Promise<Object>} Feedback form response
 */
async function getFeedbackForm(formId) {
  try {
    const response = await formsServiceFetch(
      Method.GET,
      `/forms/${formId}`,
      null,
    );
    const form = {
      id: formId,
      courseId: response.course.id,
      courseCode: response.course.code,
      courseName: response.course.name,
      instructorName: response.course.instructor.name,
      dueDate: response.deadline,
      questions: [
        {
          id: 1,
          type: "rating",
          text: "How would you rate the overall quality of this course?",
          formName: "courseRating",
          required: true,
        },
        {
          id: 2,
          type: "rating",
          text: "How would you rate the instructor's teaching effectiveness?",
          formName: "instructorRating",
          required: true,
        },
        {
          id: 3,
          type: "text",
          text: "What suggestions do you have for improving the course?",
          formName: "comment",
          required: false,
        },
      ],
    };
    return form;
  } catch (error) {
    return handleApiError(error);
  }
}

/**
 * Submit feedback form
 * @param {number} formId - Form ID
 * @param {Object} answers - Form answers
 * @returns {Promise<Object>} Submission response
 */
async function submitFeedback(formId, answers) {
  try {
    const response = await formsServiceFetch(
      Method.POST,
      `/forms/${formId}/respond`,
      answers,
    );
    return response;
  } catch (error) {
    return handleApiError(error);
  }
}

/**
 * Get feedback statistics for instructor
 * @param {number} instructorId - Instructor ID
 * @returns {Promise<Object>} Statistics response
 */
async function getInstructorStatistics(instructorId) {
  try {
    const response = await formsServiceFetch(
      Method.GET,
      `/responses/ratings/instructors/${instructorId}`,
      null,
    );

    const statistics = {
      overallRating: response.averageInstructorRating,
      totalFeedbacks: response.responseCount,
      courseRatings: response.courses.map((course) => {
        const result = {
          courseId: course.id,
          courseCode: course.code,
          courseName: course.name,
          rating: course.averageRating,
          feedbackCount: course.responseCount,
        };

        return result;
      }),
    };
    return statistics;
  } catch (error) {
    return handleApiError(error);
  }
}

/**
 * Get course statistics
 * @param {number} courseId - Course ID
 * @returns {Promise<Object>} Statistics response
 */
async function getCourseStatistics(courseId) {
  try {
    const response = await formsServiceFetch(
      Method.GET,
      `/courses/${courseId}`,
      null,
    );
    const statistics = {
      courseId: courseId,
      courseCode: response.code,
      courseName: response.name,
      instructorName: response.instructor.name,
      semester: "Fall 2023",
      overallRating: response.averageRating,
      totalFeedbacks: response.responseCount,
    };
    return statistics;
  } catch (error) {
    return handleApiError(error);
  }
}

/**
 * Get forms created by admin
 * @param {number} adminId - Admin ID
 * @returns {Promise<Object>} Forms response
 */
async function getAdminForms() {
  try {
    const response = await formsServiceFetch(Method.GET, "/forms", null);
    const forms = [];

    for (let i = 0; i < response.length; i++) {
      forms.push({
        id: response[i].id,
        title: response[i].course.name,
        courseCode: response[i].course.code,
        courseName: response[i].course.name,
        createdDate: new Date(response[i].createdAt).toISOString(),
        dueDate: new Date(response[i].deadline).toISOString(),
        status: response[i].isActive ? "active" : "closed",
        responseCount: response[i].responseCount,
      });
    }
		return forms;
  } catch (error) {
    return handleApiError(error);
  }
}

/**
 * Create a new feedback form
 * @param {Object} formData - Form data
 * @returns {Promise<Object>} Creation response
 */
async function createFeedbackForm(formData) {
  try {
    const response = await formsServiceFetch(Method.POST, "/forms", formData);
		return response;
  } catch (error) {
    return handleApiError(error);
  }
}
