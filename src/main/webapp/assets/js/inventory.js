document.addEventListener("DOMContentLoaded", () => {
	const modal = document.getElementById("delete-confirmation-modal");
	const itemIdInput = document.getElementById("delete-item-id");
	const itemNameDisplay = document.getElementById("delete-item-name-display");
	const cancelBtn = document.getElementById("cancel-delete-btn");
	const closeBtn = document.getElementById("delete-close-btn");

	// Open modal and populate fields
	document.querySelectorAll("[data-dialog-open='delete-confirmation-modal']").forEach(button => {
		button.addEventListener("click", () => {
			const itemId = button.getAttribute("data-item-id");
			const itemName = button.getAttribute("data-item-name");

			itemIdInput.value = itemId;
			itemNameDisplay.textContent = `"${itemName}"`;

			modal.showModal();
		});
	});

	// Close modal
	cancelBtn.addEventListener("click", () => modal.close());
	closeBtn.addEventListener("click", () => modal.close());
});