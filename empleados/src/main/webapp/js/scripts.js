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
        window.location.href = "EmployedController?action=search&searchByName=" + encodedQuery;
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
    searchField.value = '';  // Vacía el campo temporalmente
    searchField.value = value;  // Vuelve a asignar el valor para mover el cursor al final
}

/**
 * Sets up the delete confirmation modal.
 *
 * When the modal is shown, it retrieves the employee ID and name from the
 * button that triggered the modal and updates the modal's content with the
 * name. It also sets up the 'confirmDelete' button to redirect the user to a
 * URL that will delete the employee with the retrieved ID.
 */
function setupDeleteModal() {
    var modal = new bootstrap.Modal(document.getElementById('modalConfirm'));

    document.getElementById('modalConfirm').addEventListener('show.bs.modal', function(event) {
        var button = event.relatedTarget;
        var employeeId = button.getAttribute('data-id');
        var employeeName = button.getAttribute('data-name');

        document.getElementById('employeeName').textContent = employeeName;

        document.getElementById('confirmDelete').onclick = function() {
            window.location.href = 'EmployedController?action=delete&id=' + employeeId;
        };
    });
}

/**
 * This function is called when the window has finished loading.
 * It initializes the search input functionality, sets the focus on the search input field,
 * and sets up the delete confirmation modal.
 *
 * @example
 * window.onload = function() {
 *     handleSearchInput();
 *     focusAndMoveCursorToEnd();
 *     setupDeleteModal();
 * };
 */
window.onload = function() {
    handleSearchInput();
    focusAndMoveCursorToEnd();
    setupDeleteModal();
};