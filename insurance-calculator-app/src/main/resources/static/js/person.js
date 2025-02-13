function addPerson() {
    let personsContainer = document.getElementById("persons-container");
    let index = document.getElementsByClassName("person-card").length;
    let personTemplate = `
        <div class="card person-card shadow-sm mt-3">
            <div class="card-body">
                <h3 class="text-primary">Person ${index + 1}</h3>

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
                    <input type="text" class="form-control" id="persons[${index}].medicalRiskLimitLevel" name="persons[${index}].medicalRiskLimitLevel" />
                </div>

                <div class="mb-3">
                    <label class="form-label" for="persons[${index}].travelCost">Travel Cost:</label>
                    <input type="text" class="form-control" id="persons[${index}].travelCost" name="persons[${index}].travelCost" />
                </div>
            </div>
        </div>`;
    personsContainer.insertAdjacentHTML('beforeend', personTemplate);
}

function removePerson() {
    let personsContainer = document.getElementById("persons-container");
    let personForms = document.getElementsByClassName("person-card");
    if (personForms.length > 0) {
        personsContainer.removeChild(personForms[personForms.length - 1]);
    }
}

document.addEventListener('DOMContentLoaded', (event) => {
    const agreementDateFrom = document.getElementById('agreementDateFrom');
    const agreementDateTo= document.getElementById('agreementDateTo');

    const today = new Date();

    const dateFrom = new Date();
    dateFrom.setDate(today.getDate() + 1);

    const dateTo = new Date();
    dateTo.setDate(today.getDate() + 2);

    agreementDateFrom.value = dateFrom.toISOString().split('T')[0];
    agreementDateTo.value = dateTo.toISOString().split('T')[0];
});

function clearForm() {
    window.location.href = window.location.href.split('?')[0];
}
