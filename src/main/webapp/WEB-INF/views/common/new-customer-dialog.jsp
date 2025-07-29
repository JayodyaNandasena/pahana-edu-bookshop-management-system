<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<dialog id="new-customer-modal"
		class="m-auto p-0 border-0 w-full max-w-2xl rounded-xl bg-transparent h-full">
	<div
		class="relative bg-white rounded-xl shadow-2xl border border-gray-100 overflow-hidden">
		<!-- Modal content -->
		<div
			class="relative bg-white rounded-xl shadow-2xl border border-gray-100 overflow-hidden">
			<!-- Modal header -->
			<div
				class="bg-gradient-to-r from-blue-50 to-indigo-50 px-6 py-3 border-b border-gray-200">
				<div class="flex items-center justify-between">
					<div class="flex items-center space-x-3">
						<div
							class="w-8 h-8 bg-blue-100 rounded-lg flex items-center justify-center"
							data-dialog-close>
							<i class="fas fa-user-plus text-blue-600 text-sm"></i>
						</div>
						<h3 class="text-xl font-bold">Add New Customer</h3>
					</div>
					<button type="button" id="new-customer-close-btn"
						class="w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 flex items-center justify-center transition-colors group">
						<i class="fas fa-times text-gray-500 group-hover:text-gray-700"></i>
					</button>
				</div>
			</div>

			<!-- Modal body -->
			<div class="px-6 py-5">
				<form action="${pageContext.request.contextPath}/customer" method="post" id="customer-form" class="space-y-4">
					<input type="hidden" name="active_page" value="${activePage}">
					<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
						<div class="space-y-2">
							<label for="first-name"
								class="block text-sm font-semibold text-gray-700"> <i
								class="fas fa-user text-gray-400 mr-2"></i> First Name <span
								class="text-red-500">*</span>
							</label> <input type="text" name="first_name" id="first-name"
								class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400"
								placeholder="John" required />
						</div>
						<div class="space-y-2">
							<label for="last-name"
								class="block text-sm font-semibold text-gray-700"> <i
								class="fas fa-user text-gray-400 mr-2"></i> Last Name <span
								class="text-red-500">*</span>
							</label> <input type="text" name="last_name" id="last-name"
								class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400"
								placeholder="Doe" required />
						</div>
					</div>

					<!-- Contact information -->
					<div class="space-y-2">
						<label for="phone"
							class="block text-sm font-semibold text-gray-700"> <i
							class="fas fa-phone text-gray-400 mr-2"></i> Mobile Number <span
							class="text-red-500">*</span>
						</label> <input type="tel" name="phone" id="phone"
							class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400"
							placeholder="0700000000" required />
					</div>

					<div class="space-y-2">
						<label for="email"
							class="block text-sm font-semibold text-gray-700"> <i
							class="fas fa-envelope text-gray-400 mr-2"></i> Email Address <span
							class="text-red-500">*</span>
						</label> <input type="email" name="email" id="email"
							class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400"
							placeholder="johndoe@email.com" required />
					</div>

					<div class="space-y-2">
						<label for="address"
							class="block text-sm font-semibold text-gray-700"> <i
							class="fas fa-map-marker-alt text-gray-400 mr-2"></i> Home
							Address <span class="text-red-500">*</span>
						</label>
						<textarea name="address" id="address" rows="3"
							class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400 resize-none"
							placeholder="Enter full address including street, city, postal code..."
							required></textarea>
					</div>

					<!-- Action buttons -->
					<div class="flex flex-col sm:flex-row gap-3">
						<button type="button" id="cancel-btn"
							class="flex-1 px-6 py-3 border border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-gray-50 transition-colors"
							data-dialog-close>Cancel</button>
						<button type="submit"
							class="flex-1 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white font-semibold rounded-lg px-6 py-3 focus:ring-4 focus:ring-blue-300 transition-all transform hover:scale-[1.02] active:scale-[0.98]">
							<i class="fas fa-user-plus mr-2"></i> Create Customer
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	</dialog>