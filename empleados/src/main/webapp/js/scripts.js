/**
 * Attaches a 'keyup' event listener to the search input field. When a key is
 * released, the current value of the input field is encoded and used to
 * construct a URL for the 'EmployedController' which will search for employees
 * with names that match the search query. The page is then redirected to the
 * newly constructed URL.
 *
 * @example
 * handleSearchInput();
 */
function handleSearchInput() {
    const searchInput = document.getElementById("searchByName");
    searchInput.addEventListener("keyup", function() {
        const query = this.value;
        const encodedQuery = encodeURIComponent(query);
        window.location.href = "EmployedController?" + ACTION + "=" + SEARCH + "&searchByName=" + encodedQuery;
    });
}

/**
 * Focuses on the search field and moves the cursor to the end.
 * This is useful when you want to edit the search query and immediately
 * start typing from the end without having to manually move the cursor.
 */
function focusAndMoveCursorToEnd() {
    const searchField = document.getElementById("searchByName");
    searchField.focus();

    const value = searchField.value;
    searchField.value = '';
    searchField.value = value;
}

/**
 * Sets up the delete confirmation modal.
 *
 * When the modal is shown, it retrieves the employee ID and name from the
 * button that triggered the modal and updates the modal's content with the
 * name. It also sets up the 'confirmDelete' button to redirect the user to a
 * URL that will delete the employee with the retrieved ID.
 */
function setupModalDeleteOne() {
    document.getElementById('modalConfirm').addEventListener('show.bs.modal', function(event) {
        var button = event.relatedTarget;
        var employeeId = button.getAttribute('data-id');
        var employeeName = button.getAttribute('data-name');

        document.getElementById('message').textContent = 'Are you sure you want to delete ' + employeeName + '?';

        /**
         * Redirects the user to a URL that will delete the employee with the
         * given 'employeeId'.
         *
         * @param {string} employeeId - The ID of the employee to delete.
         */
        document.getElementById('confirmDelete').onclick = function() {
            window.location.href = 'EmployedController?' + ACTION + '=' + DELETE + '&id=' + employeeId;
        };
    });
}

/**
 * Sets up the delete confirmation modal for deleting multiple employees.
 *
 * When the 'delete selected' button is clicked, it retrieves the IDs of the
 * selected checkboxes and updates the modal's content with the names of the
 * selected employees. It also sets up the 'confirmDelete' button to redirect
 * the user to a URL that will delete the selected employees.
 */
function setupModalDeleteMultiple() {
    document.getElementById('deleteSelectedBtn').addEventListener('click', function(event) {
        event.preventDefault();

        /**
         * Retrieves the IDs of the selected checkboxes and joins them into a
         * comma-separated string.
         *
         * @returns {string} The IDs of the selected checkboxes joined into a
         * comma-separated string.
         */
        var selectedIds = Array.from(document.querySelectorAll('input[name="ids[]"]:checked')).map(function(checkbox) {
            return checkbox.value;
        }).join(',');

        if (selectedIds.length === 0) {
            window.location.href = 'EmployedController?' + ACTION + '=' + DELETE_MULTIPLE + '&ids=' + selectedIds;
            return;
        }

        /**
         * Retrieves the delete button and sets its 'data-ids' attribute to the
         * comma-separated string of selected IDs.
         */
        var deleteBtn = document.getElementById('confirmDelete');
        deleteBtn.setAttribute('data-ids', selectedIds);

        /**
         * Retrieves the message element and sets its text content to a
         * message that includes the selected IDs.
         */
        var message = document.getElementById('message');
        message.textContent = 'Are you sure you want to delete the following employees: ' + selectedIds + '?';

        /**
         * Retrieves the modal and shows it.
         */
        var modal = new bootstrap.Modal(document.getElementById('modalConfirm'));
        modal.show();
    });

    /**
     * Sets up the 'confirmDelete' button to redirect the user to a URL that
     * will delete the selected employees.
     */
    document.getElementById('confirmDelete').addEventListener('click', function() {
        var selectedIds = this.getAttribute('data-ids');
        if (selectedIds) {
            window.location.href = 'EmployedController?' + ACTION + '=' + DELETE_MULTIPLE + '&ids=' + selectedIds;
        }
    });
}


/**
 * Initialization code that is run when the page has finished loading.
 * This code is responsible for attaching event listeners to the search
 * input field and the delete buttons.
 *
 * @see handleSearchInput
 * @see focusAndMoveCursorToEnd
 * @see setupModalDeleteOne
 * @see setupModalDeleteMultiple
 */
window.onload = function() {
    handleSearchInput();
    focusAndMoveCursorToEnd();
    setupModalDeleteOne();
    setupModalDeleteMultiple();
};