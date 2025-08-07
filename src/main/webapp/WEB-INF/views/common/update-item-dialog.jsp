<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<dialog id="update-item-modal"
	class="m-auto p-0 border-0 w-full max-w-2xl rounded-xl bg-transparent h-full">
	<div class="relative bg-white rounded-xl shadow-2xl border border-gray-100 overflow-hidden">
		<!-- Modal header -->
		<div class="bg-gradient-to-r from-blue-50 to-indigo-50 px-6 py-3 border-b border-gray-200">
			<div class="flex items-center justify-between">
				<div class="flex items-center space-x-3">
					<div class="w-8 h-8 bg-blue-100 rounded-lg flex items-center justify-center">
						<i class="fa-solid fa-book text-blue-600 text-sm"></i>
					</div>
					<h3 class="text-xl font-bold">Update Item</h3>
				</div>
				<button type="button" id="update-item-close-btn"
					class="w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 flex items-center justify-center transition-colors group">
					<i class="fas fa-times text-gray-500 group-hover:text-gray-700"></i>
				</button>
			</div>
		</div>

		<!-- Modal body -->
		<div class="px-6 py-5">
			<form class="space-y-4"
				action="item?action=update" method="post"
				id="update-item-form">
				<input type="hidden" name="u_id" id="update-id" value="" />

				<div class="space-y-2">
					<label for="update-name" class="block text-sm font-semibold text-gray-700">
						<i class="fa-solid fa-font text-gray-400 mr-2"></i> Item Name <span class="text-red-500">*</span>
					</label>
					<input type="text" name="u_name" id="update-name"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400"
						placeholder="Text Book" required />
					<p class="text-red-500 text-sm mt-1 ml-1" id="nameError"></p>
				</div>

				<div class="space-y-2">
					<label for="update-category"
						class="block text-sm font-semibold text-gray-700">
						<i class="fa-solid fa-list-ul text-gray-400 mr-2"></i> Category <span class="text-red-500">*</span>
					</label>
					<select name="u_category" id="update-category"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors text-gray-700">
						<c:forEach var="category" items="${categories}">
							<option value="${category.id}">${category.name}</option>
						</c:forEach>
					</select>
					<p class="text-red-500 text-sm mt-1 ml-1" id="categoryError"></p>
					<p class="text-red-500 text-sm mt-1 ml-1" id="categoryuNameError"></p>
				</div>

				<div class="space-y-2">
					<label for="update-price" class="block text-sm font-semibold text-gray-700">
						<i class="fa-solid fa-money-bill text-gray-400 mr-2"></i> Unit Price in LKR <span class="text-red-500">*</span>
					</label>
					<input type="number" name="u_price" id="update-price" min="0.01" max="100000" step="0.01"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400"
						placeholder="200.00" required />
					<p class="text-red-500 text-sm mt-1 ml-1" id="priceError"></p>
				</div>

				<div class="space-y-2">
					<label for="update-quantity" class="block text-sm font-semibold text-gray-700">
						<i class="fa-solid fa-hashtag text-gray-400 mr-2"></i> Quantity Available <span class="text-red-500">*</span>
					</label>
					<input type="number" name="u_quantity" id="update-quantity" min="1" max="10000"
						class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400 resize-none"
						placeholder="1500" required />
					<p class="text-red-500 text-sm mt-1 ml-1" id="quantityError"></p>
				</div>

				<!-- Action buttons -->
				<div class="flex flex-col sm:flex-row gap-3">
					<button type="button" id="update-cancel-btn"
						class="flex-1 px-6 py-3 border border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-gray-50 transition-colors"
						data-dialog-close>Cancel</button>
					<button type="submit"
						class="flex-1 btn-primary text-white font-semibold rounded-lg px-6 py-3 focus:ring-4 focus:ring-blue-300 transition-all transform hover:scale-[1.02] active:scale-[0.98]">
						<i class="fa-solid fa-check mr-2"></i> Save Changes
					</button>
				</div>
			</form>
		</div>
	</div>
</dialog>

<script>
  document.addEventListener("DOMContentLoaded", function () {
    const updateModal = document.getElementById("update-item-modal");
    const form = document.getElementById("update-item-form");

    // Event delegation for update button click
    document.querySelectorAll("[data-dialog-open='update-item-modal']").forEach(button => {
      button.addEventListener("click", () => {
        const id = button.getAttribute("data-id");
        const name = button.getAttribute("data-name");
        const categoryId = button.getAttribute("data-category-id");
        const price = button.getAttribute("data-price");
        const quantity = button.getAttribute("data-quantity");

        // Populate fields
        document.getElementById("update-id").value = id;
        document.getElementById("update-name").value = name;
        document.getElementById("update-category").value = categoryId.toString();
        document.getElementById("update-price").value = price;
        document.getElementById("update-quantity").value = quantity;
        
        updateModal.showModal();
      });
    });

    // Modal close buttons
    document.getElementById("update-item-close-btn").addEventListener("click", () => {
      updateModal.close();
    });

    document.getElementById("update-cancel-btn").addEventListener("click", () => {
      updateModal.close();
    });

    // Handle form submission via fetch
    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Stop the default form submit

        const formData = new FormData(form);
        
        for (const [key, value] of formData.entries()) {
      	  console.log(key, value);
      	}

        const data = new URLSearchParams(new FormData(form));

        fetch(form.action, {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
          },
          body: data.toString()
        })
        .then(response => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then(data => {
  		  // Clear existing error messages
  		  const errorFields = ["nameError", "categoryError", "priceError", "quantityError", "generalError"];
  		  errorFields.forEach(id => {
  		    const el = document.getElementById(id);
  		    if (el) el.textContent = "";
  		  });
  		
  		  if (data.success) {
  		    alert(data.message || "Item created successfully!");
  		    document.getElementById("new-item-modal").close();
  		    form.reset();
  		  } else if (data.errors) {
  		    // Show validation errors
  		    for (const [key, msg] of Object.entries(data.errors)) {
  			    const el = document.getElementById(key);
  			    if (el) el.textContent = msg;
  		      
  		      	// highlight both 'name' and 'category' if it's a combined error
  			    if (key === "categoryuNameError") {
  			      document.getElementById("update-name").classList.add("border-red-500", "ring-red-300");
  			      document.getElementById("update-category").classList.add("border-red-500", "ring-red-300");
  			    }		      
  		    }	    
  		 	 
  		  } else {
  		    alert("An error occurred while creating the item.");
  		  }
  		})
        .catch(error => {
          // Handle error
          alert("There was a problem creating the item: " + error.message);
        });
      });
  });
</script>
