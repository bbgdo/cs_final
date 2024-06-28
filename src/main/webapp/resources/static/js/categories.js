function deleteCategory(name) {
    fetch('/categories?name=' + name, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            window.location.reload();
        } else {
            alert('Failed to delete category');
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