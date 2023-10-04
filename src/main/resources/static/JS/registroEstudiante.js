document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('registroForm').addEventListener('submit', function (event) {

        // Evita que la página se recargue al enviar el formulario
        event.preventDefault();

        // Captura los valores de los campos
        let nombre = document.getElementById('nombre').value;
        let apellido = document.getElementById('apellido').value;
        let correo = document.getElementById('correo').value;

        // Crea un objeto con los datos del estudiante
        let nuevoEstudiante = {
            nombre: nombre,
            apellido: apellido,
            correo: correo
        };

        const urlParams = new URLSearchParams(window.location.search);
        const editId = urlParams.get("edit");

        // Verificar si el parámetro "edit" está presente
        if (editId) {

            alert('Entro actualiza');

            // Hacer una solicitud a tu API para obtener los datos del estudiante por su ID (editId)
            fetch(`http://localhost:8081/api/update_student/${editId}`,{
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(nuevoEstudiante)
            }).then(response => {
                if (response.status === 200) {
                    alert('Estudiante actualizado con éxito.');
                    window.location.href = './TablaEstudiantes.html';
                } else if (response.status === 404) {
                    alert('Estudiante no encontrado.');
                    window.location.href = './TablaEstudiantes.html';
                } else {
                    alert('Error al actualizar el estudiante.');
                    window.location.href = './TablaEstudiantes.html';
                }
            }).catch(error => {
                alert('Error al actualizar estudiante: ' + error);
                window.location.href = './TablaEstudiantes.html';
            });
        }else{
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
        }

    });
});

// Busca el parámetro de edición entre los parámetros de la URL
const urlParams = new URLSearchParams(window.location.search);
const editId = urlParams.get("edit");

// Verificar si el parámetro "edit" está presente
if (editId !== null) {

    let nuevoEstudiante = {
        nombre: editId.nombre,
        apellido: editId.apellido,
        correo: editId.correo
    };

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

    // Hacer una solicitud a tu API para obtener los datos del estudiante por su ID (editId)
    /*fetch(`http://localhost:8081/api/update_student/${editId.id}`,{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevoEstudiante)
    }).then(response => {
        if (response.status === 200) {
            alert('Estudiante actualizado con éxito.');
            location.reload();
        } else if (response.status === 404) {
            alert('Estudiante no encontrado.');
        } else {
            alert('Error al actualizar el estudiante.');
        }
    }).catch(error => {
        alert('Error al actualizar estudiante: ' + error);
    });*/
}




