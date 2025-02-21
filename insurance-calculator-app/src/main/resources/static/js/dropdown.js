async function fetchDropdownData(endpoint) {
    try {
        const response = await fetch("/insurance/travel/web/v2/dropdown" + endpoint);
        const data = await response.json();
        return data.items;
    } catch (error) {
        console.error(`Error fetching data from ${endpoint}: `, error);
        return [];
    }
}

function populateDropdown(dropdownId, defaultText, options, isMultiSelect = false) {
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

    options.forEach(i => {
        let option = document.createElement("option");
        option.value = i;
        option.textContent = i;
        dropdown.appendChild(option);
    });

    // Apply Select2 for multiselect options
    if (isMultiSelect) {
        $(`#${dropdownId}`).select2({
            placeholder: defaultText,
            allowClear: true
        });
    }
}
