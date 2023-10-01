# Primer taller Programación en Ambientes Web

Universidad Libre - 20232

* Danna Sofía Marín C.
* María Isabel Valderrama H.
* María Fernanda Umbarila S.

## Detalles de versiones

* Java: 20
* PostgreSQL: 14
* Gradle: 8.2.1
* Spring Boot: 3.1.3

## Descrición de los endpoints

**1. Ruta base del proyecto**
```
{ruta_base}/api/
```
Ruta previa a todos los endpoints. Si se corre en local, *ruta_base* es *localhos:8081*.

**2. Endpoint de creación de estudiante (POST)**
```
{ruta_base}/api/create_student
```
Este endpoint hace la creación de un estudiante en base de datos, haciendo uso del modelo de estudiante. Recibe un body de tipo JSON tomando como base este mismo modelo, por ejemplo:
 
```
{
    "nombre": "Alfonso",
    "apellido": "Suarez",
    "correo": "alfonso-suarez@unilibre.edu.co"
}
```

**3. Endpoint de obtener estudiante por id (GET)**
```
{ruta_base}/api/get_student/{id}
```
Este endpoint lista en un objeto de tipo JSON los detalles de un estudiante dado un id si éste se encuentra en base de datos. Si no lo encuentra, devuelve un null.

**4. Endpoint de obtener todos los estudiantes (GET)**
```
{ruta_base}/api/get_all_students
```
Este endpoint lista un conjunto de objetos de tipo JSON los detalles de todos los estudiantes que se encuentren en base de datos.

**5. Endpoint de actualización de estudiante (PUT)**
```
{ruta_base}/api/update_student/{id}
```
Este endpoint actualiza la información de un estudiante dado su id, la información a actualizar se pasa a través de un body de tipo JSON. El endpoint realiza la actualizacion siempre y cuando el estudiante exista, a lo cual devuelve un codigo 200; si el estudiante no existe devuelve un error 404. Un ejemplo de actualización del estudiante con id 2 sería:

```
{
    "nombre": "Alfonso",
    "apellido": "Garcia",
}
```

**6. Endpoint de borrado de un estudiante (DELETE)**
```
{ruta_base}/api/delete_student/{id}
```
Este endpoint borra la información de un estudiante dado su id. Si encuentra el estudiante, lo borra devolviendo un código 204, si no lo encuentra, devuelve un código 404 y no realiza el borrado de ningún registro.