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
                        <i class="fa-solid fa-pen-to-square" style="color: #020203; cursor: pointer;" onclick="editarEstudiante(${student.id})"></i>
                        <i class="fa-solid fa-trash" style="color: #0f0f10; cursor: pointer;" onclick="eliminarEstudiante(${student.id})"></i>
                    </td>
              `;
                studentTableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error al obtener la lista de estudiantes:', error);
        });
});
function editarEstudiante(id) {

}

function eliminarEstudiante(id) {
    fetch(`http://localhost:8081/api/delete_student/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.status === 204) {
                console.log('Estudiante eliminado con éxito.');
                location.reload();
            } else if (response.status === 404) {
                console.error('Estudiante no encontrado.');
            } else {
                console.error('Error al eliminar el estudiante.');
            }
        })
        .catch(error => {
            console.error('Error al eliminar el estudiante:', error);
        });
}
