function deleteProduct(name) {
    fetch('/products?name=' + name, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            window.location.reload();
        } else {
            alert('Failed to delete product');
        }
    });
}

function editProduct(name) {
    window.location.href = '/products/edit?name=' + name;
}