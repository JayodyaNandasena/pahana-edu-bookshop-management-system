(() => {
	// Open dialog
	function openDialog(dialog, triggerEl) {
		if (!dialog) return;
		dialog.__trigger = triggerEl || document.activeElement;
		dialog.showModal();
	}

	// Close dialog
	function closeDialog(dialog) {
		if (!dialog) return;
		dialog.close();
		if (dialog.__trigger && typeof dialog.__trigger.focus === 'function') {
			dialog.__trigger.focus();
		}
	}

	// opening and closing dialogs
	// using data attributes: data-dialog-open and data-dialog-close
	document.addEventListener('click', (e) => {
		const openBtn = e.target.closest('[data-dialog-open]');
		if (openBtn) {
			const id = openBtn.getAttribute('data-dialog-open');
			openDialog(document.getElementById(id), openBtn);
			return;
		}

		document.addEventListener('click', (e) => {
			const closeBtn = e.target.closest('[data-dialog-close]');
			if (closeBtn) {
				const dlg = closeBtn.closest('dialog');
				if (dlg) dlg.close();
			}
		});

		// closing dialog clicking on the backdrop
		if (e.target instanceof HTMLDialogElement && e.target.open) {
			const rect = e.target.getBoundingClientRect();
			const clickedInDialog =
				e.clientX >= rect.left && e.clientX <= rect.right &&
				e.clientY >= rect.top && e.clientY <= rect.bottom;
			if (!clickedInDialog) closeDialog(e.target);
		}
	});

	// closing the top-most open dialog using ESC
	document.addEventListener('keydown', (e) => {
		if (e.key === 'Escape') {
			const openDialogs = Array.from(document.querySelectorAll('dialog[open]'));
			const top = openDialogs.pop();
			if (top) {
				e.preventDefault();
				closeDialog(top);
			}
		}
	});
})();
