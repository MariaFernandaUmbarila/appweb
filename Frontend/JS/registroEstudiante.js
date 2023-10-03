document.addEventListener('DOMContentLoaded', function () {
document.getElementById('registroForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Evita que la página se recargue al enviar el formulario

    // Captura los valores de los campos
    var nombre = document.getElementById('nombre').value;
    var apellido = document.getElementById('apellido').value;
    var correo = document.getElementById('correo').value;

    // Crea un objeto con los datos del estudiante
    var nuevoEstudiante = {
        nombre: nombre,
        apellido: apellido,
        correo: correo
    };

    // Realiza una solicitud POST a tu API para crear el estudiante
    fetch('http://localhost:8081/api/create_student', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevoEstudiante)
    })
    .then(response => response.json())
    .then(data => {
        // Redirige a la página de la tabla después del registro exitoso
        // o actualiza la lista de estudiantes y muestra los datos
        window.location.href = './TablaEstudiantes.html';
    })
    .catch(error => {
        console.error('Error al registrar estudiante:', error);
    });
});
});