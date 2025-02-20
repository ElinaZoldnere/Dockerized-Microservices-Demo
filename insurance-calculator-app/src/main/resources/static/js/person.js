function addPerson() {
    let personsContainer = document.getElementById("persons-container");
    let index = document.getElementsByClassName("person-card").length;
    let personTemplate = `
        <div class="card person-card shadow-sm mt-4">
            <div class="card-body">
                <h2 class="text-primary fs-4 mb-3">Person ${index + 1}</h2>

                <div class="mb-3">
                    <label class="form-label" for="persons[${index}].personFirstName">First Name:</label>
                    <input type="text" class="form-control" id="persons[${index}].personFirstName" name="persons[${index}].personFirstName" />
                </div>

                <div class="mb-3">
                    <label class="form-label" for="persons[${index}].personLastName">Last Name:</label>
                    <input type="text" class="form-control" id="persons[${index}].personLastName" name="persons[${index}].personLastName" />
                </div>

                <div class="mb-3">
                    <label class="form-label" for="persons[${index}].personalCode">Personal Code:</label>
                    <input type="text" class="form-control" id="persons[${index}].personalCode" name="persons[${index}].personalCode" />
                </div>

                <div class="mb-3">
                    <label class="form-label" for="persons[${index}].personBirthDate">Birth Date:</label>
                    <input type="date" class="form-control" id="persons[${index}].personBirthDate" name="persons[${index}].personBirthDate" />
                </div>

                <div class="mb-3">
                    <label class="form-label" for="persons[${index}].medicalRiskLimitLevel">Medical Risk Limit Level:</label>
                    <select class="form-control" id="persons[${index}].medicalRiskLimitLevel" name="persons[${index}].medicalRiskLimitLevel"></select   >
                </div>

                <div class="mb-3">
                    <label class="form-label" for="persons[${index}].travelCost">Travel Cost:</label>
                    <input type="text" class="form-control" id="persons[${index}].travelCost" name="persons[${index}].travelCost" />
                </div>
            </div>
        </div>`;
    personsContainer.insertAdjacentHTML('beforeend', personTemplate);
    addDropdownMedicalRiskLimitLevel(index);
}

function removePerson() {
    let personsContainer = document.getElementById("persons-container");
    let personForms = document.getElementsByClassName("person-card");
    if (personForms.length > 0) {
        personsContainer.removeChild(personForms[personForms.length - 1]);
    }
}

function clearForm() {
    window.location.href = window.location.href.split('?')[0];
}

document.addEventListener('DOMContentLoaded', (event) => {
    preselectDates();
    addDropdownCountries();
    addDropdownSelectedRisks();
});

function preselectDates() {
    const agreementDateFrom = document.getElementById('agreementDateFrom');
    const agreementDateTo= document.getElementById('agreementDateTo');

    const today = new Date();

    const dateFrom = new Date();
    dateFrom.setDate(today.getDate() + 1);

    const dateTo = new Date();
    dateTo.setDate(today.getDate() + 2);

    agreementDateFrom.value = dateFrom.toISOString().split('T')[0];
    agreementDateTo.value = dateTo.toISOString().split('T')[0];
}

function addDropdownCountries() {
    const countryDropdown = document.getElementById("country");

    // Clear previous content
    countryDropdown.innerHTML = "";
    // Create default option
    const defaultOption = document.createElement("option");
    defaultOption.value = ""; // no actual value
    defaultOption.textContent = "Choose Country..."; // display text
    defaultOption.disabled = true; // prevent selection
    defaultOption.selected = true; // preselected
    countryDropdown.appendChild(defaultOption);

    fetch("/insurance/travel/web/v2/dropdown/countries")
        .then(response => response.json()) // Convert to JSON
        .then(data => {
            data.items.forEach(i => {
                let option = document.createElement("option");
                option.value = i;
                option.textContent = i;
                countryDropdown.appendChild(option);
            });
        })
        .catch (error => console.error("Error fetching countries: ", error));
}

function addDropdownSelectedRisks() {
    const selectedRisksDropdown = document.getElementById("selectedRisks");

    // Clear previous content
    selectedRisksDropdown.innerHTML = "";

    fetch("/insurance/travel/web/v2/dropdown/selected-risks")
        .then(response => response.json()) // Convert to JSON
        .then(data => {
            data.items.forEach(i => {
                let option = document.createElement("option");
                option.value = i;
                option.textContent = i;
                selectedRisksDropdown.appendChild(option);
            });

            // Initialize Select2
            $('#selectedRisks').select2({
                placeholder: "Choose Risks...",
                allowClear: true
            });
        })
        .catch (error => console.error("Error fetching selected risks: ", error));
}

function addDropdownMedicalRiskLimitLevel(index) {
    // Select last added person card
    const lastPersonCard = document.querySelectorAll(".person-card")[index]; // selects by class
    const medicalRiskLimitLevelDropdown = lastPersonCard.querySelector("select[name='persons[" + index + "].medicalRiskLimitLevel']");

    // Clear previous content
    medicalRiskLimitLevelDropdown.innerHTML = "";
    // Create default option
    const defaultOption = document.createElement("option");
    defaultOption.value = ""; // no actual value
    defaultOption.textContent = "Choose Medical Risk Limit Level..."; // display text
    defaultOption.disabled = true; // prevent selection
    defaultOption.selected = true; // preselected
    medicalRiskLimitLevelDropdown.appendChild(defaultOption);

    fetch("/insurance/travel/web/v2/dropdown/medical-risk-limit-level")
        .then(response => response.json()) // Convert to JSON
        .then(data => {
            data.items.forEach(i => {
                let option = document.createElement("option");
                option.value = i;
                option.textContent = i;
                medicalRiskLimitLevelDropdown.appendChild(option);
            });
        })
        .catch (error => console.error("Error fetching medical risk limit levels: ", error));
}