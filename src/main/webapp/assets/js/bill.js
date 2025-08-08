let selectedCustomer = null;

document.addEventListener("DOMContentLoaded", function() {
	// seach customer
	document.getElementById("search-customer-form").addEventListener("submit", function(event) {
		searchCustomer(event, this);
	});

	// confirm customer
	document.getElementById("btn-confirm-customer").addEventListener("click", function(event) {
		confirmCustomer();
	});

});

function searchCustomer(event, form) {
	event.preventDefault(); // Stop the default form submit

	const formData = new FormData(form);

	// Build the query string
	const params = new URLSearchParams(formData).toString();
	const urlWithParams = `${form.action}?${params}`;

	fetch(urlWithParams, {
		method: "GET"
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Network response was not ok");
			}
			return response.json();
		})
		.then(data => {
			// Clear existing error messages
			const errorFields = ["mobileError", "generalError"];
			errorFields.forEach(id => {
				const el = document.getElementById(id);
				if (el) el.textContent = "";
			});

			if (data.success && data.customer) {
				selectedCustomer = data.customer;

				// Populate fields
				document.getElementById("customerId").value = selectedCustomer.id || "";
				document.getElementById("customerName").value = `${selectedCustomer.firstName || ""} ${selectedCustomer.lastName || ""}`.trim();

				// enable confirm customer button
				document.getElementById("btn-confirm-customer").disabled = false;
			} else if (data.errors) {
				// Show validation errors
				for (const [key, msg] of Object.entries(data.errors)) {
					const el = document.getElementById(key);
					if (el) el.textContent = msg;
				}
				document.getElementById("customerId").value = "";
				document.getElementById("customerName").value = "";

				// disable confirm customer button
				document.getElementById("btn-confirm-customer").disabled = true;
				selectedCustomer = null;
			} else {
				alert("An error occurred while searching the customer.");
			}
		})
		.catch(error => {
			alert("There was a problem searching the customer: " + error.message);
		});
}

function confirmCustomer() {
	console.log(selectedCustomer);
	// Populate fields
	document.getElementById("previewCustomerEmail").innerText = selectedCustomer.email || "";
	document.getElementById("previewCustomerName").innerText = `${selectedCustomer.firstName || ""} ${selectedCustomer.lastName || ""}`.trim();

	// enable add bill item button
	document.getElementById("btn-add-bill-item").disabled = false;
	document.getElementById("item-id").disabled = false;
	document.getElementById("item-quantity").disabled = false;
}
