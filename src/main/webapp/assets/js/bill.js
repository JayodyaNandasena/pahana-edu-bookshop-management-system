const customerDlg = document.getElementById('new-customer-modal');

// Open modal
document.getElementById('new-customer-btn').onclick = () => {
  customerDlg.showModal();
};

// Close modal functions
function closeModal() {
  customerDlg.close();
}

// Close button
document.getElementById('new-customer-close-btn').onclick = closeModal;

// Cancel button
document.getElementById('cancel-btn').onclick = closeModal;