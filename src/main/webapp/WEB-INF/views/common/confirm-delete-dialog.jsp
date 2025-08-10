<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<dialog id="delete-confirmation-modal"
	class="fixed inset-0 items-center justify-center max-w-xl p-0 bg-transparent backdrop-blur-sm">
<div
	class="bg-white rounded-xl shadow-2xl border border-gray-100 max-w-lg w-full overflow-hidden">
	<!-- Modal header -->
	<div
		class="bg-gradient-to-r from-red-50 to-red-100 px-6 py-3 border-b border-gray-200 flex items-center justify-between">
		<div class="flex items-center space-x-3">
			<div
				class="w-8 h-8 bg-red-100 rounded-lg flex items-center justify-center">
				<i class="fa-solid fa-triangle-exclamation text-red-600 text-sm"></i>
			</div>
			<h3 class="text-xl font-bold text-red-700">Confirm Delete</h3>
		</div>
		<button type="button" id="delete-close-btn"
			class="w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 flex items-center justify-center"
			aria-label="Close delete modal">
			<i class="fas fa-times text-gray-500"></i>
		</button>
	</div>

	<!-- Modal body -->
	<div class="px-6 py-5">
		<p class="text-gray-700 text-base mb-6">
			Are you sure you want to delete <span class="font-semibold"
				id="delete-item-name-display">"Item Name"</span>? <br /> This
			action cannot be undone.
		</p>

		<!-- Action buttons -->
		<form action="item" method="post" id="delete-form" class="flex gap-3">
			<input type="hidden" name="action" value="delete" /> <input
				type="hidden" name="itemId" id="delete-item-id" />

			<button type="button" id="cancel-delete-btn"
				class="flex-1 px-6 py-3 border border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-gray-100 transition">
				Cancel</button>

			<button type="submit"
				class="flex-1 bg-gradient-to-r from-red-600 to-red-700 hover:from-red-700 hover:to-red-800 text-white font-semibold rounded-lg px-6 py-3 transition-transform transform hover:scale-[1.02] active:scale-[0.98]">
				<i class="fa-solid fa-trash mr-2"></i> Delete
			</button>
		</form>
	</div>
</div>
</dialog>