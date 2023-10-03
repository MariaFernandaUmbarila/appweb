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
                  <td> <button>Editar</button>
                        <button>Eliminar</button>
                </td>
              `;
                studentTableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error al obtener la lista de estudiantes:', error);
        });
});