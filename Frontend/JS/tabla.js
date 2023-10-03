document.addEventListener('DOMContentLoaded', function () {
    const studentTableBody = document.getElementById('studentTableBody');

    // Realiza una solicitud GET a tu API para obtener la lista de estudiantes
    fetch('http://localhost:8081/api/get_all_students')
        .then(response => response.json())
        .then(data => {
            // Itera sobre los datos de estudiantes y crea filas en la tabla
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
    // Realiza una solicitud DELETE a tu API para eliminar el estudiante con el ID proporcionado
    fetch(`http://localhost:8081/api/delete_student/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.status === 204) {
                // Éxito al eliminar, puedes actualizar la lista de estudiantes o realizar otras acciones
                console.log('Estudiante eliminado con éxito.');
                // Por ejemplo, puedes recargar la página actual para actualizar la lista de estudiantes
                location.reload();
            } else if (response.status === 404) {
                // Estudiante no encontrado, maneja el error como desees
                console.error('Estudiante no encontrado.');
            } else {
                // Otro error, maneja el error como desees
                console.error('Error al eliminar el estudiante.');
            }
        })
        .catch(error => {
            console.error('Error al eliminar el estudiante:', error);
        });
}
