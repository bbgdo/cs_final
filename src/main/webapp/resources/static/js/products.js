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

function addAmount(name) {
    const amount = document.getElementById('amount-' + name).value;
    if (amount && amount > 0) {
        fetch('/products?action=addAmount&name=' + encodeURIComponent(name) + '&amount=' + encodeURIComponent(amount), {
            method: 'POST'
        }).then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Failed to add amount');
            }
        });
    }
}

function writeOff(name) {
    const amount = document.getElementById('amount-' + name).value;
    if (amount && amount > 0) {
        fetch('/products?action=writeOff&name=' + encodeURIComponent(name) + '&amount=' + encodeURIComponent(amount), {
            method: 'POST'
        }).then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Failed to write off amount');
            }
        });
    }
}

function showDetails(name, description, amount, price) {
    document.getElementById('productDescription').innerText = description;
    document.getElementById('productAmount').innerText = amount;
    document.getElementById('productPrice').innerText = price;
    document.getElementById('productTotalValue').innerText = amount * price;

    var myModal = new bootstrap.Modal(document.getElementById('productDetailsModal'));
    myModal.show();
}

function showError(message) {
    document.getElementById('errorMessage').innerText = message;
    var errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
    errorModal.show();
}