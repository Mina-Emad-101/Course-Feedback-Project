// Global utility functions for the feedback system

/**
 * Check if user is authenticated and redirect if not
 * @returns {Object|null} User object if authenticated, null otherwise
 */
function checkAuth() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = '/index.html';
        return null;
    }
	console.log(user);
    return user;
}

/**
 * Logout the current user
 */
function logout() {
    localStorage.removeItem('user');
    window.location.href = '/index.html';
}

/**
 * Format date to a readable string
 * @param {string} dateString - ISO date string
 * @returns {string} Formatted date
 */
function formatDate(dateString) {
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
}

/**
 * Generate star rating HTML
 * @param {number} rating - Rating value (0-5)
 * @param {boolean} interactive - Whether stars should be clickable
 * @param {string} inputName - Name for radio inputs if interactive
 * @returns {string} HTML for star rating
 */
function generateStarRating(rating, interactive = false, inputName = '') {
    rating = parseFloat(rating) || 0;
    let html = '<div class="star-rating">';
    
    if (interactive) {
        for (let i = 5; i >= 1; i--) {
            html += `
                <input type="radio" id="${inputName}-${i}" name="${inputName}" value="${i}" ${i === Math.round(rating) ? 'checked' : ''}>
                <label for="${inputName}-${i}"><i class="bi bi-star-fill"></i></label>
            `;
        }
    } else {
        for (let i = 1; i <= 5; i++) {
            if (i <= rating) {
                html += '<i class="bi bi-star-fill"></i>';
            } else if (i - 0.5 <= rating) {
                html += '<i class="bi bi-star-half"></i>';
            } else {
                html += '<i class="bi bi-star"></i>';
            }
        }
        html += ` <span class="small text-muted">(${rating.toFixed(1)})</span>`;
    }
    
    html += '</div>';
    return html;
}

/**
 * Initialize the navigation bar based on user role
 * @param {string} currentPage - Current page name for highlighting
 */
function initNavbar(currentPage) {
    const user = checkAuth();
    if (!user) return;
    
    const navbar = document.getElementById('mainNavbar');
    if (!navbar) return;
    
    // Set role-specific styling
    document.querySelector('body').classList.add(`${user.role.name.toLowerCase()}-theme`);
    
    // Initialize user info
    const userDisplay = document.getElementById('userDisplay');
    if (userDisplay) {
        userDisplay.innerHTML = `
            <span class="avatar-circle">${user.email.charAt(0).toUpperCase()}</span>
            <div class="ms-2">
                <div class="fw-bold">${user.email}</div>
                <div class="small text-muted text-capitalize">${user.role.name}</div>
            </div>
        `;
    }
    
    // Highlight current page
    const activeLink = navbar.querySelector(`[data-page="${currentPage}"]`);
    if (activeLink) {
        activeLink.classList.add('active');
    }
    
    // Initialize logout button
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', logout);
    }
}

/**
 * Create a chart using Chart.js
 * @param {string} canvasId - Canvas element ID
 * @param {string} type - Chart type (bar, line, pie, etc.)
 * @param {Object} data - Chart data
 * @param {Object} options - Chart options
 * @returns {Chart} Chart instance
 */
function createChart(canvasId, type, data, options = {}) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    
    // Default options
    const defaultOptions = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                position: 'top',
            },
            tooltip: {
                mode: 'index',
                intersect: false,
            }
        }
    };
    
    // Merge default options with provided options
    const chartOptions = { ...defaultOptions, ...options };
    
    return new Chart(ctx, {
        type: type,
        data: data,
        options: chartOptions
    });
}

/**
 * Display an error message
 * @param {string} message - Error message to display
 * @param {string} elementId - ID of element to show error in
 */
function showError(message, elementId = 'errorMessage') {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.innerHTML = `
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
    } else {
        console.error(message);
    }
}

/**
 * Display a success message
 * @param {string} message - Success message to display
 * @param {string} elementId - ID of element to show message in
 */
function showSuccess(message, elementId = 'successMessage') {
    const successElement = document.getElementById(elementId);
    if (successElement) {
        successElement.innerHTML = `
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i>
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
    }
}

/**
 * Export data to CSV format
 * @param {Array} data - Array of objects to export
 * @param {string} filename - File name for the download
 */
function exportToCSV(data, filename) {
    if (!data || !data.length) {
        showError('No data to export');
        return;
    }
    
    // Get headers from the first object
    const headers = Object.keys(data[0]);
    
    // Create CSV content
    let csvContent = headers.join(',') + '\n';
    
    data.forEach(item => {
        const values = headers.map(header => {
            const value = item[header];
            // Handle string values that might contain commas
            return typeof value === 'string' && value.includes(',') 
                ? `"${value}"`
                : value;
        });
        csvContent += values.join(',') + '\n';
    });
    
    // Create download link
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.setAttribute('href', url);
    link.setAttribute('download', filename);
    link.style.visibility = 'hidden';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}
