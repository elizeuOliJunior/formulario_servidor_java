
function submitForm() {
    const personalInfoForm = document.getElementById('personal-info-form');
    const addressForm = document.getElementById('address-form');
    const appointmentForm = document.getElementById('appointment-form');

    const personalInfoData = new FormData(personalInfoForm);
    const addressData = new FormData(addressForm);
    const appointmentData = new FormData(appointmentForm);

    const data = {
        personalInfo: Object.fromEntries(personalInfoData),
        address: Object.fromEntries(addressData),
        appointment: Object.fromEntries(appointmentData)
    };

    fetch('http://localhost:8000/submit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}
