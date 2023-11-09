
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
    console.log(data);
    // Aqui você pode adicionar lógica para enviar os dados para o servidor Java.
}

