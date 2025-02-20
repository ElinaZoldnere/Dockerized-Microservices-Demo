function populateDropdown(dropdownId, defaultText, endpoint, isMultiSelect = false) {
    const dropdown = document.getElementById(dropdownId);

    // Clear previous content
    dropdown.innerHTML = "";

    // Create default option if not managed automatically by Select2
    if (!isMultiSelect) {
        const defaultOption = document.createElement("option");
        defaultOption.classList.add("text-secondary"); // Light gray text color
        defaultOption.value = ""; // no actual value
        defaultOption.textContent = defaultText; // display text
        defaultOption.disabled = true; // prevent selection
        defaultOption.selected = true; // preselected
        dropdown.appendChild(defaultOption);
   }

    fetch("/insurance/travel/web/v2/dropdown" + endpoint)
        .then(response => response.json()) // Convert to JSON
        .then(data => {
            data.items.forEach(i => {
                let option = document.createElement("option");
                option.value = i;
                option.textContent = i;
                dropdown.appendChild(option);
            });

            // Initialize Select2
            if (isMultiSelect) {
                $(`#${dropdownId}`).select2({
                    placeholder: defaultText,
                    allowClear: true
                });
            }
        })
        .catch (error => console.error(`Error fetching data for ${dropdownId}: `, error));
}
