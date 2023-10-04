document.addEventListener('DOMContentLoaded', function() {

    const formulario = document.getElementById('registroForm');
    const nombreInput = document.getElementById('nombre');
    const apellidoInput = document.getElementById('apellido');
    const emailInput = document.getElementById('correo');
    const nombreError = document.getElementById('nombreError');
    const apellidoError = document.getElementById('apellidoError');
    const emailError = document.getElementById('emailError');
    const mensajeRegistro = document.getElementById('mensajeRegistro');

    document.getElementById('registrarBtn').addEventListener('click', function() {

        let isValid = true;

        // Validación del campo de nombre
        if (nombreInput.value.trim() === '') {
            isValid = false;
            nombreError.textContent = 'El nombre es obligatorio';
        } else {
            nombreError.textContent = '';
        }

        // Validación del campo de apellido
        if (apellidoInput.value.trim() === '') {
            isValid = false;
            apellidoError.textContent = 'El apellido es obligatorio';
        } else {
            apellidoError.textContent = '';
        }

        // Validación del campo de correo electrónico
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (!emailPattern.test(emailInput.value)) {
            isValid = false;
            emailError.textContent = 'El correo electrónico no es válido';
        } else {
            emailError.textContent = '';
        }

        // Si el formulario es válido, muestra el mensaje de registro exitoso
        /*if (isValid) {
            mensajeRegistro.style.display = 'block';
        }*/
    });
});
