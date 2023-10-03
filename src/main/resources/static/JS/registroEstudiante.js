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

// Obtener el parámetro "edit" de la URL
const urlParams = new URLSearchParams(window.location.search);
const editId = urlParams.get("edit");

// Verificar si el parámetro "edit" está presente
if (editId !== null) {
    // Hacer una solicitud a tu API para obtener los datos del estudiante por su ID (editId)
    fetch(`http://localhost:8081/api/get_student/${editId}`)
        .then(response => response.json())
        .then(data => {
            // Llenar el formulario con los datos del estudiante obtenidos de la API
            document.getElementById("nombre").value = data.nombre;
            document.getElementById("apellido").value = data.apellido;
            document.getElementById("correo").value = data.correo;
        })
        .catch(error => {
            console.error("Error al obtener los datos del estudiante: ", error);
        });
}


