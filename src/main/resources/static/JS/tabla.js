document.addEventListener('DOMContentLoaded', function () {

    const studentTableBody = document.getElementById('studentTableBody');

    fetch('http://localhost:8081/api/get_all_students')
        .then(response => response.json())
        .then(data => {
            data.forEach(student => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${student.id}</td>
                    <td>${student.nombre}</td>
                    <td>${student.apellido}</td>
                    <td>${student.correo}</td>
                    <td>
                        <i class="fa-solid fa-pen-to-square" style="color: #020203; cursor: pointer;" onclick="editarEstudiante('${student.id}')"></i>
                        <i class="fa-solid fa-trash" style="color: #0f0f10; cursor: pointer;" onclick="eliminarEstudiante(${student.id})"></i>
                    </td>
              `;
                studentTableBody.appendChild(row);
            });
        })
        .catch(error => {
            alert('Error al obtener la lista de estudiantes: ' + error);
        });
});

function editarEstudiante(student_edit) {
    // Redirigir a la página RegistroEstudiantes.html?edit=id
    window.location.href = `./RegistroEstudiantes.html?edit=${student_edit}`;

    /*student_edit = JSON.parse(student_edit);

    var nuevoEstudiante = {
        nombre: student_edit.nombre,
        apellido: student_edit.apellido,
        correo: student_edit.correo
    };

    // Hacer una solicitud a tu API para obtener los datos del estudiante por su ID (editId)
    fetch(`http://localhost:8081/api/update_student/${student_edit.id}`,{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevoEstudiante)
    }).then(response => {
        if (response.status === 200) {
            alert('Estudiante eliminado con éxito.');
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

function eliminarEstudiante(id) {
    fetch(`http://localhost:8081/api/delete_student/${id}`, {
        method: 'DELETE'
    }).then(response => {
            if (response.status === 204) {
                alert('Estudiante eliminado con éxito.');
                location.reload();
            } else if (response.status === 404) {
                alert('Estudiante no encontrado.');
            } else {
                alert('Error al eliminar el estudiante.');
            }
        })
        .catch(error => {
            alert('Error al eliminar el estudiante: ' + error);
        });
}