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