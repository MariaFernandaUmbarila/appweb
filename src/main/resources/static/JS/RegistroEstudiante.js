//Botón de registrar/actualizar estudiante
document.addEventListener('DOMContentLoaded', function () {

    //Constantes para la validación de parámetros en la URL
    const urlParams = new URLSearchParams(window.location.search);
    const editId = urlParams.get("edit");

    if(editId) consultarIdEstudiante(editId);

    document.getElementById('registroForm').addEventListener('submit', function (event) {

        // Evita que la página se recargue al enviar el formulario
        event.preventDefault();

        // Captura los valores de los campos y de los errores
        let nombre = document.getElementById('nombre').value;
        let apellido = document.getElementById('apellido').value;
        let correo = document.getElementById('correo').value;

        // Crea un objeto con los datos del estudiante en el formulario
        let datosEstudiante = {
            nombre: nombre,
            apellido: apellido,
            correo: correo
        };

        // Verificar si el parámetro "edit" está presente, es decir, se quiere actualizar
        if (editId) {
            actualizarEstudiante(editId, datosEstudiante);
        }else{
            //Si todos los campos del formulario son válidos
            if (validarCamposForm(nombre, apellido, correo)) crearEstudiante(datosEstudiante);
        }
    });
});

//Función de validación de campos del formulario
function validarCamposForm(nombreInput, apellidoInput, correoInput){

    const nombreError = document.getElementById('nombreError');
    const apellidoError = document.getElementById('apellidoError');
    const emailError = document.getElementById('emailError');

    let isValid = true;

    // Validación del campo de nombre
    if (nombreInput.trim() === '') {
        isValid = false;
        nombreError.textContent = 'El nombre es obligatorio';
    } else {
        nombreError.textContent = '';
    }

    // Validación del campo de apellido
    if (apellidoInput.trim() === '') {
        isValid = false;
        apellidoError.textContent = 'El apellido es obligatorio';
    } else {
        apellidoError.textContent = '';
    }

    // Validación del campo de correo electrónico
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if (!emailPattern.test(correoInput)) {
        isValid = false;
        emailError.textContent = 'El correo electrónico no es válido';
    } else {
        emailError.textContent = '';
    }

    return isValid;
}

//Función para consulta del id trapido del parámetro
function consultarIdEstudiante(idParam){
    fetch(`http://localhost:8081/api/get_student/${idParam}`)
    .then(response => response.json())
    .then(data => {
        // Llenar el formulario con los datos del estudiante obtenidos de la API
        document.getElementById("nombre").value = data.nombre;
        document.getElementById("apellido").value = data.apellido;
        document.getElementById("correo").value = data.correo;
        document.getElementById("registrarBtn").textContent = "Actualizar";
    })
    .catch(error => {
        console.error("Error al obtener los datos del estudiante: ", error);
    });
}

//Función para actualización del estudiante
function actualizarEstudiante(idParam, body){
    // Hacer una solicitud a tu API para obtener los datos del estudiante por su ID (editId)
    fetch(`http://localhost:8081/api/update_student/${idParam}`,{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
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
}

//Función para creación del estudiante
function crearEstudiante(body){
    // Consumo del endpoint de actualización, ya que no está presente el parámetro edir
    fetch('http://localhost:8081/api/create_student', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
    .then(response => response.json())
    .then(() => {
        // Redirige a la página de la tabla después del registro exitoso o actualiza la lista de estudiantes y muestra los datos
        window.location.href = './TablaEstudiantes.html';
    })
    .catch(error => {
        console.error('Error al registrar estudiante:', error);
    });
}