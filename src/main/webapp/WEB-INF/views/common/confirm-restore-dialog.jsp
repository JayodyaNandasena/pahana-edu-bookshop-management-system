<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<dialog id="restore-confirmation-modal"
	class="fixed inset-0 items-center justify-center max-w-xl p-0 bg-transparent backdrop-blur-sm">
<div
	class="bg-white rounded-xl shadow-2xl border border-gray-100 max-w-lg w-full overflow-hidden">
	<!-- Modal header -->
	<div
		class="bg-gradient-to-r from-purple-50 to-purple-100 px-6 py-3 border-b border-gray-200 flex items-center justify-between">
		<div class="flex items-center space-x-3">
			<div
				class="w-8 h-8 bg-purple-100 rounded-lg flex items-center justify-center">
				<i class="fa-solid fa-reply text-purple-600 text-sm"></i>
			</div>
			<h3 class="text-xl font-bold text-purple-700">Confirm Restore</h3>
		</div>
		<button type="button" id="restore-close-btn"
			class="w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 flex items-center justify-center"
			aria-label="Close restore modal">
			<i class="fas fa-times text-gray-500"></i>
		</button>
	</div>

	<!-- Modal body -->
	<div class="px-6 py-5">
		<p class="text-gray-700 text-base mb-6">
			Are you sure you want to restore <span class="font-semibold"
				id="restore-item-name-display">"Item Name"</span>?
		</p>

		<!-- Action buttons -->
		<form action="item" method="post" id="restore-form" class="flex gap-3">
			<input type="hidden" name="action" value="restore" /> 
			<input type="hidden" name="itemId" id="restore-item-id" />

			<button type="button" id="cancel-restore-btn"
				class="flex-1 px-6 py-3 border border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-gray-100 transition">
				Cancel
			</button>

			<button type="submit"
				class="flex-1 bg-gradient-to-r from-purple-600 to-purple-700 hover:from-purple-700 hover:to-purple-800 text-white font-semibold rounded-lg px-6 py-3 transition-transform transform hover:scale-[1.02] active:scale-[0.98]">
				<i class="fa-solid fa-reply mr-2"></i> Restore
			</button>
		</form>
	</div>
</div>
</dialog>

<script>
	document.addEventListener("DOMContentLoaded", () => {
		const modal = document.getElementById("restore-confirmation-modal");
		const itemIdInput = document.getElementById("restore-item-id");
		const itemNameDisplay = document.getElementById("restore-item-name-display");
		const cancelBtn = document.getElementById("cancel-restore-btn");
		const closeBtn = document.getElementById("restore-close-btn");
	
		// Open modal and populate fields
		document.querySelectorAll("[data-dialog-open='restore-confirmation-modal']").forEach(button => {
			button.addEventListener("click", () => {
				const itemId = button.getAttribute("data-item-id");
				const itemName = button.getAttribute("data-item-name");
					
				itemIdInput.value = itemId;
				itemNameDisplay.textContent = itemName;
	
				modal.showModal();
			});
		});
	
		// Close modal
		cancelBtn.addEventListener("click", () => modal.close());
		closeBtn.addEventListener("click", () => modal.close());
	});
</script>