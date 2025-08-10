const btnGenerateBill = document.getElementById("btn-generate-bill");

let selectedCustomer = null;
let billItems = [];
let total = 0;

document.addEventListener("DOMContentLoaded", function() {
	// seach customer
	document.getElementById("search-customer-form").addEventListener("submit", function(event) {
		searchCustomer(event, this);
	});

	// confirm customer
	document.getElementById("btn-confirm-customer").addEventListener("click", function(event) {
		confirmCustomer();
	});

	// add bill item
	document.getElementById("add-item-form").addEventListener("submit", function(event) {
		addItem(event, this);
	});

	// clear items list
	document.getElementById("btn-clear-all").addEventListener("click", function() {
		clearAllItems();
	});

	// create bill
	btnGenerateBill.addEventListener("click", function(event) {
		event.preventDefault(); // Prevent accidental form submit
		if (btnGenerateBill.disabled) return; // Ignore extra clicks

		btnGenerateBill.disabled = true;
		btnGenerateBill.textContent = "Processing...";
		createBill();
	});

	// print bill
	document.getElementById('btn-pdf').addEventListener('click', function() {
		printBill();
	});

	// reset for new bill
	document.getElementById('btn-new-bill').addEventListener('click', function() {
		resetPage();
	})

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
				toastr.error("Network response was not ok");
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
				toastr.success("Customer found!");
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
				toastr.error("An error occurred while searching the customer.");
			}
		})
		.catch(error => {
			toastr.error("There was a problem searching the customer: " + error.message);
		});
}

function confirmCustomer() {
	// Populate fields
	document.getElementById("previewCustomerEmail").innerText = selectedCustomer.email || "";
	document.getElementById("previewCustomerName").innerText = `${selectedCustomer.firstName || ""} ${selectedCustomer.lastName || ""}`.trim();

	// enable add bill item button
	document.getElementById("btn-add-bill-item").disabled = false;
	document.getElementById("item-id").disabled = false;
	document.getElementById("item-quantity").disabled = false;

	toastr.success("Customer confirmed!");
}

function addItem(event, form) {
	event.preventDefault();

	const formData = new FormData(form);
	const id = formData.get("id");
	const quantity = parseInt(formData.get("quantity"), 10);

	let params = new URLSearchParams(formData).toString();
	let existingItemIndex = -1;

	if (billItems.length > 0) {
		const existingItem = checkItemExists(id, quantity);
		if (existingItem !== null) {
			params = existingItem.queryParams;
			existingItemIndex = existingItem.existingItemIndex;
		}
	}

	const urlWithParams = `${form.action}?${params}`;

	fetch(urlWithParams, {
		method: "GET"
	})
		.then(response => {
			if (!response.ok) {
				toastr.error("Network response was not ok");
			}
			return response.json();
		})
		.then(data => {
			const errorFields = ["idError", "quantityError", "addItemError"];
			errorFields.forEach(id => {
				const el = document.getElementById(id);
				if (el) el.textContent = "";
			});

			if (data.success && data.item) {
				if (existingItemIndex !== -1) {
					// Update the existing item with new data from backend
					billItems[existingItemIndex] = data.item;
				} else {
					// Add new item if not exists
					billItems.push(data.item);
				}

				updateItemsList();
				updatePreview();
				form.reset();

			} else if (!data.success) {
				// Show validation errors from backend
				if (data.errors) {
					for (const [key, msg] of Object.entries(data.errors)) {
						const el = document.getElementById(key);
						if (el) el.textContent = msg;
					}
				}

				if (data.message) {
					document.getElementById("addItemError").innerText = data.message;
				}
			} else {
				toastr.error("An error occurred while searching the item.");
			}
		})
		.catch(error => {
			toastr.error("There was a problem searching the item: " + error.message);
		});
}

function checkItemExists(id, quantity) {
	const existingItemIndex = billItems.findIndex(item => item.id == id);

	if (existingItemIndex !== -1) {
		const existingItem = billItems[existingItemIndex];
		const combinedQuantity = existingItem.quantity + quantity;

		toastr.info("Item already in bill. Quantity updated and stock will be rechecked.");

		return {
			existingItemIndex,
			queryParams: `id=${id}&quantity=${combinedQuantity}`
		};
	}

	return null;
}

function updateItemsList() {
	const itemsList = document.getElementById('itemsList');
	const emptyState = document.getElementById('emptyState');

	if (billItems.length === 0) {
		itemsList.innerHTML = '';
		emptyState.style.display = 'block';
		document.getElementById("btn-generate-bill").disabled = true;
		document.getElementById("btn-clear-all-pseudo").disabled = true;
		return;
	}

	if (billItems.length === 1) {
		emptyState.style.display = 'none';
		document.getElementById("btn-generate-bill").disabled = false;
		document.getElementById("btn-clear-all-pseudo").disabled = false;
	}

	itemsList.innerHTML = billItems.map((item, index) => `
		<div class="px-6 py-4 hover:bg-blue-50 transition-colors fade-in">
			<div class="grid grid-cols-10 gap-4 items-center">
				<div class="text-s text-gray-400 font-medium">${item.id}</div>
				<div class="col-span-2 font-sm text-gray-900">${item.name}</div>
				<div class="col-span-2 text-center font-bold text-blue-600">${item.quantity}</div>
				<div class="col-span-2 text-right font-semibold">${item.unitPrice.toFixed(2)}</div>
				<div class="col-span-2 text-right font-bold text-green-600">${item.total.toFixed(2)}</div>
				<div class="text-center">
					<button onclick="removeItem(${index})" class="text-red-500 hover:text-red-700 hover:bg-red-50 p-2 rounded-lg transition-all">
						<i class="fas fa-trash"></i>
					</button>
				</div>
			</div>
		</div>
	`).join('');
}

function removeItem(index) {
	billItems.splice(index, 1);
	updateItemsList();
	updatePreview();

	toastr.info("Item removed!");
}

function clearAllItems() {
	if (billItems.length > 0) {
		billItems = [];
		updateItemsList();
		updatePreview();
		document.getElementById("clear-items-confirmation-modal").close();

		toastr.info("All items removed!");
	}
}

function updatePreview() {
	const previewItems = document.getElementById('previewItems');
	const previewTotal = document.getElementById('previewTotal');
	//const invoiceNumber = document.getElementById('invoiceNumber');
	const emptyItemState = document.getElementById('emptyPreviewItemState');

	if (billItems.length === 0) {
		emptyItemState.style.display = 'block';
		previewItems.innerText = '';
		previewTotal.textContent = '0.00';
		document.getElementById("btn-pdf").disabled = true;
		return;
	}

	if (billItems.length === 1) {
		emptyItemState.style.display = 'none';
	}

	total = billItems.reduce((sum, item) => sum + item.total, 0);

	previewItems.innerHTML = billItems.map(item => `
		<div class="flex justify-between text-sm">
			<span class="text-gray-700">${item.name} (×${item.quantity})</span>
			<span class="font-semibold text-gray-900">${item.total.toFixed(2)}</span>
		</div>
	`).join('');

	previewTotal.textContent = `${total.toFixed(2)}`;
}

function createBill() {
	// Prepare items array with only needed fields
	const filteredItems = billItems.map(item => ({
		id: item.id,
		quantity: item.quantity,
		unitPrice: item.unitPrice,
	}));

	// Get Sri Lanka time
	const now = new Date();
	const optionsDate = { timeZone: 'Asia/Colombo', year: 'numeric', month: '2-digit', day: '2-digit' };
	const optionsTime = { timeZone: 'Asia/Colombo', hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false };

	// Format date to YYYY-MM-DD
	const [month, day, year] = now.toLocaleDateString('en-GB', optionsDate).split('/');
	const date = `${year}-${month}-${day}`;

	// Format time to HH:MM:SS
	const time = now.toLocaleTimeString('en-GB', optionsTime);

	// Validate inputs
	if (!selectedCustomer || !selectedCustomer.id) {
		toastr.error("Customer is not selected or missing ID");
		return;
	}

	if (filteredItems.length === 0) {
		toastr.error("No items added to the bill.");
		return;
	}
	if (total <= 0) {
		toastr.error("Total amount must be greater than zero.");
		return;
	}
	if (!date || !time) {
		toastr.error("Invalid date or time.");
		return;
	}

	// Create the bill object
	var bill = {
		customerId: selectedCustomer.id,
		items: filteredItems,
		date: date,
		time: time,
		total: total,
		cashierId: 1 // TODO: replace with actual user ID
	};

	// Send the bill to the server
	fetch("/bookshopManagement/bills", {
		method: "POST",
		headers: {
			"Content-Type": "application/json;charset=UTF-8"
		},
		body: JSON.stringify(bill)
	})
		.then(response => {
			if (!response.ok) {
				toastr.error("Network response was not ok");
			}
			return response.json();
		})
		.then(data => {
			if (data.success) {
				document.getElementById("invoiceNumber").innerText = data.result;
				document.getElementById("previewDateTime").innerText = `${date} ${time}`;
				document.getElementById("btn-pdf").disabled = false;
				toastr.success(data.message || "Bill added successfully!");

				btnGenerateBill.textContent = "Generate Bill";

				sendBillEmail(date, time);
			} else if (data.errors) {
				for (const [key, msg] of Object.entries(data.errors)) {
					const el = document.getElementById(key);
					if (el) el.textContent = msg;
				}

				btnGenerateBill.disabled = false; // Re-enable on failure
				btnGenerateBill.textContent = "Generate Bill";
			} else {
				toastr.error("An error occurred while creating the bill.");

				btnGenerateBill.disabled = false; // Re-enable on failure
				btnGenerateBill.textContent = "Generate Bill";
			}
		})
		.catch(error => {
			toastr.error("There was a problem creating the bill: " + error.message);

			btnGenerateBill.disabled = false; // Re-enable on failure
			btnGenerateBill.textContent = "Generate Bill";
		});
}

function sendBillEmail(date, time) {
	if (!selectedCustomer || selectedCustomer.email.trim() === '') {
		toastr.error('Customer email not found');
		return;
	}

	const invoiceNumber = document.getElementById('invoiceNumber').textContent || 'Invoice';
	const subject = `${invoiceNumber} - Pahana Edu Bookshop`;

	const billPreview = document.getElementById('bill-preview');
	if (!billPreview) {
		toastr.error('Bill preview not found');
		return;
	}

	// Show sending status
	const sendingToast = toastr.info('Sending bill email...', { timeOut: 0, extendedTimeOut: 0, tapToDismiss: false });

	// Use html2canvas to convert to canvas
	html2canvas(billPreview).then(canvas => {
		// Convert canvas to base64 PNG string
		const base64Image = canvas.toDataURL('image/png');

		// Send data as JSON including image base64 string
		fetch('send-email', {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({
				to: selectedCustomer.email,
				subject: subject,
				date: date,
				time: time,
				imageBase64: base64Image  // send image as base64
			})
		})
			.then(response => response.json())
			.then(data => {
				toastr.clear(sendingToast); // Remove "Sending..." toast

				if (data.status === 'success') {
					toastr.success('Bill email sent successfully to ' + selectedCustomer.email);
				} else {
					toastr.error('Failed to send email: ' + (data.message || 'Unknown error'));
				}
			})
			.catch(error => {
				toastr.error('Error sending email: ' + error.message);
			});
	});
}

function printBill() {
	const billPreview = document.getElementById('bill-preview').innerHTML;
	const invoiceNumber = document.getElementById("invoiceNumber").innerText;

	const printWindow = window.open('', '_blank', 'width=800,height=600');

	printWindow.document.write(`
        <html>
        <head>
            <title>${invoiceNumber}</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
            <style>
                @media print {
                    body {
                        margin: 10px 50px; /* Bigger margins */
                    }
                    #bill-preview {
                        max-width: 700px; /* Limit content width */
                        margin: auto;
                    }
                }
            </style>
        </head>
        <body>
            <div id="bill-preview">
                ${billPreview}
            </div>
        </body>
        </html>
    `);

	printWindow.document.close();

	printWindow.onload = function() {
		printWindow.print();
		printWindow.close();
	};
}

function resetPage(){
	location.reload();
}
