document.addEventListener("DOMContentLoaded", function() {
    const errorMessage = document.getElementById('errorMessage').innerText;
    if (errorMessage && errorMessage !== "null") {
        showError(errorMessage);
    }
});

function deleteCategory(name) {
    fetch('/categories?name=' + name, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            window.location.reload();
        } else {
            showError('Failed to delete category');
        }
    });
}

function editCategory(name) {
    window.location.href = '/categories/edit?name=' + name;
}

function showError(message) {
    document.getElementById('errorMessage').innerText = message;
    const errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
    errorModal.show();
}
